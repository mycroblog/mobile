#!/usr/bin/env python3
"""Minimal stdio MCP client, to drive `maestro mcp` one call at a time and time each call.

usage: mcp_client.py <script.json> -- <server command...>
script.json: [{"tool": name, "args": {...}, "label": "..."}, ...]; "list" as tool lists the tools.
"""
import json, subprocess, sys, time

script = json.load(open(sys.argv[1]))
cmd = sys.argv[sys.argv.index("--") + 1:]
t0 = time.monotonic()
p = subprocess.Popen(cmd, stdin=subprocess.PIPE, stdout=subprocess.PIPE, stderr=open("/dev/null", "w"), text=True, bufsize=1)
mid = 0
def rpc(method, params=None, notify=False):
    global mid
    msg = {"jsonrpc": "2.0", "method": method}
    if params is not None: msg["params"] = params
    if not notify: mid += 1; msg["id"] = mid
    p.stdin.write(json.dumps(msg) + "\n"); p.stdin.flush()
    if notify: return None
    while True:
        line = p.stdout.readline()
        if not line: raise SystemExit("server closed")
        try: r = json.loads(line)
        except ValueError: continue
        if r.get("id") == mid: return r

rpc("initialize", {"protocolVersion": "2025-06-18", "capabilities": {}, "clientInfo": {"name": "spike", "version": "0"}})
rpc("notifications/initialized", notify=True)
print(json.dumps({"label": "initialize", "s": round(time.monotonic() - t0, 3)}), flush=True)
for step in script:
    t = time.monotonic()
    if step["tool"] == "list":
        r = rpc("tools/list", {})
        out = [(x["name"], x.get("description", "")[:100], list(x.get("inputSchema", {}).get("properties", {}))) for x in r["result"]["tools"]]
    else:
        r = rpc("tools/call", {"name": step["tool"], "arguments": step.get("args", {})})
        res = r.get("result") or r.get("error")
        out = [c.get("text", "")[: step.get("show", 300)] if c.get("type") == "text" else f"<{c.get('type')} {len(c.get('data',''))}b>" for c in (res.get("content", []) if isinstance(res, dict) else [])] or res
    print(json.dumps({"label": step.get("label", step["tool"]), "s": round(time.monotonic() - t, 3), "out": out}, ensure_ascii=False), flush=True)
p.stdin.close(); p.wait(timeout=30)
