---
name: comment-issue
description: Comment on an issue or a pull request of mycroblog/mobile or mycroblog/my - record a decision, report a spike, answer the decider, point an issue at the ADR or pull request that settles it. Use when asked to comment, reply, note, or report on an issue or a pull request.
---

# Comment on an issue or a pull request

Arguments: the repository (default `mobile`), the number, and what the comment is for.

1. Write the comment in English to a file in the scratchpad. Say the conclusion, not the session: a decision and
   where it is recorded, a spike's finding with its sources, what changed. Link repository files by `blob/main/...`
   or a permalink. Never quote the decider; state what was decided.
2. Post it with the repository first, in exactly this form, which the permissions allow:
   - an issue: `gh issue comment -R mycroblog/<repo> <n> --body-file <file>`
   - a pull request: `gh pr comment -R mycroblog/<repo> <n> --body-file <file>`
3. Report the comment's URL.
