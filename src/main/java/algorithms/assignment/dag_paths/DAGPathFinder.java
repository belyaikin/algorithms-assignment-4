package algorithms.assignment.dag_paths;

import algorithms.assignment.dag_paths.result.DAGPathMetrics;
import algorithms.assignment.dag_paths.result.DAGPathResult;
import algorithms.assignment.graph.Graph;
import algorithms.assignment.graph.Neighbor;
import algorithms.assignment.graph.Vertex;
import algorithms.assignment.topological_sort.KahnTopologicalSort;
import algorithms.assignment.topological_sort.result.TopologicalSortResult;

import java.util.*;

public final class DAGPathFinder<T> {
    private final DAGPathMetrics metrics;

    public DAGPathFinder() {
        this.metrics = new DAGPathMetrics();
    }

    public DAGPathResult<T> shortestPaths(Graph<T> graph, T source) {
        metrics.reset();
        metrics.startTimer();

        KahnTopologicalSort<T> topo = new KahnTopologicalSort<>();
        TopologicalSortResult<T> topoResult = topo.sort(graph);
        List<T> order = topoResult.order();

        Map<T, Double> dist = new HashMap<>();
        Map<T, T> parent = new HashMap<>();

        for (Vertex<T> v : graph.getVertices()) {
            dist.put(v.getData(), Double.POSITIVE_INFINITY);
        }
        dist.put(source, 0.0);

        for (T u : order) {
            if (dist.get(u) != Double.POSITIVE_INFINITY) {
                Vertex<T> vertex = graph.getVertex(u);
                for (Neighbor<T> neighbor : vertex.getNeighbors()) {
                    metrics.incrementCounter("edges_examined");
                    T v = neighbor.vertex().getData();
                    double weight = neighbor.edge().getWeight();
                    double newDist = dist.get(u) + weight;
                    if (newDist < dist.get(v)) {
                        dist.put(v, newDist);
                        parent.put(v, u);
                        metrics.incrementCounter("distance_updates");
                    }
                }
            }
        }

        metrics.stopTimer();

        return new DAGPathResult<>(
                dist, parent, order, source,
                Double.NaN, List.of(), metrics
        );
    }

    public DAGPathResult<T> longestPaths(Graph<T> graph, T source) {
        metrics.reset();
        metrics.startTimer();

        KahnTopologicalSort<T> topo = new KahnTopologicalSort<>();
        TopologicalSortResult<T> topoResult = topo.sort(graph);
        List<T> order = topoResult.order();

        Map<T, Double> dist = new HashMap<>();
        Map<T, T> parent = new HashMap<>();

        for (Vertex<T> v : graph.getVertices()) {
            dist.put(v.getData(), Double.NEGATIVE_INFINITY);
        }
        dist.put(source, 0.0);

        for (T u : order) {
            if (dist.get(u) != Double.NEGATIVE_INFINITY) {
                Vertex<T> vertex = graph.getVertex(u);
                for (Neighbor<T> neighbor : vertex.getNeighbors()) {
                    metrics.incrementCounter("edges_examined");
                    T v = neighbor.vertex().getData();
                    double weight = neighbor.edge().getWeight();
                    double newDist = dist.get(u) + weight;
                    if (newDist > dist.get(v)) {
                        dist.put(v, newDist);
                        parent.put(v, u);
                        metrics.incrementCounter("distance_updates");
                    }
                }
            }
        }

        double maxDist = Double.NEGATIVE_INFINITY;
        T endVertex = null;
        for (Map.Entry<T, Double> entry : dist.entrySet()) {
            if (entry.getValue() > maxDist) {
                maxDist = entry.getValue();
                endVertex = entry.getKey();
            }
        }

        List<T> criticalPath = new ArrayList<>();
        T current = endVertex;
        while (current != null) {
            criticalPath.add(current);
            current = parent.get(current);
        }
        Collections.reverse(criticalPath);

        metrics.stopTimer();

        return new DAGPathResult<>(
                dist, parent, order, source,
                maxDist, criticalPath, metrics
        );
    }

    public DAGPathMetrics getMetrics() {
        return metrics;
    }
}