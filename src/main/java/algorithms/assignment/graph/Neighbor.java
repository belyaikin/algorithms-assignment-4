
package algorithms.assignment.graph;

public record Neighbor<T>(
        Vertex<T> vertex, Edge edge) {
public boolean isVisited() {
    return edge.isIncluded() || vertex.isVisited();
    }
}
