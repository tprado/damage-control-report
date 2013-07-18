#!/bin/bash

echo
echo 'Building html-generator module...'
echo '-------------------------------------------------------------------'
gradle --info :html-generator:clean :html-generator:install
echo '-------------------------------------------------------------------'

