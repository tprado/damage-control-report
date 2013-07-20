#!/bin/bash

export MVN_COMMAND=$(which mvn)

gradle --info 1> build.output 2> build.error
RESULT=$?

echo 'build output:'
echo
cat build.output
echo

echo 'build error:'
echo
cat build.error
echo

exit $RESULT
