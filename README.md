# fraud-detection-app

## Deployment Instructions

cd k8s/eks
kubectl apply -f fraud-detection-secret.yaml
kubectl apply -f fraud-detection.yaml
kubectl get deployments
kubectl get services