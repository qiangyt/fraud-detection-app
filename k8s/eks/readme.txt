Installing or updating to the latest version of the AWS CLI
https://docs.aws.amazon.com/AmazonECR/latest/userguide/getting-started-cli.html

curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"
unzip awscliv2.zip
sudo ./aws/install

aws configure
aws ecr-public get-login-password --region us-east-1 | docker login --username AWS --password-stdin public.ecr.aws/g8s9f1z6

aws eks describe-cluster --name hsbc --region eu-north-1
aws eks --region eu-north-1 update-kubeconfig --name hsbc

kubectl apply -f aws-secret.yaml