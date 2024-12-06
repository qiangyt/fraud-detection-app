# Install a single-node k8s/k0s cluster

## References

- <https://docs.k0sproject.io/stable/install/>

- <https://docs.k0sproject.io/stable/k0sctl-install/#4-access-the-cluster>

- <https://github.com/k0sproject/k0sctl#installation>

## update the HTTP_PROXY if needed

    ```shell
    sudo ./install_k0s.sh
    ```

## The k0s install sub-command installs k0s as a system service on the local host that is running one of the supported init systems: Systemd or OpenRC. Run the following command to install a single node k0s that includes the controller and worker functions with the default configuration

    ```shell
    sudo k0s install controller --single
    ```

## To start the k0s service, run

    ```shell
    sudo k0s start
    sudo k0s status
    ```

## Install the cluster

    `sudo k0sctl apply --config k0sctl.yaml`

    ```
    sudo k0s kubectl get nodes
    ```

## To access your k0s cluster, use k0sctl to generate a kubeconfig for the purpose

    ```shell
    k0sctl kubeconfig > kubeconfig
    ```
    
    With the kubeconfig, you can access your cluster using either kubectl or Lens.

    ```shell
    kubectl get pods --kubeconfig kubeconfig -A
    ```

## Re-install the service

    ```shell
    sudo k0s install controller --single --force
    sudo systemctl daemon-reload
    ```

## Stop the service

    ```shell
    sudo k0s reset
    ```
    Reboot the system.
