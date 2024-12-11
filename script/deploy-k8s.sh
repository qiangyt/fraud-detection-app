#!/bin/bash

ENV_FILE=".env"

work_dir="$(pwd)"
script_dir="$(dirname "$(readlink -f "$0")")"
project_dir="$(dirname "$script_dir")"

source "$script_dir/common.sh"

function k8s_apply() {
  local _manifest_file=$1  
  kubectl apply -f $eks_dir/$_manifest_file
  echo "Kubernetes $_manifest_file is applied successfully"
}

k8s_apply fraud-detection-secret.yaml
k8s_apply fraud-detection-deployment.yaml
k8s_apply fraud-detection-service.yaml
k8s_apply fraud-detection-hpa.yaml

echo "The fraud detection app is successfully deployed to Kubernetes!"
