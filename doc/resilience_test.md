# Resilience Test Report for fraud-detection-app

1. Execute the following command to verify the configuration and current status of HPA:

   ```bash
   kubectl get hpa
   ```

   And get the Cluster IP for later usage:

   ```bash
   kubectl get svc fraud-detection
   ```

2. Use `kubectl run` to create a load generator to simulate high load and trigger scaling by HPA:

   ```bash
   kubectl run -i --tty load-generator --image=busybox --restart=Never -- /bin/bash

   # Run inside load-generator
   URL="http://<cluster-ip>:8080/rest/detection" # replace the cluster ip

   while true    \
   do            \
       curl --request POST --url "$URL" --header "content-type: application/json" --data '{"accountId": "resilence-test-account-1","amount": 999,"memo": "N/A"}' \
       echo ""   \
       sleep 1   \
   done
   ```

3. In new terminal (or AWS Cloud Shell), execute the following command to observe the scaling of HPA in real-time:

   ```bash
   kubectl get hpa fraud-detection --watch
   ```

   Observations:
   - `REPLICAS` increased from 2 to 8, indicating that HPA scaled the number of Pods based on high load.
   - `TARGETS` reached close to the target value (70%).

   This indicates that HPA detected the load and scaled the Pods.

4. In new terminal (or AWS Cloud Shell), delete the load-generator to simulate load reduction:

   ```bash
   kubectl delete pod load-generator
   ```

5. Continue to monitor HPA and observe the number of Pod replicas:

   ```bash
   kubectl get hpa fraud-detection --watch
   ```

   Observations:
   - `REPLICAS` decreased from 8, indicating that HPA scaled down the number of Pods based on load reduction.
   - `TARGETS` dropped below the target value (<70%).

   This indicates that HPA detected the load reduction and scaled down the Pods.

   Finally, the number of Pod replicas returned to `MINPODS` (2), system load stabilized.

6. Test result summary:

   - Scaling up: During the simulated high load, HPA dynamically scaled the Pods based on CPU usage, from 2 to 8. The test proved that HPA can quickly respond to load changes.
   - Scaling down: After removing the high load, HPA gradually scaled down the number of Pods, returning to the minimum replicas (2).

   This test confirms the performance of HPA in the following aspects:

   - Ability to dynamically scale resources up and down, ensuring efficient operation during load fluctuations.
   - Maximizing resource utilization while avoiding overload or resource wastage.

