package algorithms.assignment.graph;

/**
 * Represents a directed edge in a graph with weight and inclusion flag.
 */
public class Edge {
    private final int weight;
    private boolean included;

    public Edge() {
        this(1);
    }

    public Edge(int weight) {
        this.weight = weight;
        this.included = false;
    }

    public int getWeight() {
        return weight;
    }

    public boolean isIncluded() {
        return included;
    }

    public void setIncluded(boolean included) {
        this.included = included;
    }

    @Override
    public String toString() {
        return "Edge(w=" + weight + ")";
    }
}

