package algorithms.assignment;

/**
 * Common interface for tracking algorithm metrics.
 */
public interface Metrics {
    /**
     * Starts the timer.
     */
    void startTimer();

    /**
     * Stops the timer.
     */
    void stopTimer();

    /**
     * Gets the elapsed time in nanoseconds.
     */
    long getElapsedTimeNanos();

    /**
     * Gets the elapsed time in milliseconds.
     */
    default double getElapsedTimeMillis() {
        return getElapsedTimeNanos() / 1_000_000.0;
    }

    /**
     * Increments a named counter.
     */
    void incrementCounter(String counterName);

    /**
     * Gets the value of a named counter.
     */
    long getCounter(String counterName);

    /**
     * Resets all metrics.
     */
    void reset();

    /**
     * Gets a summary of all metrics.
     */
    String getSummary();
}

