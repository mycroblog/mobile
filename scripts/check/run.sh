#!/usr/bin/env bash
# The checks that the tasks of tasks/*.yml run: `scripts/check/run.sh <check>`. Each prints what is wrong and how to
# fix it, and exits non-zero when it fails. Run them through `task check` (or one area, `task docs:check`).
set -euo pipefail

ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/../.." && pwd)"
cd "$ROOT"
MARKDOWNLINT="markdownlint-cli2@0.23.2"
KNOWLEDGE_LIMIT=4096 # bytes per file of .claude/knowledge, .claude/agents and .claude/skills (ADR-0011 §5)

fail() { printf 'check: %s\n' "$*" >&2; exit 1; }

need() { command -v "$1" >/dev/null 2>&1 || fail "$1 is not installed: $2 (CONTRIBUTING.md says how)."; }

check_markdown() {
  need npx "install Node.js with asdf install nodejs"
  local out
  out="$(mktemp)"
  trap 'rm -f "$out"' RETURN
  (cd "$ROOT" && npx -y "$MARKDOWNLINT" "**/*.md" >/dev/null 2>"$out") || {
    cat "$out" >&2
    fail "fix each line above as its rule says (npx $MARKDOWNLINT --fix \"**/*.md\" fixes most), then check again."
  }
}

check_fullwidth_parens() {
  local hits
  hits="$(git -C "$ROOT" ls-files -z '*.md' | (cd "$ROOT" && xargs -0 grep -n -e '（' -e '）') || true)"
  if [[ -n "$hits" ]]; then
    printf '%s\n' "$hits" >&2
    fail 'full-width parentheses in Markdown: write ( and ) with a space before ( inside a sentence, and none before 、 or 。'
  fi
}

check_knowledge_size() {
  local failed=0 f size
  while IFS= read -r -d '' f; do
    size="$(wc -c <"$f")"
    if (( size > KNOWLEDGE_LIMIT )); then
      printf 'check: %s is %s bytes, over %s: split it by topic and name the new file in its index, or say it in fewer words.\n' \
        "${f#"$ROOT"/}" "$size" "$KNOWLEDGE_LIMIT" >&2
      failed=1
    fi
  done < <(find "$ROOT/.claude/knowledge" "$ROOT/.claude/agents" "$ROOT/.claude/skills" -name '*.md' -print0 2>/dev/null)
  return "$failed"
}

check_shell() {
  need shellcheck "install shellcheck with asdf install shellcheck"
  local files=("$ROOT/scripts/kit")
  shopt -s nullglob
  files+=("$ROOT"/scripts/*.sh "$ROOT"/scripts/*/*.sh)
  shopt -u nullglob
  shellcheck "${files[@]}" || fail "fix each finding above as its SC code's page on shellcheck.net says, then check again."
}

check_tools() {
  need node "install Node.js with asdf install nodejs"
  local out
  out="$(mktemp)"
  trap 'rm -f "$out"' RETURN
  (cd "$ROOT" && node --test tools/drive/ .claude/hooks/ >"$out" 2>&1) || {
    grep -E '^(not ok|# fail|✖)|Error' "$out" >&2 || cat "$out" >&2
    fail 'the tests of the app-driving tool or the hooks failed: run node --test tools/drive/ .claude/hooks/ and fix the code they name.'
  }
}

# The version a tool of .tool-versions reports, in the form .tool-versions pins it, or nothing when it does not run.
installed_version() {
  case "$1" in
    nodejs) node --version 2>/dev/null | sed 's/^v//' ;;
    shellcheck) shellcheck --version 2>/dev/null | sed -n 's/^version: //p' ;;
    java) java -version 2>&1 | sed -n 's/.*version "\([^"]*\)".*/\1/p' | head -n 1 ;;
    task) task --version 2>/dev/null | sed -e 's/^Task version: //' -e 's/^v//' -e 's/ .*//' ;;
    *) printf 'unknown\n' ;;
  esac
}

# The part of a pinned version that the tool itself reports: temurin-21.0.13+9.0.LTS is java 21.0.13.
comparable_version() {
  case "$1" in
    java) sed -e 's/^[a-z]*-//' -e 's/+.*//' <<<"$2" ;;
    *) printf '%s\n' "$2" ;;
  esac
}

check_tool_versions() {
  local failed=0 tool pinned want have
  while read -r tool pinned _; do
    [[ -z "$tool" || "$tool" == \#* ]] && continue
    want="$(comparable_version "$tool" "$pinned")"
    have="$(installed_version "$tool" || true)"
    if [[ "$have" == "unknown" ]]; then
      printf 'check: .tool-versions pins %s, which this check does not know: add it to installed_version in %s.\n' \
        "$tool" "${BASH_SOURCE[0]#"$ROOT"/}" >&2
      failed=1
    elif [[ -z "$have" ]]; then
      printf 'check: %s %s is not installed: asdf plugin add %s && asdf install %s %s (CONTRIBUTING.md).\n' \
        "$tool" "$pinned" "$tool" "$tool" "$pinned" >&2
      failed=1
    elif [[ "$have" != "$want" ]]; then
      printf 'check: %s is %s, but .tool-versions pins %s: asdf install %s %s, and make sure asdf shims come first on PATH (CONTRIBUTING.md).\n' \
        "$tool" "$have" "$pinned" "$tool" "$pinned" >&2
      failed=1
    fi
  done <"$ROOT/.tool-versions"
  return "$failed"
}

check_xcode_version() {
  local want have
  want="$(tr -d '[:space:]' <"$ROOT/.xcode-version")"
  have="$(xcodebuild -version 2>/dev/null | sed -n 's/^Xcode //p' | head -n 1 || true)"
  if [[ -z "$have" ]]; then
    fail "Xcode is not selected: install Xcode $want from the App Store or developer.apple.com, then sudo xcode-select -s /Applications/Xcode.app (CONTRIBUTING.md)."
  elif [[ "$have" != "$want" ]]; then
    fail "the selected Xcode is $have, but .xcode-version pins $want, the latest released Xcode (ADR-0009 §7): install it and sudo xcode-select -s <its path>, or update .xcode-version in a pull request of its own when a newer Xcode is released."
  fi
}

case "${1:-}" in
  markdown) check_markdown ;;
  fullwidth-parens) check_fullwidth_parens ;;
  knowledge-size) check_knowledge_size ;;
  shell) check_shell ;;
  tools) check_tools ;;
  tool-versions) check_tool_versions ;;
  xcode-version) check_xcode_version ;;
  *) fail "unknown check ${1:-(none)}: run the checks with task check (task --list shows them)." ;;
esac
