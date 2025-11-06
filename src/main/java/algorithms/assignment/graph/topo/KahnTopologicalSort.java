package algorithms.assignment.graph.topo;

import algorithms.assignment.graph.Graph;
import algorithms.assignment.graph.Neighbor;
import algorithms.assignment.graph.Vertex;

import java.util.*;

/**
 * Topological sort using Kahn's algorithm (BFS-based with in-degree).
 *
 * Algorithm:
 * 1. Compute in-degrees for all vertices
 * 2. Add all vertices with in-degree 0 to a queue
 * 3. While queue is not empty:
 *    - Remove a vertex from queue, add to result
 *    - For each neighbor, decrement in-degree
 *    - If in-degree becomes 0, add to queue
 * 4. If all vertices processed, graph is a DAG; otherwise, cycle detected
 */
public class KahnTopologicalSort<T> {
    private final TopologicalSortMetrics metrics;

    public KahnTopologicalSort() {
        this.metrics = new TopologicalSortMetrics();
    }

    /**
     * Performs topological sort on the given graph using Kahn's algorithm.
     *
     * @param graph the directed graph (should be a DAG)
     * @return TopologicalSortResult containing the order and metrics
     */
    public TopologicalSortResult<T> sort(Graph<T> graph) {
        metrics.reset();
        metrics.startTimer();

        int n = graph.getVertexCount();
        List<T> order = new ArrayList<>();

        // Compute in-degrees
        Map<Vertex<T>, Integer> inDegrees = graph.computeInDegrees();
        metrics.incrementCounter("in-degree_computations");

        // Queue for vertices with in-degree 0
        Queue<Vertex<T>> queue = new LinkedList<>();

        // Add all vertices with in-degree 0
        for (Map.Entry<Vertex<T>, Integer> entry : inDegrees.entrySet()) {
            if (entry.getValue() == 0) {
                queue.offer(entry.getKey());
                metrics.incrementCounter("queue_pushes");
            }
        }

        // Process vertices
        while (!queue.isEmpty()) {
            Vertex<T> current = queue.poll();
            metrics.incrementCounter("queue_pops");
            order.add(current.getData());
            metrics.incrementCounter("vertices_processed");

            // Process all neighbors
            for (Neighbor<T> neighbor : current.getNeighbors()) {
                Vertex<T> neighborVertex = neighbor.vertex();
                metrics.incrementCounter("edges_examined");

                // Decrement in-degree
                int newInDegree = inDegrees.get(neighborVertex) - 1;
                inDegrees.put(neighborVertex, newInDegree);

                // If in-degree becomes 0, add to queue
                if (newInDegree == 0) {
                    queue.offer(neighborVertex);
                    metrics.incrementCounter("queue_pushes");
                }
            }
        }

        metrics.stopTimer();

        // Check if all vertices were processed
        boolean hasCycle = order.size() != n;

        if (hasCycle) {
            // Cycle detected - return empty order
            return new TopologicalSortResult<>(Collections.emptyList(), true, metrics);
        }

        return new TopologicalSortResult<>(order, false, metrics);
    }

    /**
     * Gets the metrics from the last sort operation.
     */
    public TopologicalSortMetrics getMetrics() {
        return metrics;
    }
}

