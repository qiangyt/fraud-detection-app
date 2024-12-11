from graphviz import Digraph

def draw_architecture_diagram():
    diagram = Digraph('Fraud Detection System Architecture', format='png')

    # SQS Queues
    diagram.node('DetectionRequestQueue', 'Detection Request Queue', shape='queue')
    diagram.node('DetectionFailureQueue', 'Detection Failure Queue', shape='queue')
    diagram.node('AlertQueue', 'Alert Queue (FIFO)', shape='queue')

    # Fraud Detection Microservices
    diagram.node('GatewayService', 'Gateway Microservice', shape='box')
    diagram.node('DetectionEngine', 'Detection Engine Microservice', shape='box')
    diagram.node('RuleAdmin', 'Rule Admin Microservice (Optional)', shape='box', style='dashed')

    # Thread Pools
    diagram.node('LongPollingThreadPool', 'Long-Polling Thread Pool', shape='parallelogram')
    diagram.node('DetectionWorkerThreadPool', 'Detection Worker Thread Pool', shape='parallelogram')

    # External Client
    diagram.node('Client', 'Client', shape='ellipse')

    # Logging
    diagram.node('CloudWatch', 'AWS CloudWatch', shape='cylinder')

    # Connections
    # Client to Gateway
    diagram.edge('Client', 'GatewayService', label='Transaction Requests')

    # Gateway to SQS Detection Request Queue
    diagram.edge('GatewayService', 'DetectionRequestQueue', label='Submit Detection Requests')

    # Detection Engine interactions
    diagram.edge('DetectionRequestQueue', 'LongPollingThreadPool', label='Long Polling')
    diagram.edge('LongPollingThreadPool', 'DetectionWorkerThreadPool', label='Dispatch Requests')
    diagram.edge('DetectionWorkerThreadPool', 'DetectionEngine', label='Process Detection')
    diagram.edge('DetectionEngine', 'AlertQueue', label='Fraud Alerts')
    diagram.edge('DetectionEngine', 'DetectionFailureQueue', label='Failed Requests')

    # Alerts to Logging
    diagram.edge('DetectionEngine', 'CloudWatch', label='Alert Metrics')

    # Optional Rule Admin
    diagram.edge('RuleAdmin', 'DetectionEngine', label='Push Updated Rules')

    # Return diagram
    return diagram

# Render and save diagram
diagram = draw_architecture_diagram()
diagram.render('fraud_detection_app_architecture')
