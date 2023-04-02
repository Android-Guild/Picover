# Picover

App for documenting memories from Android Guild meetings and parties

## Contribution

Current issues are present in the [project backlog](https://github.com/orgs/intive/projects/3). Workflow is the following:
- **TODO** column is for giving ideas and discussions on the topics. Most of the ideas are created as a drafts and then discussed.
- If the idea is accepted and prepared to be implemented, it goes to **Ready for development** column.
- **In progress** and **Done** columns are self-explanatory  .

## Repository

### Branch

Template:
> `$acronym/$issue_number-$description`

Example:
> <b>kma/1-inital-project</b>

### Commit

Template:
> `#$issue_number $description`

Example:
> <b>#1 initial project</b>

– *$acronym* – selected by contributor, should not be changed.
- *$issue_number* – points to a issue from the [GitHub project](https://github.com/orgs/intive/projects/3/views/1).
- *$description* – is up to author, "what has been done in this commit?" in few words, could be a name of the issue for issues with only one commit.

### Pull Requests

- Pull Request title should be the same as commit message
- Only one commit should be present in one Pull Request

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
