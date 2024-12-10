#!/bin/bash

./test.sh

repo_d=/data/qiangyt/github/qiangyt/fraud-detection-app
tag_suffix=$(date +"%Y%m%d-%H%M%S")

registry=public.ecr.aws/g8s9f1z6/fraud-detection-app
tag=${registry}:${tag_suffix}
tag_latest=${registry}:latest

docker build -t ${tag} ${repo_d}
docker tag ${tag} ${tag_latest}
docker push ${tag}
docker push ${tag_latest}
