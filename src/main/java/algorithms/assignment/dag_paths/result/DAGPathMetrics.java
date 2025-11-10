package algorithms.assignment.dag_paths.result;

import algorithms.assignment.Metrics;

import java.util.HashMap;
import java.util.Map;

public final class DAGPathMetrics implements Metrics {
    private long startTime;
    private long endTime;
    private final Map<String, Long> counters;

    public DAGPathMetrics() {
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
    public void incrementCounter(String name) {
        counters.put(name, counters.getOrDefault(name, 0L) + 1);
    }

    @Override
    public long getCounter(String name) {
        return counters.getOrDefault(name, 0L);
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
        sb.append("DAG Path Metrics:\n");
        sb.append(String.format("  Time: %.3f ms\n", getElapsedTimeMillis()));

        for (Map.Entry<String, Long> e : counters.entrySet()) {
            sb.append(String.format("  %s: %d\n", e.getKey(), e.getValue()));
        }

        return sb.toString();
    }
}
