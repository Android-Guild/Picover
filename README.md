# Picover

[![](https://github.com/Android-Guild/Picover-KMP/actions/workflows/build.yml/badge.svg)](https://github.com/Android-Guild/Picover-KMP/actions/workflows/build.yml)
[![](https://github.com/Android-Guild/Picover-KMP/actions/workflows/lint.yml/badge.svg)](https://github.com/Android-Guild/Picover-KMP/actions/workflows/lint.yml)

App for documenting memories from Android Guild meetings and parties

## Contribution

Current issues are present in the [project backlog](https://github.com/orgs/intive/projects/3). Workflow is the following:

- **TODO** column is for giving ideas and discussions on the topics. Most of the ideas are created as a drafts and then discussed.
- If the idea is accepted and prepared to be implemented, it goes to **Ready for development** column.
- **In progress** and **Done** columns are self-explanatory.

## Repository

### Branch and commit naming

Allowed branch name templates:
- `issue_number/description`
- `description`

Examples:
- `1/initial-project`
- `initial-project`

Allowed commit message templates:
- `#issue_number description`
- `description`

Examples:
- `#1 Initial project`
- `Initial project`

Where:
- `issue_number` – number for a particular issue created [here](https://github.com/Android-Guild/Picover-KMP/issues),
- `description` – up to author, "what has been done in this commit?" in few words, could be a name of the issue for
  issues with only one commit.

### Pull Requests

- Pull Request title should be the same as the commit message
- Only one commit should be present in one Pull Request
- Progress not perfection. In the review, we suggest what can be improved. If the issue does not affect the functionality it is up to the owner of the PR if he wants to implement it.
- All comments need to be answered before the approval.

## CI/CD

Two build types:
- release
- debug

App will be distributed via Firebase, link will be provided after implementation.

## Name conventions

### String resources

PascalCase

### Code

[Kotlin coding conventions](https://kotlinlang.org/docs/coding-conventions.html)

### Agreements

- Text validation: allow users to type more characters than limited by the validator and let them to decide how to fit the signs to match the max size. See
  discussion: [here](https://github.com/Android-Guild/Picover/issues/297)
