package algorithms.assignment.graph.topo;

import algorithms.assignment.graph.Graph;
import algorithms.assignment.graph.Vertex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for topological sort algorithms.
 */
public class TopologicalSortTest {
    private KahnTopologicalSort<Integer> kahn;
    private DFSTopologicalSort<Integer> dfs;

    @BeforeEach
    public void setUp() {
        kahn = new KahnTopologicalSort<>();
        dfs = new DFSTopologicalSort<>();
    }

    @Test
    public void testSimpleDAG() {
        // Simple DAG: 0 -> 1 -> 2
        Graph<Integer> graph = new Graph<>();
        graph.addVertex(new Vertex<>(0));
        graph.addVertex(new Vertex<>(1));
        graph.addVertex(new Vertex<>(2));
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);

        // Test Kahn's algorithm
        TopologicalSortResult<Integer> kahnResult = kahn.sort(graph);
        assertFalse(kahnResult.hasCycle());
        assertEquals(3, kahnResult.getOrder().size());
        List<Integer> kahnOrder = kahnResult.getOrder();
        assertTrue(kahnOrder.indexOf(0) < kahnOrder.indexOf(1));
        assertTrue(kahnOrder.indexOf(1) < kahnOrder.indexOf(2));

        // Test DFS algorithm
        TopologicalSortResult<Integer> dfsResult = dfs.sort(graph);
        assertFalse(dfsResult.hasCycle());
        assertEquals(3, dfsResult.getOrder().size());
        List<Integer> dfsOrder = dfsResult.getOrder();
        assertTrue(dfsOrder.indexOf(0) < dfsOrder.indexOf(1));
        assertTrue(dfsOrder.indexOf(1) < dfsOrder.indexOf(2));
    }

    @Test
    public void testDiamondDAG() {
        // Diamond DAG: 0 -> {1, 2} -> 3
        Graph<Integer> graph = new Graph<>();
        for (int i = 0; i < 4; i++) {
            graph.addVertex(new Vertex<>(i));
        }
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 3);

        // Test Kahn's algorithm
        TopologicalSortResult<Integer> kahnResult = kahn.sort(graph);
        assertFalse(kahnResult.hasCycle());
        assertEquals(4, kahnResult.getOrder().size());
        List<Integer> kahnOrder = kahnResult.getOrder();
        assertTrue(kahnOrder.indexOf(0) < kahnOrder.indexOf(1));
        assertTrue(kahnOrder.indexOf(0) < kahnOrder.indexOf(2));
        assertTrue(kahnOrder.indexOf(1) < kahnOrder.indexOf(3));
        assertTrue(kahnOrder.indexOf(2) < kahnOrder.indexOf(3));

        // Test DFS algorithm
        TopologicalSortResult<Integer> dfsResult = dfs.sort(graph);
        assertFalse(dfsResult.hasCycle());
        assertEquals(4, dfsResult.getOrder().size());
        List<Integer> dfsOrder = dfsResult.getOrder();
        assertTrue(dfsOrder.indexOf(0) < dfsOrder.indexOf(1));
        assertTrue(dfsOrder.indexOf(0) < dfsOrder.indexOf(2));
        assertTrue(dfsOrder.indexOf(1) < dfsOrder.indexOf(3));
        assertTrue(dfsOrder.indexOf(2) < dfsOrder.indexOf(3));
    }

    @Test
    public void testDisconnectedDAG() {
        // Two disconnected components: 0 -> 1 and 2 -> 3
        Graph<Integer> graph = new Graph<>();
        for (int i = 0; i < 4; i++) {
            graph.addVertex(new Vertex<>(i));
        }
        graph.addEdge(0, 1);
        graph.addEdge(2, 3);

        // Test Kahn's algorithm
        TopologicalSortResult<Integer> kahnResult = kahn.sort(graph);
        assertFalse(kahnResult.hasCycle());
        assertEquals(4, kahnResult.getOrder().size());

        // Test DFS algorithm
        TopologicalSortResult<Integer> dfsResult = dfs.sort(graph);
        assertFalse(dfsResult.hasCycle());
        assertEquals(4, dfsResult.getOrder().size());
    }

    @Test
    public void testCyclicGraph() {
        // Cyclic graph: 0 -> 1 -> 2 -> 0
        Graph<Integer> graph = new Graph<>();
        for (int i = 0; i < 3; i++) {
            graph.addVertex(new Vertex<>(i));
        }
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 0);

        // Test Kahn's algorithm
        TopologicalSortResult<Integer> kahnResult = kahn.sort(graph);
        assertTrue(kahnResult.hasCycle());
        assertTrue(kahnResult.getOrder().isEmpty());

        // Test DFS algorithm
        TopologicalSortResult<Integer> dfsResult = dfs.sort(graph);
        assertTrue(dfsResult.hasCycle());
        assertTrue(dfsResult.getOrder().isEmpty());
    }

    @Test
    public void testSelfLoop() {
        // Graph with self-loop: 0 -> 0
        Graph<Integer> graph = new Graph<>();
        graph.addVertex(new Vertex<>(0));
        graph.addEdge(0, 0);

        // Test Kahn's algorithm
        TopologicalSortResult<Integer> kahnResult = kahn.sort(graph);
        assertTrue(kahnResult.hasCycle());

        // Test DFS algorithm
        TopologicalSortResult<Integer> dfsResult = dfs.sort(graph);
        assertTrue(dfsResult.hasCycle());
    }

    @Test
    public void testSingleVertex() {
        // Single vertex with no edges
        Graph<Integer> graph = new Graph<>();
        graph.addVertex(new Vertex<>(0));

        // Test Kahn's algorithm
        TopologicalSortResult<Integer> kahnResult = kahn.sort(graph);
        assertFalse(kahnResult.hasCycle());
        assertEquals(1, kahnResult.getOrder().size());
        assertEquals(0, kahnResult.getOrder().get(0));

        // Test DFS algorithm
        TopologicalSortResult<Integer> dfsResult = dfs.sort(graph);
        assertFalse(dfsResult.hasCycle());
        assertEquals(1, dfsResult.getOrder().size());
        assertEquals(0, dfsResult.getOrder().get(0));
    }

    @Test
    public void testComplexDAG() {
        // More complex DAG with multiple paths
        Graph<Integer> graph = new Graph<>();
        for (int i = 0; i < 6; i++) {
            graph.addVertex(new Vertex<>(i));
        }
        graph.addEdge(5, 2);
        graph.addEdge(5, 0);
        graph.addEdge(4, 0);
        graph.addEdge(4, 1);
        graph.addEdge(2, 3);
        graph.addEdge(3, 1);

        // Test Kahn's algorithm
        TopologicalSortResult<Integer> kahnResult = kahn.sort(graph);
        assertFalse(kahnResult.hasCycle());
        assertEquals(6, kahnResult.getOrder().size());

        // Verify partial order is maintained
        List<Integer> kahnOrder = kahnResult.getOrder();
        assertTrue(kahnOrder.indexOf(5) < kahnOrder.indexOf(2));
        assertTrue(kahnOrder.indexOf(5) < kahnOrder.indexOf(0));
        assertTrue(kahnOrder.indexOf(4) < kahnOrder.indexOf(0));
        assertTrue(kahnOrder.indexOf(4) < kahnOrder.indexOf(1));
        assertTrue(kahnOrder.indexOf(2) < kahnOrder.indexOf(3));
        assertTrue(kahnOrder.indexOf(3) < kahnOrder.indexOf(1));

        // Test DFS algorithm
        TopologicalSortResult<Integer> dfsResult = dfs.sort(graph);
        assertFalse(dfsResult.hasCycle());
        assertEquals(6, dfsResult.getOrder().size());

        // Verify partial order is maintained
        List<Integer> dfsOrder = dfsResult.getOrder();
        assertTrue(dfsOrder.indexOf(5) < dfsOrder.indexOf(2));
        assertTrue(dfsOrder.indexOf(5) < dfsOrder.indexOf(0));
        assertTrue(dfsOrder.indexOf(4) < dfsOrder.indexOf(0));
        assertTrue(dfsOrder.indexOf(4) < dfsOrder.indexOf(1));
        assertTrue(dfsOrder.indexOf(2) < dfsOrder.indexOf(3));
        assertTrue(dfsOrder.indexOf(3) < dfsOrder.indexOf(1));
    }

    @Test
    public void testMetricsCollection() {
        // Test that metrics are being collected
        Graph<Integer> graph = new Graph<>();
        for (int i = 0; i < 3; i++) {
            graph.addVertex(new Vertex<>(i));
        }
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);

        // Test Kahn metrics
        TopologicalSortResult<Integer> kahnResult = kahn.sort(graph);
        TopologicalSortMetrics kahnMetrics = kahnResult.getMetrics();
        assertTrue(kahnMetrics.getElapsedTimeNanos() > 0);
        assertTrue(kahnMetrics.getCounter("vertices_processed") > 0);

        // Test DFS metrics
        TopologicalSortResult<Integer> dfsResult = dfs.sort(graph);
        TopologicalSortMetrics dfsMetrics = dfsResult.getMetrics();
        assertTrue(dfsMetrics.getElapsedTimeNanos() > 0);
        assertTrue(dfsMetrics.getCounter("vertices_visited") > 0);
    }
}

