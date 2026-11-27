#!/bin/sh
# Compiles one generator's output from scratch and prints the wall time.
# usage: compile.sh <oag|fabrikt> [extra gradle args]
set -eu
export ASDF_JAVA_VERSION=temurin-21.0.13+9.0.LTS
JAVA_HOME=$(asdf where java temurin-21.0.13+9.0.LTS)
export JAVA_HOME
cd "$(dirname "$0")/harness"
P=$1
shift
START=$(date +%s)
trap 'echo "elapsed: $(( $(date +%s) - START ))s"' EXIT
./gradlew --console=plain ":$P:clean" ":$P:compileKotlin" --rerun-tasks "$@"
