@echo off
setlocal
set FSOTHome=%~d0%~p0
set FSOTJar=%FSOTHome%fsot.jar
set antCodeCoverXML=%FSOTHome%ant-codecover.xml

set CODECOVER_DIR=%FSOTHome%codecover

java -Xmx512M -jar "%FSOTJar%" %*
endlocal