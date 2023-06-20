#!/bin/bash

acronym="[a-z]{3}"
slash="\/"
numbers="[0-9]+"
hyphen="-"
word="[^[:cntrl:]]+"

regex="$acronym$slash$numbers$hyphen$word"

branch=${GITHUB_HEAD_REF#refs/*/v}
if ! [[ $branch =~ $regex ]]
then
    echo "::error::Branch is invalid — $branch"
    echo "::error::Branch should looks like - \$acronym/\$issue_number-\$description"
    exit 1
fi
