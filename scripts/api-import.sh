#!/usr/bin/env bash
# Imports my's bundled client API document (api/openapi.yaml, MIT) into shared/api/openapi.yaml and records the my
# commit it came from in shared/api/my-commit (ADR-0003 §1): `scripts/api-import.sh [<my checkout>] [<commit or tag>]`.
# The checkout defaults to ../my and the commit to its HEAD. Run `task api:generate` after it.
set -euo pipefail

ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
MY="${1:-$ROOT/../my}"
REF="${2:-HEAD}"

fail() { printf 'api-import: %s\n' "$*" >&2; exit 1; }

git -C "$MY" rev-parse --git-dir >/dev/null 2>&1 || fail "$MY is not a checkout of mycroblog/my: clone it next to this repository, or pass its path."
commit="$(git -C "$MY" rev-parse --verify --quiet "$REF^{commit}")" ||
  fail "$REF is not a commit of $MY: git -C $MY fetch --tags origin, or pass a commit or tag that exists."
git -C "$MY" show "$commit:api/openapi.yaml" >"$ROOT/shared/api/openapi.yaml" ||
  fail "$commit of $MY has no api/openapi.yaml."
printf '%s\n' "$commit" >"$ROOT/shared/api/my-commit"
printf 'api-import: shared/api/openapi.yaml is api/openapi.yaml of mycroblog/my@%s; now run task api:generate.\n' "$commit"
