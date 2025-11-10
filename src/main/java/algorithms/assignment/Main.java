package algorithms.assignment;

import algorithms.assignment.graph.Graph;
import algorithms.assignment.graph.Vertex;
import algorithms.assignment.graph.strongly_connected_components.KosarajuSCC;
import algorithms.assignment.graph.strongly_connected_components.TarjanSCC;
import algorithms.assignment.graph.strongly_connected_components.result.SCCResult;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Strongly Connected Components (SCC) Demo ===\n");

        Graph<String> graph = createSCCGraph();
        System.out.println(graph);

        System.out.println("\n--- Kosaraju's Algorithm ---");
        KosarajuSCC<String> kosaraju = new KosarajuSCC<>();
        SCCResult<String> kosarajuResult = kosaraju.findSCCs(graph);

        System.out.println("Number of SCCs: " + kosarajuResult.components().size());
        for (int i = 0; i < kosarajuResult.components().size(); i++) {
            System.out.println("  Component " + (i + 1) + ": " + kosarajuResult.components().get(i));
        }
        System.out.println("\n" + kosarajuResult.metrics().getSummary());

        System.out.println("\n--- Tarjan's Algorithm ---");
        TarjanSCC<String> tarjan = new TarjanSCC<>();
        SCCResult<String> tarjanResult = tarjan.findSCCs(graph);

        System.out.println("Number of SCCs: " + tarjanResult.components().size());

        for (int i = 0; i < tarjanResult.components().size(); i++) {
            System.out.println("  Component " + (i + 1) + ": " + tarjanResult.components().get(i));
        }

        System.out.println("\n" + tarjanResult.metrics().getSummary());
    }

    private static Graph<String> createSCCGraph() {
        Graph<String> graph = new Graph<>();

        graph.addVertex(new Vertex<>("A"));
        graph.addVertex(new Vertex<>("B"));
        graph.addVertex(new Vertex<>("C"));
        graph.addVertex(new Vertex<>("D"));
        graph.addVertex(new Vertex<>("E"));
        graph.addVertex(new Vertex<>("F"));
        graph.addVertex(new Vertex<>("G"));

        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "A");
        graph.addEdge("B", "D");
        graph.addEdge("D", "E");
        graph.addEdge("E", "F");
        graph.addEdge("F", "D");
        graph.addEdge("G", "F");

        return graph;
    }
}
