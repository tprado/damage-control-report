#!/bin/bash

cat $(which gradle)

echo '----------------------------------------------------------------------------'

echo
echo 'Building html-generator module...'
echo

cd html-generator
pwd

gradle install 1> build.output 2> build.error
HTML_GENERATOR_RESULT=$?

echo 'build output:'
echo
cat build.output
echo

echo 'build error:'
echo
cat build.error
echo

cd ..

exit $HTML_GENERATOR_RESULT

