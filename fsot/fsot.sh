#!/bin/sh

EXEC="$0"
POS="${EXEC%/*}"

exec java -Xmx512M -jar "$POS/fsot.jar" "$@"
