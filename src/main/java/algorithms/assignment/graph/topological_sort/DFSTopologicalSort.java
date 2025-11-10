package algorithms.assignment.graph.topological_sort;

import algorithms.assignment.graph.Graph;
import algorithms.assignment.graph.Neighbor;
import algorithms.assignment.graph.Vertex;
import algorithms.assignment.graph.topological_sort.result.TopologicalSortMetrics;
import algorithms.assignment.graph.topological_sort.result.TopologicalSortResult;

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
public final class DFSTopologicalSort<T> {
    private final TopologicalSortMetrics metrics;
    private Set<Vertex<T>> visited;
    private Set<Vertex<T>> recursionStack;
    private Deque<T> stack;
    private boolean cycleDetected;

    public DFSTopologicalSort() {
        this.metrics = new TopologicalSortMetrics();
    }

    public TopologicalSortResult<T> sort(Graph<T> graph) {
        metrics.reset();
        metrics.startTimer();

        visited = new HashSet<>();
        recursionStack = new HashSet<>();
        stack = new ArrayDeque<>();
        cycleDetected = false;

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
            return new TopologicalSortResult<>(Collections.emptyList(), true, metrics);
        }

        List<T> order = new ArrayList<>(stack);
        return new TopologicalSortResult<>(order, false, metrics);
    }

    private void dfs(Vertex<T> vertex) {
        if (cycleDetected) {
            return;
        }

        if (recursionStack.contains(vertex)) {
            cycleDetected = true;
            return;
        }

        if (visited.contains(vertex)) {
            return;
        }

        visited.add(vertex);
        recursionStack.add(vertex);
        metrics.incrementCounter("vertices_visited");

        for (Neighbor<T> neighbor : vertex.getNeighbors()) {
            Vertex<T> neighborVertex = neighbor.vertex();
            metrics.incrementCounter("edges_traversed");
            dfs(neighborVertex);

            if (cycleDetected) {
                return;
            }
        }

        recursionStack.remove(vertex);
        stack.push(vertex.getData());
        metrics.incrementCounter("stack_pushes");
    }

    public TopologicalSortMetrics getMetrics() {
        return metrics;
    }
}

