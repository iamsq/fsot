#!/bin/sh

EXEC="`dirname $0`"
POS="${EXEC%/*}"
CODECOVER_DIR="$POS/codecover"
antCodeCoverXML="$POS/ant-codecover.xml"

exec java -Xmx512M -jar "$POS/fsot.jar" "$@"
