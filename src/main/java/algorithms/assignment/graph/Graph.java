package algorithms.assignment.graph;

import java.util.*;

/**
 * Directed graph implementation using adjacency list representation.
 * Uses generic Vertex type.
 */
public class Graph<T> {
    private final Map<T, Vertex<T>> vertices;

    public Graph() {
        this.vertices = new HashMap<>();
    }

    /**
     * Adds a vertex to the graph.
     */
    public void addVertex(Vertex<T> vertex) {
        vertices.put(vertex.getData(), vertex);
    }

    /**
     * Adds a directed edge from source to destination.
     */
    public void addEdge(T sourceData, T destData, int weight) {
        Vertex<T> source = vertices.get(sourceData);
        Vertex<T> dest = vertices.get(destData);

        if (source == null || dest == null) {
            throw new IllegalArgumentException("Both vertices must exist before adding edge");
        }

        Edge edge = new Edge(weight);
        source.addNeighbor(dest, edge);
    }

    /**
     * Adds a directed edge with default weight of 1.
     */
    public void addEdge(T sourceData, T destData) {
        addEdge(sourceData, destData, 1);
    }

    /**
     * Gets a vertex by its data.
     */
    public Vertex<T> getVertex(T data) {
        return vertices.get(data);
    }

    /**
     * Gets all vertices.
     */
    public Collection<Vertex<T>> getVertices() {
        return vertices.values();
    }

    /**
     * Gets the number of vertices.
     */
    public int getVertexCount() {
        return vertices.size();
    }

    /**
     * Gets all vertex data keys.
     */
    public Set<T> getVertexKeys() {
        return vertices.keySet();
    }

    /**
     * Computes in-degree for all vertices.
     */
    public Map<Vertex<T>, Integer> computeInDegrees() {
        Map<Vertex<T>, Integer> inDegrees = new HashMap<>();

        // Initialize all vertices with in-degree 0
        for (Vertex<T> vertex : vertices.values()) {
            inDegrees.put(vertex, 0);
        }

        // Count incoming edges
        for (Vertex<T> vertex : vertices.values()) {
            for (Neighbor<T> neighbor : vertex.getNeighbors()) {
                Vertex<T> dest = neighbor.vertex();
                inDegrees.put(dest, inDegrees.get(dest) + 1);
            }
        }

        return inDegrees;
    }

    /**
     * Resets the visited flag for all vertices.
     */
    public void resetVisited() {
        for (Vertex<T> vertex : vertices.values()) {
            vertex.setVisited(false);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Graph with ").append(vertices.size()).append(" vertices:\n");
        for (Vertex<T> vertex : vertices.values()) {
            sb.append("  ").append(vertex).append(" -> ");
            sb.append(vertex.getNeighbors()).append("\n");
        }
        return sb.toString();
    }
}

