#!/bin/sh

EXEC="`dirname $0`"
#POS="${EXEC%/*}"
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
export CODECOVER_DIR="$DIR/codecover"
export antCodeCoverXML="$DIR/ant-codecover.xml"
java -Xmx512M -jar "$DIR/fsot.jar" "$@"
