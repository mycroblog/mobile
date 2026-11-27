#!/bin/sh
# Generates the Kotlin client with Fabrikt 29.2.0 (io.fabrikt:fabrikt from Maven Central).
# usage: FABRIKT_JAR=fabrikt-29.2.0.jar MY_API_DIR=../my/api gen-fabrikt.sh <outdir>
set -eu
export ASDF_JAVA_VERSION=temurin-21.0.13+9.0.LTS
START=$(date +%s)
trap 'echo "elapsed: $(( $(date +%s) - START ))s"' EXIT
MY_API_DIR=${MY_API_DIR:-$(cd "$(dirname "$0")/../../../my/api" && pwd)}
FABRIKT_JAR=${FABRIKT_JAR:?download io.fabrikt:fabrikt:29.2.0 from Maven Central and set FABRIKT_JAR}
OUT=$1
rm -rf "$OUT"
mkdir -p "$OUT"
java -jar "$FABRIKT_JAR" \
  --api-file "$MY_API_DIR/openapi.yaml" \
  --base-package social.mycroblog.api \
  --output-directory "$OUT" \
  --targets HTTP_MODELS --targets CLIENT \
  --http-client-target KTOR --http-client-opts GROUP_BY_TAG \
  --serialization-library KOTLINX_SERIALIZATION \
  --validation-library NO_VALIDATION \
  --instant-library KOTLIN_TIME_INSTANT \
  --http-model-opts FAULT_TOLERANT_ENUMS
