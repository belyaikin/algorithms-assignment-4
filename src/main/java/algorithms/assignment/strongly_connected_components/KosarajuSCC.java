package algorithms.assignment.strongly_connected_components;

import algorithms.assignment.graph.Graph;
import algorithms.assignment.graph.Vertex;
import algorithms.assignment.graph.Neighbor;
import algorithms.assignment.strongly_connected_components.result.SCCMetrics;
import algorithms.assignment.strongly_connected_components.result.SCCResult;

import java.util.*;

public final class KosarajuSCC<T> {
    private final SCCMetrics metrics = new SCCMetrics();

    public SCCResult<T> findSCCs(Graph<T> graph) {
        metrics.reset();
        metrics.startTimer();

        List<List<Vertex<T>>> sccList = new ArrayList<>();
        Set<Vertex<T>> visited = new HashSet<>();
        Stack<Vertex<T>> stack = new Stack<>();

        for (Vertex<T> vertex : graph.getVertices()) {
            if (!visited.contains(vertex)) {
                fillOrder(vertex, visited, stack);
            }
        }

        Graph<T> transposed = transposeGraph(graph);

        visited.clear();
        while (!stack.isEmpty()) {
            Vertex<T> vertex = transposed.getVertex(stack.pop().getData());
            if (vertex != null && !visited.contains(vertex)) {
                List<Vertex<T>> scc = new ArrayList<>();
                dfs(vertex, visited, scc);
                sccList.add(scc);
                metrics.incrementCounter("scc_found");
            }
        }

        metrics.stopTimer();
        return new SCCResult<>(sccList, metrics);
    }

    private void fillOrder(Vertex<T> v, Set<Vertex<T>> visited, Stack<Vertex<T>> stack) {
        visited.add(v);
        metrics.incrementCounter("dfs_calls");

        for (Neighbor<T> neighbor : v.getNeighbors()) {
            metrics.incrementCounter("edges_examined");
            Vertex<T> next = neighbor.vertex();
            if (!visited.contains(next)) {
                fillOrder(next, visited, stack);
            }
        }
        stack.push(v);
    }

    private void dfs(Vertex<T> v, Set<Vertex<T>> visited, List<Vertex<T>> scc) {
        visited.add(v);
        scc.add(v);
        metrics.incrementCounter("dfs_calls");

        for (Neighbor<T> neighbor : v.getNeighbors()) {
            metrics.incrementCounter("edges_examined");
            Vertex<T> next = neighbor.vertex();
            if (!visited.contains(next)) {
                dfs(next, visited, scc);
            }
        }
    }

    private Graph<T> transposeGraph(Graph<T> graph) {
        Graph<T> transposed = new Graph<>();

        for (Vertex<T> v : graph.getVertices()) {
            transposed.addVertex(new Vertex<>(v.getData()));
        }

        for (Vertex<T> v : graph.getVertices()) {
            for (Neighbor<T> neighbor : v.getNeighbors()) {
                Vertex<T> from = transposed.getVertex(neighbor.vertex().getData());
                Vertex<T> to = transposed.getVertex(v.getData());
                transposed.addEdge(from.getData(), to.getData());
            }
        }

        return transposed;
    }

    public SCCMetrics getMetrics() {
        return metrics;
    }
}
