# Build, Deploy, and Run fraud-detection-app

## 1. Local Build

### Preparation

1. **Development Environment**

   - Linux or Mac OS: Due to the use of bash scripts, Windows development environment is not supported. Mac OS environment has not been verified due to limited conditions.
   - Network: Ensure smooth access to Docker Hub, Maven Public Repository, Github, and AWS.

2. **Tools**

   - Common command line tools: git, tee, curl, grep, base64, zip
   - Java 17
   - Docker
   - Kubernetes CLI (`kubectl`)
   - AWS CLI (`aws`) (usually pre-installed in AWS Cloud Shell)

3. **AWS Resources**:
   - Create 1 AWS EKS cluster
   - 3 AWS SQS queues
     - Detection request queue (standard queue)
     - Detection failure queue (standard queue)
     - Alert queue (FIFO queue)
   - AWS CloudWatch Log for logging
   - AWS CloudWatch Metric for alert statistics

4. **Permissions**:
   - IAM role with access to SQS and CloudWatch
   - Configure Kubernetes cluster to use AWS IAM role

### Build Steps

1. Clone the repository

   ```bash
   git clone https://github.com/qiangyt/fraud-detection-app.git
   cd fraud-detection-app
   ```

2. Minimal configuration items (used during the build process for integration testing)

   | # | Configuration Item                      | Example Value         | Description                 |
   |---|----------------------------------------|-----------------------|-----------------------------|
   | 1 | `AWS_REGION`                           | eu-north-1            | AWS region                  |
   | 2 | `AWS_ACCESS_KEY_ID`                    |                       | AWS access key id           |
   | 3 | `AWS_ACCESS_KEY_SECRET`                |                       | AWS access key secret       |
   | 4 | `AWS_SQS_DETECT_QUEUE_URL`             | https://sqs.eu-north-1.amazonaws.com/820242901663/hsbc_fraud_detection               | Detection request queue URL |
   | 5 | `AWS_SQS_DETECT_DEAD_LETTER_QUEUE_URL` | https://sqs.eu-north-1.amazonaws.com/820242901663/hsbc_fraud_detection_dead_letter   | Detection failure queue URL |
   | 6 | `AWS_SQS_ALERT_QUEUE_URL`              | https://sqs.eu-north-1.amazonaws.com/820242901663/hsbc_fraud_alert.fifo              | Alert queue URL, must be FIFO queue |

   Generate the minimal configuration file: copy [../.env.example](../.env.example), modify the configuration values, and save it as `../.env` in the root directory.

3. Execute [../script/build.sh](../script/build.sh), which usually takes about 30 seconds. It will compile the Java code and package it into a Jar file: [../target/fraud-detection-app-0.0.1-SNAPSHOT.jar](../target/fraud-detection-app-0.0.1-SNAPSHOT.jar).

   Note: Since integration tests need to access external nodes like AWS SQS, they may occasionally fail due to network issues. If you encounter failures, ensure external network access is normal and rerun the script.

4. (Optional) Build Docker image

   If you need to build the Docker image yourself, refer to the following:

   First, create a Docker image repository on ECR, set the appropriate IAM permissions, and log in:

   ```bash
   aws ecr create-repository --repository-name <your repository name>
   aws ecr get-login-password --region <region> | docker login --username AWS --password-stdin <your repository address>
   ```

   Then, modify the following variables in [../script/release.sh](../script/release.sh):

   ```bash
   repo_d=<your git working directory>
   registry=<your repository address>
   ```

   Finally, execute [../script/release.sh](../script/release.sh).

   Note: I have prepared a Docker image that can be used directly (`public.ecr.aws/g8s9f1z6/fraud-detection-app:latest`). After successfully building your own image, you need to manually modify the default Docker image in the K8S deployment manifest ([../k8s/eks/fraud-detection-deployment.yaml](../k8s/eks/fraud-detection-deployment.yaml)).

## 2. Deploy to AWS EKS

### Preparation

- Configure the local K8S Context.

  Execute the following commands:

   ```bash
   K8S_REGION=eu-north-1  # Change to the actual AWS region
   K8S_CLUSTER=charming-grunge-sparrow  # Change to the actual EKS Cluster
   
   aws eks describe-cluster --name $K8S_CLUSTER --region $K8S_REGION
   aws eks --region $K8S_REGION update-kubeconfig --name $K8S_CLUSTER

   kubectl get pods  # Ensure the Kubernetes context points to the actual EKS cluster
   ```

- Generate K8S Secret manifest

  Execute the following command:

   ```bash
   script/generate-k8s-secret.sh
   ```

   This script will read the configuration items from [../.env](../.env), base64 encode them, and write them to [../k8s/eks/fraud-detection-secret.yaml](../k8s/eks/fraud-detection-secret.yaml) (to be used later).

### Apply Kubernetes configuration files

  Execute the following command:

   ```bash
   script/deploy-k8s.sh
   ```

   This script will deploy:

   - Secret (generated in the previous step): [../k8s/eks/fraud-detection-secret.yaml](../k8s/eks/fraud-detection-secret.yaml)
   - Deployment: [../k8s/eks/fraud-detection-deployment.yaml](../k8s/eks/fraud-detection-deployment.yaml)
   - Service: [../k8s/eks/fraud-detection-service.yaml](../k8s/eks/fraud-detection-service.yaml)
   - HPA: [../k8s/eks/fraud-detection-hpa.yaml](../k8s/eks/fraud-detection-hpa.yaml)
   - CloudWatch Agent DaemonSet

   Then refer to [Test fraud-detection-app](../ut_and_it.md) to test and verify in AWS Cloud Shell.

