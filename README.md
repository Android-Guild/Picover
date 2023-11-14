# Picover

[![](https://github.com/intive/Picover/actions/workflows/build.yml/badge.svg)](https://github.com/intive/Picover/actions/workflows/build.yml)
[![](https://github.com/intive/Picover/actions/workflows/lint.yml/badge.svg)](https://github.com/Android-Guild/Picover/actions/workflows/lint.yml)

App for documenting memories from Android Guild meetings and parties

## Contribution

Current issues are present in the [project backlog](https://github.com/orgs/intive/projects/3). Workflow is the following:

- **TODO** column is for giving ideas and discussions on the topics. Most of the ideas are created as a drafts and then discussed.
- If the idea is accepted and prepared to be implemented, it goes to **Ready for development** column.
- **In progress** and **Done** columns are self-explanatory.

## Repository

### Branch

Each branch should be associated with a team member and corresponding issue created [here](https://github.com/intive/Picover/issues).
To accomplish that, use following template `issue_number/description`, example:
> 1/initial-project

Where:

- `issue_number` – number preceded by a hash character for a particular issue,
- `description` – up to author, "what has been done in this commit?" in few words, could be a name of the issue for
  issues with only one commit.

### Commit

In a similar manner, to keep bidirectional history, each commit should be associated with the corresponding issue,
so the template is following `#issue_number description`, example:
> #1 initial project

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
