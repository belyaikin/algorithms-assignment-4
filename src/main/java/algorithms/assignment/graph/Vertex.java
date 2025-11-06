package algorithms.assignment.graph;

import java.util.LinkedList;
import java.util.List;

public final class Vertex<T> {
    private final T data;
    private boolean visited;
    private List<Neighbor<T>> neighbors = new LinkedList<>();

    public Vertex(T data) {
        this.data = data;
    }

    public List<Neighbor<T>> getNeighbors() {
        return neighbors;
    }

    public void addNeighbor(Vertex<T> vertex, Edge edge) {
        neighbors.add(new Neighbor<>(vertex, edge));
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public T getData() {
        return data;
    }
}
