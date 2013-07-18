#!/bin/bash

echo
echo 'Building html-generator module...'
echo '-------------------------------------------------------------------'
cd html-generator
gradle install &> build.output
HTML_GENERATOR_RESULT=$?
cat build.output
echo '-------------------------------------------------------------------'

cd ..

exit $HTML_GENERATOR_RESULT
