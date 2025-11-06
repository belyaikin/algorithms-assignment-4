package algorithms.assignment.graph;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents a vertex in a directed graph with generic data type.
 */
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

    @Override
    public String toString() {
        return "Vertex(" + data + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex<?> vertex = (Vertex<?>) o;
        return data != null ? data.equals(vertex.data) : vertex.data == null;
    }

    @Override
    public int hashCode() {
        return data != null ? data.hashCode() : 0;
    }
}

