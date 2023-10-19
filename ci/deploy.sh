#!/usr/bin/env bash

SEMVER_PAT="[0-9][0-9]*\.[0-9][0-9]*\.[0-9][0-9]*"
VERSION=$(echo "$1" | grep -o -e "$SEMVER_PAT")

ci/deploy.rb "$1"

exit 0
