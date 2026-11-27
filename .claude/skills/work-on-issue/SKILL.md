---
name: work-on-issue
description: Take an issue, several issues, an epic, or a milestone of mycroblog/mobile to main - claim, build on one branch with the roles of ADR-0011, open the pull request, land it, clean up, and close what it completes. Given an epic or a milestone, plan its pull requests first and act on its own once the plan is approved. Use when asked to work on, pick up, implement, or finish issues, an epic, or a milestone.
---

# Work on a goal

Arguments: issue numbers, an epic, or a milestone. Without one, take the next issue nothing blocks of the earliest
milestone in this order: Agent harness, Risky choices verified, App skeletons and checks, then the others.

## Plan an epic or a milestone

Given an epic or a milestone, you are the orchestrator. Before building:

1. Read every issue in it (`scripts/kit ctx <n>`). Order them by what depends on what ("Depends on #n"), and group
   them into pull requests by the files they share.
2. If it all fits one pull request, go on to the next section without asking.
3. Otherwise present the plan and wait for the decider's approval: the pull requests in order, the issues of each,
   what is left out and why, and the decisions it needs. Revise it until approved.
4. Record the approved plan with `/comment-issue` on the milestone's first issue, and follow it from there, after a
   compaction too. Ask again only for a decision that is expensive to undo and that no Accepted ADR covers, or when
   the plan stops holding.

## Build and land

1. Claim each issue: `scripts/kit issue claim <n> --plan "<one line>"`. It refuses what is blocked or taken.
2. One worktree and one branch per pull request, `<type>/<issue>-<slug>`; commit as the work goes.
3. A spike: answer its question with the platform advisors and throwaway code on an unmerged `spike/<issue>-<slug>`
   branch; report with `/comment-issue` on the issue; write the conclusion into the ADR the issue names with `/adr`.
   The pull request carries only the ADR change.
4. Build: yourself for one small issue; otherwise up to four subagents at once.
   - Before a decision or code that relies on how iOS or Android behaves, ask `ios-platform-advisor` or
     `android-platform-advisor`. Never rely on memory for an OS fact.
   - A feature for both apps: the API generation first, then `swift-developer` and `kotlin-developer` in parallel,
     then `swift-test-developer` and `kotlin-test-developer`, each given the issue and its developer's report, not
     the code. `routine` for mechanical work.
   - A UI change: until the evaluator roles exist (#17), show the decider each app running, on both widths of
     ADR-0009 §2 and ADR-0010 §4.
5. Bring the branch up to date with `git rebase origin/main`; `scripts/kit check` is the local check.
6. `scripts/kit pr open` (`--refs` when the change does not complete the issue), then `scripts/kit pr land <pr>`
   in the background, and end the turn until it reports. Fix what it reports at once.
7. Finish when the goal is merged and closed. Report what is merged, what is not and why, and what needs a decision.

## When a decision is needed

Decide what is cheap to undo and say so in the pull request. Stop only for one that no Accepted ADR covers and that is
expensive to undo: a public contract, stored data that would be lost, a paid service, a setting users see, a
store listing. Then comment on the issue with the options and your recommendation, draft a Proposed ADR with `/adr`
if it is architectural, and go on with the rest. When the decider answers, state the conclusion in the ADR that owns
the topic, or on the issue.
