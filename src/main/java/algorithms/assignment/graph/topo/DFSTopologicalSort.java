package algorithms.assignment.graph.topo;

import algorithms.assignment.graph.Graph;
import algorithms.assignment.graph.Neighbor;
import algorithms.assignment.graph.Vertex;

import java.util.*;

/**
 * Topological sort using DFS-based algorithm.
 *
 * Algorithm:
 * 1. Perform DFS from each unvisited vertex
 * 2. After visiting all descendants, add vertex to result (post-order)
 * 3. Reverse the result to get topological order
 * 4. Detect cycles using a recursive stack
 */
public class DFSTopologicalSort<T> {
    private final TopologicalSortMetrics metrics;
    private Set<Vertex<T>> visited;
    private Set<Vertex<T>> recursionStack;
    private Deque<T> stack;
    private boolean cycleDetected;

    public DFSTopologicalSort() {
        this.metrics = new TopologicalSortMetrics();
    }

    /**
     * Performs topological sort on the given graph using DFS.
     *
     * @param graph the directed graph (should be a DAG)
     * @return TopologicalSortResult containing the order and metrics
     */
    public TopologicalSortResult<T> sort(Graph<T> graph) {
        metrics.reset();
        metrics.startTimer();

        // Initialize data structures
        visited = new HashSet<>();
        recursionStack = new HashSet<>();
        stack = new ArrayDeque<>();
        cycleDetected = false;

        // Perform DFS from each unvisited vertex
        for (Vertex<T> vertex : graph.getVertices()) {
            if (!visited.contains(vertex)) {
                dfs(vertex);
                if (cycleDetected) {
                    break;
                }
            }
        }

        metrics.stopTimer();

        if (cycleDetected) {
            // Cycle detected - return empty order
            return new TopologicalSortResult<>(Collections.emptyList(), true, metrics);
        }

        // Convert stack to list (already in topological order)
        List<T> order = new ArrayList<>(stack);
        return new TopologicalSortResult<>(order, false, metrics);
    }

    /**
     * DFS helper method with cycle detection.
     */
    private void dfs(Vertex<T> vertex) {
        if (cycleDetected) {
            return;
        }

        // Check for cycle (back edge)
        if (recursionStack.contains(vertex)) {
            cycleDetected = true;
            return;
        }

        // Already visited in another DFS branch
        if (visited.contains(vertex)) {
            return;
        }

        // Mark as visited and add to recursion stack
        visited.add(vertex);
        recursionStack.add(vertex);
        metrics.incrementCounter("vertices_visited");

        // Visit all neighbors
        for (Neighbor<T> neighbor : vertex.getNeighbors()) {
            Vertex<T> neighborVertex = neighbor.vertex();
            metrics.incrementCounter("edges_traversed");
            dfs(neighborVertex);

            if (cycleDetected) {
                return;
            }
        }

        // Remove from recursion stack and add to result
        recursionStack.remove(vertex);
        stack.push(vertex.getData());
        metrics.incrementCounter("stack_pushes");
    }

    /**
     * Gets the metrics from the last sort operation.
     */
    public TopologicalSortMetrics getMetrics() {
        return metrics;
    }
}

