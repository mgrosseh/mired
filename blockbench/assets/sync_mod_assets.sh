#!/usr/bin/env sh
fail() {
  printf "Failed to execute. %s\n" "$1"
  exit 1
}

cd "$(dirname "$0")" || fail 1

echo "$PWD/mired"
[ -e "./mired" ] && rm -rI ./mired
mkdir mired
cp -r ../../src/generated/resources/assets/mired/* mired
cp -r ../../src/main/resources/assets/mired/* mired
