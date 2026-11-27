#!/bin/sh
# Prints the size of each generator's output.
set -eu
cd "$(dirname "$0")/out"
for d in oag-ktor fabrikt-ktor; do
  base=$d/src/main/kotlin/social/mycroblog/api
  echo "$d: kt_files=$(find "$d" -name '*.kt' | wc -l | tr -d ' ')" \
    "kt_loc=$(find "$d" -name '*.kt' -exec cat {} + | wc -l | tr -d ' ')" \
    "kt_bytes=$(find "$d" -name '*.kt' -exec cat {} + | wc -c | tr -d ' ')" \
    "models=$(ls "$base/models" | wc -l | tr -d ' ')" \
    "other_files=$(find "$d" -type f ! -name '*.kt' | wc -l | tr -d ' ')"
  for sub in "$base"/*/; do
    echo "  $(basename "$sub"): $(ls "$sub" | wc -l | tr -d ' ') files, $(cat "$sub"/*.kt | wc -l | tr -d ' ') lines"
  done
done
