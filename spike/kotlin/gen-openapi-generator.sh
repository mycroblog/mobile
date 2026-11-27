#!/bin/sh
# Generates the Kotlin client with openapi-generator, reading my's bundled spec (MIT) in place.
# usage: MY_API_DIR=../my/api gen-openapi-generator.sh <library> <outdir>
set -eu
export ASDF_JAVA_VERSION=temurin-21.0.13+9.0.LTS
START=$(date +%s)
trap 'echo "elapsed: $(( $(date +%s) - START ))s"' EXIT
MY_API_DIR=${MY_API_DIR:-$(cd "$(dirname "$0")/../../../my/api" && pwd)}
LIB=$1
OUT=$2
rm -rf "$OUT"
mkdir -p "$OUT"
OUT_ABS=$(cd "$OUT" && pwd)
docker run --rm -u "$(id -u):$(id -g)" -v "$MY_API_DIR:/spec:ro" -v "$OUT_ABS:/out" \
  openapitools/openapi-generator-cli:v7.28.0 generate \
  -i /spec/openapi.yaml -g kotlin -o /out \
  --library "$LIB" \
  --package-name social.mycroblog.api \
  --additional-properties=serializationLibrary=kotlinx_serialization,dateLibrary=kotlinx-datetime,enumUnknownDefaultCase=true,omitGradleWrapper=true,enumPropertyNaming=UPPERCASE \
  --model-name-mappings List=UserList \
  --type-mappings AnyType=JsonElement \
  --import-mappings JsonElement=kotlinx.serialization.json.JsonElement
