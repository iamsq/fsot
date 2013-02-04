#!/bin/sh

EXEC="`dirname $0`"
POS="${EXEC%/*}"
export CODECOVER_DIR="$POS/codecover"
export antCodeCoverXML="$POS/ant-codecover.xml"

java -Xmx512M -jar "$POS/fsot.jar" "$@"
