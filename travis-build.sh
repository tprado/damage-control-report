#!/bin/bash

if [[ ! $M2_HOME ]]; then
    echo 'M2_HOME env var not defined.'

    MAVEN_COMMAND_PATH="$(which mvn)"
    if [[ -h $MAVEN_COMMAND_PATH ]]; then
        MAVEN_COMMAND_PATH="$(readlink $MAVEN_COMMAND_PATH)"
    fi
    export M2_HOME="${MAVEN_COMMAND_PATH%/bin/mvn}"
fi

echo
echo "M2_HOME=$M2_HOME"
echo

gradle assemble
