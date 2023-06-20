#!/bin/bash

hash="^#"
number="[0-9]+"
space="[[:space:]]"
word="[^[:cntrl:]]+"

regex="$hash$number$space$word$space"

message=$(git log -1 --pretty=format:"%s" | tr -dc '[:print:]\n')
if ! [[ "$message" =~ $regex ]]
then
  echo "::error::Message is invalid — $message"
  echo "::error::Commit message should look like - #\$issue_number \$description"
  exit 1
fi
