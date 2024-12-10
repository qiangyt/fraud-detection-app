#!/bin/bash

work_dir="$(pwd)"
script_dir="$(dirname "$(readlink -f "$0")")"
project_dir="$(dirname "$script_dir")"

cd "$project_dir" || exit 1

source .env

export AWS_REGION
export AWS_ACCESS_KEY_ID
export AWS_ACCESS_KEY_SECRET
export AWS_SQS_DETECT_QUEUE_URL
export AWS_SQS_DETECT_DEAD_LETTER_QUEUE_URL
export AWS_SQS_ALERT_QUEUE_URL

mvn spotless:apply

rm -rf target
mvn clean verify

cd "$work_dir" || exit 1
