package algorithms.assignment.graph.strongly_connected_components.result;

import algorithms.assignment.graph.Vertex;

import java.util.List;

public record SCCResult<T>(
        List<List<Vertex<T>>> components,
        SCCMetrics metrics
) {
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SCC Result:\n");
        for (int i = 0; i < components.size(); i++) {
            sb.append("  Component ").append(i + 1).append(": ").append(components.get(i)).append("\n");
        }
        sb.append(metrics.getSummary());
        return sb.toString();
    }
}
