---
name: adr
description: Draft a new Architecture Decision Record or revise an existing one in docs/adr of mycroblog/mobile, following ADR-0000. Use when asked to write, update, or propose a design decision, or to fold a spike's conclusion into an ADR.
---

# ADR skill

Policy: ADR-0000 (which follows my ADR-0000 §1). Template: `docs/adr/template.md`.

1. Find the ADR that owns the topic (`ls docs/adr`, `grep -l <words> docs/adr/*.md`). One ADR per topic: if one owns
   it, edit it. Only for a topic none owns, copy the template to `docs/adr/<next number>-<english-slug>.md`.
2. Write three things and nothing else: the decision, its reasons (with the options not taken), and the conditions to
   revisit it. No file, function, task, or setting names, no lists of what exists, no procedures: the code holds
   those. Write in Japanese, with half-width parentheses.
   - The decision stands alone, in numbered sections others cite as `ADR-NNNN §k`. Keep the numbers: add sections at
     the end and rewrite a section in place. A Proposed ADR phrases it as a recommendation.
   - `採らなかった案` gives each option its strongest case and why it lost, a line or two each.
   - Cite the server's ADRs as `my ADR-NNNN §k`.
3. Never set the status to Accepted or Rejected, and change the conclusion of an Accepted ADR only as the decider
   stated it. Propose any other change in the pull request.
4. An external fact (an OS behavior, a version, a limit) carries its source and the date it was checked; get it from
   the platform advisor. One you could not check is marked `要検証`.
5. `scripts/kit check`. If the ADR needs the decider, comment on the issue with the options and your recommendation
   in five lines, and link the pull request.
