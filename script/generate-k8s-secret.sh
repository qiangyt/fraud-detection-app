#!/bin/bash

ENV_FILE=".env"

work_dir="$(pwd)"
script_dir="$(dirname "$(readlink -f "$0")")"
project_dir="$(dirname "$script_dir")"

source "$script_dir/common.sh"




AWS_REGION_BASE64=$(echo -n "$AWS_REGION" | base64)
AWS_ACCESS_KEY_ID_BASE64=$(echo -n "$AWS_ACCESS_KEY_ID" | base64)
AWS_ACCESS_KEY_SECRET_BASE64=$(echo -n "$AWS_ACCESS_KEY_SECRET" | base64)
AWS_SQS_DETECT_QUEUE_URL_BASE64=$(echo -n "$AWS_SQS_DETECT_QUEUE_URL" | base64)
AWS_SQS_DETECT_DEAD_LETTER_QUEUE_URL_BASE64=$(echo -n "$AWS_SQS_DETECT_DEAD_LETTER_QUEUE_URL" | base64)
AWS_SQS_ALERT_QUEUE_URL_BASE64=$(echo -n "$AWS_SQS_ALERT_QUEUE_URL" | base64)

secret_file=$eks_dir/fraud-detection-secret.yaml

tee $secret_file << EOF
apiVersion: v1
kind: Secret
metadata:
  name: aws-secret
type: Opaque
data:
  aws-region: $AWS_REGION_BASE64
  aws-access-key-id: $AWS_ACCESS_KEY_ID_BASE64
  aws-access-key-secret: $AWS_ACCESS_KEY_SECRET_BASE64
  aws-sqs-detect-queue-url: $AWS_SQS_DETECT_QUEUE_URL_BASE64
  aws-sqs-detect-dead-letter-queue-url: $AWS_SQS_DETECT_DEAD_LETTER_QUEUE_URL_BASE64
  aws-sqs-alert-queue-url: $AWS_SQS_ALERT_QUEUE_URL_BASE64
EOF

echo "Kubernetes Secret YAML file '$secret_file' is generated successfully!"
