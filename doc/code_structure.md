# Code Structure

  ```
  src/main/java/qiangyt
    └── fraud_detection
        ├── app                     # the server-side application code
        │   ├── alert               # alerter interfaces and implementations
        │   ├── config              # configuration properties and bean factory
        │   ├── controller          # RESTful API controller and the SQS long-polling controller
        │   ├── engine              # Detection engine and implementations
        │   ├── queue               # The SQS queue sender codes        │   │   
        │   └── service             # The entry of detection process
        │       
        ├── client                  # client side code (used by integration test)
        │   └── v1
        │       └── client
        │           └── rest
        │           
        ├── framework               # non-app-specific classes which are common for many applications
        │   
        └── sdk                     # app-specific classes that are shared by client and app packages
    ```
