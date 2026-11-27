# swift-test-developer: index

What the swift-test-developer role has learned in this repository. Read this page, then `decisions.md` when it exists.

- `decisions.md`: one line per decision or correction: `- <what to do> - <why> (<issue or PR>, <date>)`.

## Rules for a line

- A correction of the decider becomes a line here. The same correction a second time becomes a proposal, in the pull
  request, to turn the line into a check (ADR-0011 §5).
- Facts about the OS belong to the platform advisor's knowledge, not here: ask it, and it records them with sources.
- Every file is at most 4096 bytes (`scripts/kit check`). Near it: merge similar lines, delete lines a check now
  holds, or split the file by topic and name the new file above.
- Add, merge, or delete lines; never rewrite a file.
