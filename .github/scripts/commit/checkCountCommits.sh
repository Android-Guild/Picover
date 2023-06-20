#!/bin/bash

COMMITS_COUNT=$1

if (( COMMITS_COUNT > 1 )); then
  echo "Error: Pull request contains more than one commit (you have $COMMITS_COUNT commits)."
  exit 1
fi
