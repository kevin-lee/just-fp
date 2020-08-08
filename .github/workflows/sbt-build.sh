#!/bin/bash -e

set -x

if [ -z "$2" ]
  then
    echo "Missing parameters. Please enter the [project] and [Scala version]."
    echo "sbt-build.sh core 2.12.10"
    exit 1
else
  : ${CURRENT_BRANCH_NAME:?"CURRENT_BRANCH_NAME is missing."}
  : ${CI_BRANCH:?"CI_BRANCH is missing."}
  project_name=$1
  scala_version=$2
  echo "============================================"
  echo "Build projects"
  echo "--------------------------------------------"
  echo ""
  if [[ "$CURRENT_BRANCH_NAME" == "main" || "$CURRENT_BRANCH_NAME" == "release" ]]
  then
    sbt -J-Xmx2048m "project ${project_name}" ++${scala_version}! clean coverage test coverageReport coverageAggregate
    sbt -J-Xmx2048m "project ${project_name}" ++${scala_version}! coveralls
    sbt -J-Xmx2048m "project ${project_name}" ++${scala_version}! clean packagedArtifacts
  else
    sbt -J-Xmx2048m "project ${project_name}" ++${scala_version}! clean coverage test coverageReport coverageAggregate package
    sbt -J-Xmx2048m "project ${project_name}" ++${scala_version}! coveralls
  fi


  echo "============================================"
  echo "Building projects: Done"
  echo "============================================"
fi
