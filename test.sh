#!/bin/bash

source .env

export AWS_REGION
export AWS_ACCESS_KEY_ID
export AWS_ACCESS_KEY_SECRET
export AWS_SQS_DETECT_QUEUE_URL
export AWS_SQS_ALERT_QUEUE_URL

mvn spotless:apply

rm -rf target
mvn clean verify
