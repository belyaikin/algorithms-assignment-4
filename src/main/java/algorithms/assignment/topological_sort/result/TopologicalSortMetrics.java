package algorithms.assignment.topological_sort.result;

import algorithms.assignment.Metrics;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of the Metrics interface for topological sort algorithms.
 */
public final class TopologicalSortMetrics implements Metrics {
    private long startTime;
    private long endTime;
    private final Map<String, Long> counters;

    public TopologicalSortMetrics() {
        this.counters = new HashMap<>();
        reset();
    }

    @Override
    public void startTimer() {
        startTime = System.nanoTime();
    }

    @Override
    public void stopTimer() {
        endTime = System.nanoTime();
    }

    @Override
    public long getElapsedTimeNanos() {
        return endTime - startTime;
    }

    @Override
    public void incrementCounter(String counterName) {
        counters.put(counterName, counters.getOrDefault(counterName, 0L) + 1);
    }

    @Override
    public long getCounter(String counterName) {
        return counters.getOrDefault(counterName, 0L);
    }

    @Override
    public void reset() {
        startTime = 0;
        endTime = 0;
        counters.clear();
    }

    @Override
    public String getSummary() {
        StringBuilder sb = new StringBuilder();
        sb.append("Topological Sort Metrics:\n");
        sb.append(String.format("  Time: %.3f ms\n", getElapsedTimeMillis()));

        for (Map.Entry<String, Long> entry : counters.entrySet()) {
            sb.append(String.format("  %s: %d\n", entry.getKey(), entry.getValue()));
        }

        return sb.toString();
    }
}