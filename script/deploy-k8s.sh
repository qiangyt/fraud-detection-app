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

# kubectl create namespace amazon-cloudwatch
# k8s_apply apply -f cloudwatch-agent-configmap.yaml
# curl https://raw.githubusercontent.com/aws-samples/amazon-cloudwatch-container-insights/latest/k8s/deployment-mode/daemonset/container-insights-monitoring/cloudwatch-agent.yaml -o cloudwatch-agent.yaml
# kubectl apply -f cloudwatch-agent.yaml
# kubectl get daemonset -n amazon-cloudwatch
# kubectl get pods -n amazon-cloudwatch
# kubectl logs <cloudwatch-agent-pod-name> -n amazon-cloudwatch

k8s_apply fraud-detection-secret.yaml
k8s_apply fraud-detection-deployment.yaml
k8s_apply fraud-detection-service.yaml
k8s_apply fraud-detection-hpa.yaml

echo "The fraud detection app is successfully deployed to Kubernetes!"
