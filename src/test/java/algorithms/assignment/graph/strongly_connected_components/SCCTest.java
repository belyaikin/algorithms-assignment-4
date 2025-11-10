package algorithms.assignment.graph.strongly_connected_components;

import algorithms.assignment.graph.Graph;
import algorithms.assignment.graph.Vertex;
import algorithms.assignment.graph.strongly_connected_components.result.SCCMetrics;
import algorithms.assignment.graph.strongly_connected_components.result.SCCResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SCCTest {
    private KosarajuSCC<Integer> kosaraju;
    private TarjanSCC<Integer> tarjan;

    @BeforeEach
    public void setUp() {
        kosaraju = new KosarajuSCC<>();
        tarjan = new TarjanSCC<>();
    }

    @Test
    public void testSingleSCC() {
        Graph<Integer> graph = new Graph<>();
        graph.addVertex(new Vertex<>(0));
        graph.addVertex(new Vertex<>(1));
        graph.addVertex(new Vertex<>(2));
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 0);

        SCCResult<Integer> kosarajuResult = kosaraju.findSCCs(graph);
        SCCResult<Integer> tarjanResult = tarjan.findSCCs(graph);

        assertEquals(1, kosarajuResult.components().size());
        assertEquals(1, tarjanResult.components().size());
        assertTrue(kosarajuResult.components().get(0).containsAll(List.of(0, 1, 2)));
        assertTrue(tarjanResult.components().get(0).containsAll(List.of(0, 1, 2)));
    }

    @Test
    public void testMultipleSCCs() {
        Graph<Integer> graph = new Graph<>();
        for (int i = 0; i < 6; i++) graph.addVertex(new Vertex<>(i));

        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 0);
        graph.addEdge(3, 4);
        graph.addEdge(4, 5);
        graph.addEdge(5, 3);

        SCCResult<Integer> kosarajuResult = kosaraju.findSCCs(graph);
        SCCResult<Integer> tarjanResult = tarjan.findSCCs(graph);

        assertEquals(2, kosarajuResult.components().size());
        assertEquals(2, tarjanResult.components().size());
    }

    @Test
    public void testDisconnectedGraph() {
        Graph<Integer> graph = new Graph<>();
        graph.addVertex(new Vertex<>(0));
        graph.addVertex(new Vertex<>(1));
        graph.addVertex(new Vertex<>(2));

        SCCResult<Integer> kosarajuResult = kosaraju.findSCCs(graph);
        SCCResult<Integer> tarjanResult = tarjan.findSCCs(graph);

        assertEquals(3, kosarajuResult.components().size());
        assertEquals(3, tarjanResult.components().size());
    }

    @Test
    public void testMetricsCollection() {
        Graph<Integer> graph = new Graph<>();
        graph.addVertex(new Vertex<>(0));
        graph.addVertex(new Vertex<>(1));
        graph.addEdge(0, 1);

        SCCResult<Integer> kosarajuResult = kosaraju.findSCCs(graph);
        SCCMetrics kosarajuMetrics = kosarajuResult.metrics();
        assertTrue(kosarajuMetrics.getElapsedTimeNanos() > 0);
        assertTrue(kosarajuMetrics.getCounter("dfs_calls") > 0);

        SCCResult<Integer> tarjanResult = tarjan.findSCCs(graph);
        SCCMetrics tarjanMetrics = tarjanResult.metrics();
        assertTrue(tarjanMetrics.getElapsedTimeNanos() > 0);
        assertTrue(tarjanMetrics.getCounter("dfs_calls") > 0);
    }
}
