#!/bin/bash

message=$(git log -1 --pretty=format:"%s" | tr -dc '[:print:]\n')
if ! [[ "$message" =~ ^#[0-9]+[[:space:]][^[:cntrl:]]+[[:space:]]*$ ]]
then
  echo "::error::Message is invalid — $message"
  echo "::error::Commit message should look like - #\$issue_number \$description"
  exit 1
fi
