#!/bin/bash

hash="^#"
number="[0-9]+"
space="[[:space:]]"
word="[^[:cntrl:]]+"

message=$(git log -1 --pretty=format:"%s" | tr -dc '[:print:]\n')
if ! [[ "$message" =~ $hash$number$space$word$space ]]
then
  echo "::error::Message is invalid — $message"
  echo "::error::Commit message should look like - #\$issue_number \$description"
  exit 1
fi
