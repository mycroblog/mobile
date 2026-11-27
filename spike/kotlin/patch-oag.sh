#!/bin/sh
# Spike-only: patches the two compile errors that no openapi-generator option avoids (an inline
# query enum's default refers to an enum class that is never generated), to see what comes after.
set -eu
API=$1/src/main/kotlin/social/mycroblog/api/apis
sed -i '' -E 's/= Order\.[A-Za-z_]+/= "newest"/' "$API/AdminModerationApi.kt"
sed -i '' -E 's/= Purpose\.[A-Za-z_]+/= "attachment"/' "$API/MediaApi.kt"
