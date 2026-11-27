#!/bin/sh
# Time one step (tap + tree + screenshot) through the idb CLI, n times. usage: bench_idb.sh <idb> <udid> <outdir> [n]
IDB=$1; U=$2; OUT=$3; N=${4:-5}
i=0
while [ $i -lt $N ]; do
  s=$(python3 -c 'import time; print(time.monotonic())')
  "$IDB" ui tap --udid "$U" 38 84 >/dev/null
  "$IDB" ui describe-all --udid "$U" > "$OUT/idb-tree.json"
  "$IDB" screenshot --udid "$U" "$OUT/idb.png" >/dev/null
  python3 -c "import time; print(round(time.monotonic()-$s, 3))"
  i=$((i+1))
done
