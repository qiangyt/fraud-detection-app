#!/bin/bash

source .env

export AWS_REGION
export AWS_ACCESS_KEY_ID
export AWS_ACCESS_KEY_SECRET
export AWS_SQS_QUEUE_URL

mvn clean spring-boot:run
