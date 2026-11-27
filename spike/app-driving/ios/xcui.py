#!/usr/bin/env python3
"""Client of the XCUITest driver server (Spike3UITests/DriverServer.swift).

usage: xcui.py '<json command>' [...]     one reply line per command, e.g.
       xcui.py '{"op":"tap","label":"Compose","tree":true,"screenshot":"/tmp/s.png"}'
       xcui.py --bench <n> <outdir>       time n steps of tap + tree + screenshot
"""
import json, os, socket, statistics, sys, time

PORT = int(os.environ.get("SPIKE_DRIVER_PORT", "22087"))

class Driver:
    def __init__(self):
        self.s = socket.create_connection(("127.0.0.1", PORT)); self.f = self.s.makefile("rwb")
    def __call__(self, **cmd):
        self.f.write(json.dumps(cmd).encode() + b"\n"); self.f.flush()
        return json.loads(self.f.readline())

if __name__ == "__main__":
    d = Driver()
    if sys.argv[1] == "--bench":
        n, out = int(sys.argv[2]), sys.argv[3]
        times = []
        for _ in range(n):
            t = time.monotonic()
            r = d(op="tap", x=38, y=84, tree=True, screenshot=f"{out}/xcui.png")
            times.append(time.monotonic() - t)
        print(json.dumps({"steps": times, "median": statistics.median(times), "last_ok": r["ok"], "server_ms": r["ms"]}))
    else:
        for a in sys.argv[1:]:
            r = d(**json.loads(a)); tree = r.pop("tree", None)
            print(json.dumps(r, ensure_ascii=False))
            if tree: print(tree)
