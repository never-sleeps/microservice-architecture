#!/usr/bin/env bash

set -e

IMAGE_NAME=neversleeps/user-service-09
IMAGE_TAG="$1"

if [ -z "${IMAGE_TAG}" ]; then
  echo "Missing required parameter 1 (image tag)"
  exit 1
fi

./gradlew clean build
docker build -t "${IMAGE_NAME}":"${IMAGE_TAG}" .
docker push "${IMAGE_NAME}":"${IMAGE_TAG}"