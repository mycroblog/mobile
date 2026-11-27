#!/usr/bin/env python3
"""Print an AXe describe-ui JSON tree (stdin) as indented `role "label" [id] value @frame` lines."""
import json, sys

def walk(n, d):
    label = n.get("AXLabel") or n.get("title") or ""
    if n.get("type") not in ("Group",) or label:
        f = n.get("frame") or {}
        bits = [n.get("type") or n.get("role") or "?"]
        if label.strip(): bits.append(json.dumps(label, ensure_ascii=False))
        if n.get("AXUniqueId"): bits.append(f"[{n['AXUniqueId']}]")
        if n.get("AXValue"): bits.append(f"={json.dumps(n['AXValue'], ensure_ascii=False)}")
        bits.append(f"@{int(f.get('x',0))},{int(f.get('y',0))},{int(f.get('width',0))}x{int(f.get('height',0))}")
        print("  " * d + " ".join(bits)); d += 1
    for c in n.get("children") or []: walk(c, d)

data = json.load(sys.stdin)
for n in (data if isinstance(data, list) else [data]): walk(n, 0)
