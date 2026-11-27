#!/bin/sh
# Runs the runtime probes (decoding a oneOf, a timeline page, a Problem) against one output.
# usage: runtime-test.sh <oag|fabrikt>
set -eu
export ASDF_JAVA_VERSION=temurin-21.0.13+9.0.LTS
JAVA_HOME=$(asdf where java temurin-21.0.13+9.0.LTS)
export JAVA_HOME
cd "$(dirname "$0")/harness"
./gradlew --console=plain ":$1:test"
