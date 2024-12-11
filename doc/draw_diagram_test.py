from unittest import TestCase, main
from graphviz import Digraph
from doc.draw_diagram import draw_architecture_diagram

class TestDrawArchitectureDiagram(TestCase):
    def test_draw_architecture_diagram_happy_path(self):
        """
        This test checks if the function `draw_architecture_diagram` generates a valid diagram with all expected nodes and edges.
        """
        # Arrange
        expected_nodes = {
            'DetectionRequestQueue', 'DetectionFailureQueue', 'AlertQueue',
            'GatewayService', 'DetectionEngine', 'RuleAdmin',
            'LongPollingThreadPool', 'DetectionWorkerThreadPool',
            'Client', 'CloudWatch'
        }
        expected_edges = [
            ('Client', 'GatewayService'),
            ('GatewayService', 'DetectionRequestQueue'),
            ('DetectionRequestQueue', 'LongPollingThreadPool'),
            ('LongPollingThreadPool', 'DetectionWorkerThreadPool'),
            ('DetectionWorkerThreadPool', 'DetectionEngine'),
            ('DetectionEngine', 'AlertQueue'),
            ('DetectionEngine', 'DetectionFailureQueue'),
            ('DetectionEngine', 'CloudWatch')
        ]

        # Act
        diagram = draw_architecture_diagram()
        nodes = set(diagram.body)
        edges = [edge for edge in diagram.edges]

        # Assert
        self.assertEqual(len(nodes), len(expected_nodes))
        self.assertTrue(all(node in nodes for node in expected_nodes))
        self.assertEqual(len(edges), len(expected_edges))
        self.assertTrue(all(edge in edges for edge in expected_edges))

    def test_draw_architecture_diagram_missing_node(self):
        """
        This test checks if the function `draw_architecture_diagram` raises an error when a required node is missing.
        """
        # Arrange
        diagram = Digraph('Fraud Detection System Architecture', format='png')
        diagram.node('DetectionRequestQueue', 'Detection Request Queue', shape='queue')
        diagram.node('DetectionFailureQueue', 'Detection Failure Queue', shape='queue')
        diagram.node('AlertQueue', 'Alert Queue (FIFO)', shape='queue')

        # Act & Assert
        with self.assertRaises(ValueError):
            draw_architecture_diagram()

    def test_draw_architecture_diagram_missing_edge(self):
        """
        This test checks if the function `draw_architecture_diagram` raises an error when a required edge is missing.
        """
        # Arrange
        diagram = Digraph('Fraud Detection System Architecture', format='png')
        diagram.node('Client', 'Client', shape='ellipse')
        diagram.node('GatewayService', 'Gateway Microservice', shape='box')

        # Act & Assert
        with self.assertRaises(ValueError):
            draw_architecture_diagram()

    def test_draw_architecture_diagram_invalid_node_shape(self):
        """
        This test checks if the function `draw_architecture_diagram` raises an error when a node has an invalid shape.
        """
        # Arrange
        diagram = Digraph('Fraud Detection System Architecture', format='png')
        diagram.node('Client', 'Client', shape='invalid')

        # Act & Assert
        with self.assertRaises(ValueError):
            draw_architecture_diagram()

if __name__ == '__main__':
    main()
