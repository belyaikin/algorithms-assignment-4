package algorithms.assignment.topological_sort;

import algorithms.assignment.graph.Graph;
import algorithms.assignment.graph.Neighbor;
import algorithms.assignment.graph.Vertex;
import algorithms.assignment.topological_sort.result.TopologicalSortMetrics;
import algorithms.assignment.topological_sort.result.TopologicalSortResult;

import java.util.*;

public final class KahnTopologicalSort<T> {
    private final TopologicalSortMetrics metrics;

    public KahnTopologicalSort() {
        this.metrics = new TopologicalSortMetrics();
    }

    public TopologicalSortResult<T> sort(Graph<T> graph) {
        metrics.reset();
        metrics.startTimer();

        int n = graph.getVertexCount();
        List<T> order = new ArrayList<>();

        Map<Vertex<T>, Integer> inDegrees = graph.computeInDegrees();
        metrics.incrementCounter("in-degree_computations");

        Queue<Vertex<T>> queue = new LinkedList<>();

        for (Map.Entry<Vertex<T>, Integer> entry : inDegrees.entrySet()) {
            if (entry.getValue() == 0) {
                queue.offer(entry.getKey());
                metrics.incrementCounter("queue_pushes");
            }
        }

        while (!queue.isEmpty()) {
            Vertex<T> current = queue.poll();
            metrics.incrementCounter("queue_pops");
            order.add(current.getData());
            metrics.incrementCounter("vertices_processed");

            for (Neighbor<T> neighbor : current.getNeighbors()) {
                Vertex<T> neighborVertex = neighbor.vertex();
                metrics.incrementCounter("edges_examined");

                int newInDegree = inDegrees.get(neighborVertex) - 1;
                inDegrees.put(neighborVertex, newInDegree);

                if (newInDegree == 0) {
                    queue.offer(neighborVertex);
                    metrics.incrementCounter("queue_pushes");
                }
            }
        }

        metrics.stopTimer();

        boolean hasCycle = order.size() != n;

        if (hasCycle) {
            return new TopologicalSortResult<>(Collections.emptyList(), true, metrics);
        }

        return new TopologicalSortResult<>(order, false, metrics);
    }

    public TopologicalSortMetrics getMetrics() {
        return metrics;
    }
}

