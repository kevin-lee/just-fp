#!/bin/bash -e

set -x

if [ -z "$1" ]
  then
    echo "Missing parameters. Please enter the [Scala version]."
    echo "sbt-build.sh 2.13.10"
    exit 1
else
  : ${CURRENT_BRANCH_NAME:?"CURRENT_BRANCH_NAME is missing."}
  : ${CI_BRANCH:?"CI_BRANCH is missing."}

  scala_version=$1
  echo "============================================"
  echo "Build projects"
  echo "--------------------------------------------"
  echo ""

  test_task="test"
  if [[ $scala_version == 2.12* || $scala_version == 2.13* ]] ;
  then
    test_task="${test_task} scalafix"
  fi

  if [ "$2" == "report" ]
  then
    test_task="coverage ${test_task} coverageReport coverageAggregate"
  fi

  echo "sbt -J-Xmx2048m ++${scala_version}! -v clean ${test_task}"
  sbt \
    -J-Xmx2048m \
    ++${scala_version}! \
    -v \
    clean \
    ${test_task}

  export SOURCE_DATE_EPOCH=$(date +%s)
  echo "SOURCE_DATE_EPOCH=$SOURCE_DATE_EPOCH"

  if [[ "$CURRENT_BRANCH_NAME" == "main" || "$CURRENT_BRANCH_NAME" == "release" ]]
  then
    sbt \
      -J-Xmx2048m \
      ++${scala_version}! \
      -v \
      clean \
      packagedArtifacts
  else
    sbt \
      -J-Xmx2048m \
      ++${scala_version}! \
      -v \
      clean \
      packagedArtifacts
  fi


  echo "============================================"
  echo "Building projects: Done"
  echo "============================================"
fi
