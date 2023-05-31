#!/bin/bash

branch=${GITHUB_HEAD_REF#refs/*/v}
if ! [[ $branch =~ [a-z]{3}\/[0-9]+-[^[:cntrl:]]+$ ]]
then
    echo "::error::Branch is invalid — $branch"
    echo "::error::Branch should looks like - \$acronym/\$issue_number-\$description "
    exit 1
fi
