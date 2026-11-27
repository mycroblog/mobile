#!/usr/bin/env python3
"""Time one step (tap + tree + screenshot) through AXe, both per command and through `axe serve`.

usage: bench_axe.py <udid> <outdir> [n]
"""
import json, statistics, subprocess, sys, time

udid, out = sys.argv[1], sys.argv[2]
n = int(sys.argv[3]) if len(sys.argv) > 3 else 5
TAP = ["tap", "-x", "38", "-y", "84"]  # the "Plain N" toolbar button on an iPhone 18 Pro in portrait

def per_command():
    t = time.monotonic()
    subprocess.run(["axe", *TAP, "--udid", udid], check=True, capture_output=True)
    subprocess.run(["axe", "describe-ui", "--udid", udid], check=True, capture_output=True)
    subprocess.run(["axe", "screenshot", "--udid", udid, "--output", f"{out}/axe-cmd.png"], check=True, capture_output=True)
    return time.monotonic() - t

def serve():
    t0 = time.monotonic()
    p = subprocess.Popen(["axe", "serve", "--udid", udid], stdin=subprocess.PIPE, stdout=subprocess.PIPE, text=True, bufsize=1)
    ready = p.stdout.readline()
    def ask(line):
        p.stdin.write(line + "\n"); p.stdin.flush()
        return json.loads(p.stdout.readline())
    ask("describe-ui")  # the first tree read opens the accessibility session
    cold = time.monotonic() - t0
    times = []
    for _ in range(n):
        t = time.monotonic()
        ask(" ".join(TAP)); ask("describe-ui"); ask(f"screenshot --output {out}/axe-serve.png")
        times.append(time.monotonic() - t)
    p.stdin.close(); p.wait()
    return ready.strip(), cold, times

cmd = [per_command() for _ in range(n)]
ready, cold, srv = serve()
print(json.dumps({"per_command": cmd, "per_command_median": statistics.median(cmd),
                  "serve_ready": ready, "serve_cold_start_incl_first_tree": cold,
                  "serve": srv, "serve_median": statistics.median(srv)}, indent=1))
