# Picover [![Dev branch verification](https://github.com/intive/Picover/actions/workflows/default_branch_verification.yml/badge.svg)](https://github.com/intive/Picover/actions/workflows/default_branch_verification.yml) [![Pull Request verification](https://github.com/intive/Picover/actions/workflows/pull_request_verification.yml/badge.svg)](https://github.com/intive/Picover/actions/workflows/pull_request_verification.yml)

App for documenting memories from Android Guild meetings and parties

## Team:

<table>
  <tr>
    <td>
      <p align="center">
        <a href="https://github.com/BorysChajdas">
          <img  src="https://avatars.githubusercontent.com/BorysChajdas" alt="Borys Chajdas" width="70">
        </a>
      </p>
    </td>
	<td>
      <p align="center">
        <a href="https://github.com/MarekMacko">
          <img src="https://avatars.githubusercontent.com/MarekMacko" alt="Marek Macko" width="70">
        </a>
      </p>
    </td>
	<td>
      <p align="center">
        <a href="https://github.com/Nataniel-Antosik">
          <img src="https://avatars.githubusercontent.com/Nataniel-Antosik" alt="Nataniel Antosik" width="70" >
        </a>
      </p>
    </td>
	<td>
      <p align="center">
        <a href="https://github.com/kmaslowiec">
		  <img src="https://avatars.githubusercontent.com/kmaslowiec" alt="Konrad Masłowiec" width="70">
		</a>
      </p>
    </td>
	<td>
      <p align="center">
		<a href="https://github.com/michal-kucznerowicz">
		  <img src="https://avatars.githubusercontent.com/michal-kucznerowicz" alt="Michał Kucznerowicz<" width="70">
		</a>
      </p>
    </td>
	<td>
      <p align="center">
		<a href="https://github.com/marcinWisnia">
		  <img src="https://avatars.githubusercontent.com/marcinWisnia" alt="Marcin Wiśniewski" width="70">
		</a>
      </p>
    </td>
	<td>
      <p align="center">
		<a href="https://github.com/bartek977">
		  <img src="https://avatars.githubusercontent.com/bartek977" alt="Bartłomiej Turkosz" width="70">
		</a>
      </p>
    </td>
  </tr>
  <tr>
	<td>
	  <p align="center">
		Borys Chajdas
	  </p>
    </td>
	<td>
	  <p align="center">
		Marek Macko
	  </p>
    </td>
	<td>
	  <p align="center">
		Nataniel Antosik
	  </p>
    </td>
	<td>
	  <p align="center">
		Konrad Masłowiec
	  </p>
    </td>
	<td>
	  <p align="center">
		Michał Kucznerowicz
	  </p>
    </td>
	<td>
	  <p align="center">
		Marcin Wiśniewski
	  </p>
    </td>
	<td>
	  <p align="center">
		Bartłomiej Turkosz
	  </p>
    </td>
  </tr>
</table>

## Contribution

Current issues are present in the [project backlog](https://github.com/orgs/intive/projects/3). Workflow is the following:

- **TODO** column is for giving ideas and discussions on the topics. Most of the ideas are created as a drafts and then discussed.
- If the idea is accepted and prepared to be implemented, it goes to **Ready for development** column.
- **In progress** and **Done** columns are self-explanatory.

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

- Pull Request title should be the same as the commit message
- Only one commit should be present in one Pull Request
- Progress not perfection. In the review, we suggest what can be improved. If the issue does not affect the functionality it is up to the owner of the PR if he wants to implement it.
- All comments need to be answered before the approval.
- If you approve the PR and you have not found an issue please try to leave a comment.

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
