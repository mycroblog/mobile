---
name: new-issue
description: Create one or several issues of mycroblog/mobile - a task, a bug, or a spike - in the right milestone, in order, with the body shape of its type. Also files an issue of mycroblog/my for a gap of the server. Use when asked to file, open, create, cut, or split work into issues.
---

# Create issues

Arguments: what the issues are about.

1. Find the milestone: `gh api repos/mycroblog/mobile/milestones --jq '.[] | "\(.title): \(.description)"'`. A gap of
   the server goes to mycroblog/my instead, in its own task shape (`../my/.github/ISSUE_TEMPLATE/task.yml`).
2. Pick the type: a task builds something, a bug fixes one, a spike finds something out.
3. Write each body in English to a file in the scratchpad, in the shape of its type:
   - task: `### Goal` (what and why, citing the ADR section that decides it), `### Scope` when the goal does not say
     it, `### Done when` with checkboxes that someone else can verify.
   - bug: what happens, what should, how to reproduce it, `### Done when`.
   - spike: `### Spike` (the question and why it matters), `### Done when` (the report as a comment, the conclusion in
     the ADR it names, the code on an unmerged `spike/` branch).
   Links to repository files use `blob/main/...` or a permalink. Name what to build, not how to name its files.
4. Create them in order, earliest first, so that each later one can say `Depends on #n` of the one it waits for
   (`scripts/kit issue claim` reads that line):
   `gh issue create --repo mycroblog/mobile --milestone "<title>" --title "<title>" --body-file <file>`.
5. Report each issue's number, title, and what it depends on, in one line each.
