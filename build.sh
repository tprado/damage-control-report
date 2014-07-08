#!/bin/bash

export MVN_COMMAND=$(which mvn)

echo
echo "TRAVIS_BRANCH=${TRAVIS_BRANCH}"
echo "TRAVIS_PULL_REQUEST=${TRAVIS_PULL_REQUEST}"
echo

run_gradle() {
    local task=$1

    gradle --info ${task}

}

echo 'building artifacts...'
run_gradle

if [[ ${TRAVIS_BRANCH} = 'master' && ${TRAVIS_PULL_REQUEST} = 'false' ]]; then
    echo 'uploading archives...'
    run_gradle uploadArchives
fi

exit 0
