#!/bin/bash

set -e

echo "checking combined.yml"
semgrep --config="combined.yml" --validate || exit 1

find dist -name "*.yml" | while read -r fil; do
    echo "checking $fil"
    semgrep --config="$fil" --validate || exit 1
done

exit 0
