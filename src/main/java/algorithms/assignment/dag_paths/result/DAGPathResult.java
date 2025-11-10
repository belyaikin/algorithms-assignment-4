package algorithms.assignment.dag_paths.result;

import java.util.List;
import java.util.Map;

public record DAGPathResult<T>(
        Map<T, Double> distances,
        Map<T, T> parents,
        List<T> order,
        T source,
        double criticalPathLength,
        List<T> criticalPath,
        DAGPathMetrics metrics
) {
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DAG Path Result:\n");
        sb.append("  Source: ").append(source).append("\n");
        sb.append("  Distances: ").append(distances).append("\n");
        sb.append("  Critical Path Length: ").append(criticalPathLength).append("\n");
        sb.append("  Critical Path: ").append(criticalPath).append("\n");
        sb.append(metrics.getSummary());
        return sb.toString();
    }
}