#!/bin/bash

cd "$project_dir" || exit 1


ENV_FILE="$project_dir/.env"
if [ ! -f "$ENV_FILE" ]; then
  echo "Error: .env file not found!"
  exit 1
fi

source $ENV_FILE
export $(grep -v '^#' "$ENV_FILE" | xargs)

export AWS_REGION
export AWS_ACCESS_KEY_ID
export AWS_ACCESS_KEY_SECRET
export AWS_SQS_DETECT_QUEUE_URL
export AWS_SQS_DETECT_DEAD_LETTER_QUEUE_URL
export AWS_SQS_ALERT_QUEUE_URL


eks_dir="$project_dir/k8s/eks"
