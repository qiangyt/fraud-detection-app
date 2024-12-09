#!/bin/bash

ENV_FILE=".env"

if [ ! -f "$ENV_FILE" ]; then
  echo "Error: .env file not found!"
  exit 1
fi

export $(grep -v '^#' "$ENV_FILE" | xargs)

AWS_REGION_BASE64=$(echo -n "$AWS_REGION" | base64)
AWS_ACCESS_KEY_ID_BASE64=$(echo -n "$AWS_ACCESS_KEY_ID" | base64)
AWS_ACCESS_KEY_SECRET_BASE64=$(echo -n "$AWS_ACCESS_KEY_SECRET" | base64)
AWS_SQS_DETECT_QUEUE_URL_BASE64=$(echo -n "$AWS_SQS_DETECT_QUEUE_URL" | base64)
AWS_SQS_ALERT_QUEUE_URL_BASE64=$(echo -n "$AWS_SQS_ALERT_QUEUE_URL" | base64)


cat << EOF > fraud-detection-secret.yaml
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
  aws-sqs-alert-queue-url: $AWS_SQS_ALERT_QUEUE_URL_BASE64
EOF

echo "Kubernetes Secret YAML file 'fraud-detection-secret.yaml' has been generated successfully!"
