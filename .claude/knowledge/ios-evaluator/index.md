# ios-evaluator: index

What the ios-evaluator role has learned in this repository. Read this page, then `decisions.md` and `examples.md` when they
exist. The orchestrator adds the lines; this role reads them and never sees the source.

- `decisions.md`: one line per decision or correction of the decider: `- <what to judge, and how> - <why> (<issue or PR>, <date>)`.
- `examples.md`: one line per finding the decider called right or wrong, with its screenshot described in words.

## Rules for a line

- A correction of the decider becomes a line here. The same correction a second time becomes a proposal, in the pull
  request, to turn the line into a check (ADR-0011 §5).
- A guideline is cited by its page, not restated here.
- Every file is at most 4096 bytes (`scripts/kit check`). Near it: merge similar lines, or split the file by topic
  and name the new file above.
- Add, merge, or delete lines; never rewrite a file.
