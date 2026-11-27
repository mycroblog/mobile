#!/usr/bin/env python3
"""Drives `maestro mcp` over stdio (newline-delimited JSON-RPC) and times its tools.

  maestro_mcp.py tools                 # list the tools and their input schemas
  maestro_mcp.py call <tool> '<json>'  # one call, prints the result (images summarised)
  maestro_mcp.py bench <device> <n>    # n steps of run(tapOn) + inspect_screen + take_screenshot
"""
import json, os, subprocess, sys, time

MAESTRO = os.environ.get("MAESTRO", "maestro")
APP = "appId: dev.mycroblog.spike15\n---\n"


class Mcp:
    def __init__(self):
        t = time.time()
        env = dict(os.environ, MAESTRO_CLI_NO_ANALYTICS="1", MAESTRO_CLI_ANALYSIS_NOTIFICATION_DISABLED="true")
        self.p = subprocess.Popen([MAESTRO, "mcp", "--no-viewer"], stdin=subprocess.PIPE, stdout=subprocess.PIPE,
                                  stderr=open(os.environ.get("MCP_LOG", "/dev/null"), "w"), env=env, text=True)
        self.id = 0
        self.rpc("initialize", {"protocolVersion": "2025-06-18", "capabilities": {},
                                "clientInfo": {"name": "spike", "version": "0"}})
        self.p.stdin.write(json.dumps({"jsonrpc": "2.0", "method": "notifications/initialized"}) + "\n")
        self.p.stdin.flush()
        self.startup = time.time() - t

    def rpc(self, method, params):
        self.id += 1
        self.p.stdin.write(json.dumps({"jsonrpc": "2.0", "id": self.id, "method": method, "params": params}) + "\n")
        self.p.stdin.flush()
        while True:
            msg = json.loads(self.p.stdout.readline())
            if msg.get("id") == self.id:
                return msg

    def call(self, name, args):
        return self.rpc("tools/call", {"name": name, "arguments": args})


def summarise(msg):
    for c in msg.get("result", {}).get("content", []):
        if c.get("type") == "image":
            c["data"] = f"<{len(c['data'])} base64 chars>"
    return json.dumps(msg, indent=1)[:6000]


if __name__ == "__main__":
    m = Mcp()
    print(f"startup {m.startup:.2f}s", file=sys.stderr)
    if sys.argv[1] == "tools":
        for t in m.rpc("tools/list", {})["result"]["tools"]:
            print(t["name"], json.dumps(t["inputSchema"].get("properties", {}))[:300])
    elif sys.argv[1] == "call":
        t = time.time()
        r = m.call(sys.argv[2], json.loads(sys.argv[3]))
        print(f"{time.time() - t:.2f}s", file=sys.stderr)
        print(summarise(r))
    elif sys.argv[1] == "bench":
        dev, n = sys.argv[2], int(sys.argv[3])
        t = time.time(); m.call("inspect_screen", {"device_id": dev}); print(f"first call {time.time()-t:.2f}s")
        steps = []
        for _ in range(n):
            t = time.time()
            m.call("run", {"device_id": dev, "yaml": APP + "- tapOn: Row 6"})
            t1 = time.time()
            m.call("inspect_screen", {"device_id": dev})
            t2 = time.time()
            m.call("take_screenshot", {"device_id": dev})
            t3 = time.time()
            steps.append((t3 - t, t1 - t, t2 - t1, t3 - t2))
            m.call("run", {"device_id": dev, "yaml": APP + "- back"})
        steps.sort()
        print("step total/tap/tree/shot", [tuple(round(x, 2) for x in s) for s in steps])
        print("median", round(steps[len(steps) // 2][0], 2))
