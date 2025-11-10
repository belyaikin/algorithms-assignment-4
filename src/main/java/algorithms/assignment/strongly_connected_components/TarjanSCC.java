package algorithms.assignment.strongly_connected_components;

import algorithms.assignment.graph.Graph;
import algorithms.assignment.graph.Vertex;
import algorithms.assignment.graph.Neighbor;
import algorithms.assignment.strongly_connected_components.result.SCCMetrics;
import algorithms.assignment.strongly_connected_components.result.SCCResult;

import java.util.*;

public final class TarjanSCC<T> {
    private final SCCMetrics metrics = new SCCMetrics();

    public SCCResult<T> findSCCs(Graph<T> graph) {
        metrics.reset();
        metrics.startTimer();

        List<List<Vertex<T>>> sccList = new ArrayList<>();
        Map<Vertex<T>, Integer> indexMap = new HashMap<>();
        Map<Vertex<T>, Integer> lowlinkMap = new HashMap<>();
        Stack<Vertex<T>> stack = new Stack<>();
        Set<Vertex<T>> onStack = new HashSet<>();
        int[] index = {0};

        for (Vertex<T> v : graph.getVertices()) {
            if (!indexMap.containsKey(v)) {
                strongConnect(v, indexMap, lowlinkMap, onStack, stack, index, sccList);
            }
        }

        metrics.stopTimer();
        metrics.incrementCounter("scc_count");

        return new SCCResult<>(sccList, metrics);
    }

    private void strongConnect(Vertex<T> v,
                               Map<Vertex<T>, Integer> indexMap,
                               Map<Vertex<T>, Integer> lowlinkMap,
                               Set<Vertex<T>> onStack,
                               Stack<Vertex<T>> stack,
                               int[] index,
                               List<List<Vertex<T>>> sccList) {
        metrics.incrementCounter("dfs_calls");

        indexMap.put(v, index[0]);
        lowlinkMap.put(v, index[0]);
        index[0]++;

        stack.push(v);
        onStack.add(v);

        for (Neighbor<T> neighbor : v.getNeighbors()) {
            metrics.incrementCounter("edges_examined");

            Vertex<T> w = neighbor.vertex();
            if (!indexMap.containsKey(w)) {
                strongConnect(w, indexMap, lowlinkMap, onStack, stack, index, sccList);
                lowlinkMap.put(v, Math.min(lowlinkMap.get(v), lowlinkMap.get(w)));
            } else if (onStack.contains(w)) {
                lowlinkMap.put(v, Math.min(lowlinkMap.get(v), indexMap.get(w)));
            }
        }

        if (lowlinkMap.get(v).equals(indexMap.get(v))) {
            metrics.incrementCounter("scc_found");
            List<Vertex<T>> scc = new ArrayList<>();
            Vertex<T> w;
            do {
                w = stack.pop();
                onStack.remove(w);
                scc.add(w);
            } while (!w.equals(v));
            sccList.add(scc);
        }
    }

    public SCCMetrics getMetrics() {
        return metrics;
    }
}
