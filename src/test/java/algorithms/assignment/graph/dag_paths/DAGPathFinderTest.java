package algorithms.assignment.graph.dag_paths;

import algorithms.assignment.dag_paths.DAGPathFinder;
import algorithms.assignment.dag_paths.result.DAGPathResult;
import algorithms.assignment.graph.Graph;
import algorithms.assignment.graph.Vertex;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DAGPathFinderTest {
    @Test
    public void testShortestAndLongestPaths() {
        Graph<String> graph = new Graph<>();
        graph.addVertex(new Vertex<>("A"));
        graph.addVertex(new Vertex<>("B"));
        graph.addVertex(new Vertex<>("C"));
        graph.addEdge("A", "B", 2);
        graph.addEdge("A", "C", 4);
        graph.addEdge("B", "C", 1);

        DAGPathFinder<String> finder = new DAGPathFinder<>();
        DAGPathResult<String> shortest = finder.shortestPaths(graph, "A");
        DAGPathResult<String> longest = finder.longestPaths(graph, "A");

        assertEquals(0.0, shortest.distances().get("A"));
        assertEquals(3.0, shortest.distances().get("C"));
        assertEquals(4.0, longest.distances().get("C"));
    }
}
