package algorithms.assignment.graph.topological_sort.result;

import java.util.List;

/**
 * Result of a topological sort operation.
 */
public record TopologicalSortResult<T>(
        List<T> order,
        boolean hasCycle,
        TopologicalSortMetrics metrics
) {
    /**
     * Gets the topological order of vertices (or components).
     * Empty if the graph has a cycle.
     */
    @Override
    public List<T> order() {
        return order;
    }

    /**
     * Returns true if a cycle was detected (graph is not a DAG).
     */
    @Override
    public boolean hasCycle() {
        return hasCycle;
    }

    /**
     * Gets the metrics collected during the sort.
     */
    @Override
    public TopologicalSortMetrics metrics() {
        return metrics;
    }

    @Override
    public String toString() {
        if (hasCycle) {
            return "Topological Sort Result: CYCLE DETECTED (not a DAG)";
        }
        return "Topological Sort Result: " + order;
    }
}

