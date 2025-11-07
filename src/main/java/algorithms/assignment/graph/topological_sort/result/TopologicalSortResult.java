package algorithms.assignment.graph.topological_sort.result;

import java.util.List;

/**
 * Result of a topological sort operation.
 */
public class TopologicalSortResult<T> {
    private final List<T> order;
    private final boolean hasCycle;
    private final TopologicalSortMetrics metrics;

    public TopologicalSortResult(List<T> order, boolean hasCycle, TopologicalSortMetrics metrics) {
        this.order = order;
        this.hasCycle = hasCycle;
        this.metrics = metrics;
    }

    /**
     * Gets the topological order of vertices (or components).
     * Empty if the graph has a cycle.
     */
    public List<T> getOrder() {
        return order;
    }

    /**
     * Returns true if a cycle was detected (graph is not a DAG).
     */
    public boolean hasCycle() {
        return hasCycle;
    }

    /**
     * Gets the metrics collected during the sort.
     */
    public TopologicalSortMetrics getMetrics() {
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

