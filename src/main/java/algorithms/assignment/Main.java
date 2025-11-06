package algorithms.assignment;

import algorithms.assignment.graph.Graph;
import algorithms.assignment.graph.Vertex;
import algorithms.assignment.graph.topo.DFSTopologicalSort;
import algorithms.assignment.graph.topo.KahnTopologicalSort;
import algorithms.assignment.graph.topo.TopologicalSortResult;

/**
 * Main class demonstrating topological sort on a DAG.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Topological Sort Demo ===\n");

        // Create a sample DAG representing task dependencies
        Graph<String> dag = createSampleDAG();
        System.out.println(dag);

        // Test Kahn's algorithm
        System.out.println("\n--- Kahn's Algorithm (BFS-based) ---");
        KahnTopologicalSort<String> kahn = new KahnTopologicalSort<>();
        TopologicalSortResult<String> kahnResult = kahn.sort(dag);

        if (!kahnResult.hasCycle()) {
            System.out.println("Topological Order: " + kahnResult.getOrder());
            System.out.println("\nOrder of tasks:");
            for (String task : kahnResult.getOrder()) {
                System.out.println("  " + task);
            }
        } else {
            System.out.println("Cycle detected! Graph is not a DAG.");
        }
        System.out.println("\n" + kahnResult.getMetrics().getSummary());

        // Test DFS algorithm
        System.out.println("\n--- DFS-based Algorithm ---");
        DFSTopologicalSort<String> dfs = new DFSTopologicalSort<>();
        TopologicalSortResult<String> dfsResult = dfs.sort(dag);

        if (!dfsResult.hasCycle()) {
            System.out.println("Topological Order: " + dfsResult.getOrder());
            System.out.println("\nOrder of tasks:");
            for (String task : dfsResult.getOrder()) {
                System.out.println("  " + task);
            }
        } else {
            System.out.println("Cycle detected! Graph is not a DAG.");
        }
        System.out.println("\n" + dfsResult.getMetrics().getSummary());

        // Test with a graph containing a cycle
        System.out.println("\n\n=== Cycle Detection Test ===\n");
        Graph<String> cyclicGraph = createCyclicGraph();
        System.out.println(cyclicGraph);

        System.out.println("\n--- Kahn's Algorithm on Cyclic Graph ---");
        TopologicalSortResult<String> cyclicKahn = kahn.sort(cyclicGraph);
        System.out.println(cyclicKahn);

        System.out.println("\n--- DFS Algorithm on Cyclic Graph ---");
        TopologicalSortResult<String> cyclicDFS = dfs.sort(cyclicGraph);
        System.out.println(cyclicDFS);
    }

    /**
     * Creates a sample DAG representing smart city tasks.
     */
    private static Graph<String> createSampleDAG() {
        Graph<String> graph = new Graph<>();

        // Add vertices
        graph.addVertex(new Vertex<>("Planning"));
        graph.addVertex(new Vertex<>("Infrastructure Setup"));
        graph.addVertex(new Vertex<>("Sensor Installation"));
        graph.addVertex(new Vertex<>("Camera Installation"));
        graph.addVertex(new Vertex<>("Network Configuration"));
        graph.addVertex(new Vertex<>("Testing"));
        graph.addVertex(new Vertex<>("Deployment"));

        // Add edges (dependencies)
        graph.addEdge("Planning", "Infrastructure Setup");
        graph.addEdge("Planning", "Network Configuration");
        graph.addEdge("Infrastructure Setup", "Sensor Installation");
        graph.addEdge("Infrastructure Setup", "Camera Installation");
        graph.addEdge("Sensor Installation", "Testing");
        graph.addEdge("Camera Installation", "Testing");
        graph.addEdge("Network Configuration", "Testing");
        graph.addEdge("Testing", "Deployment");

        return graph;
    }

    /**
     * Creates a cyclic graph for testing cycle detection.
     */
    private static Graph<String> createCyclicGraph() {
        Graph<String> graph = new Graph<>();

        // Add vertices
        graph.addVertex(new Vertex<>("Task A"));
        graph.addVertex(new Vertex<>("Task B"));
        graph.addVertex(new Vertex<>("Task C"));
        graph.addVertex(new Vertex<>("Task D"));

        // Add edges forming a cycle
        graph.addEdge("Task A", "Task B");
        graph.addEdge("Task B", "Task C");
        graph.addEdge("Task C", "Task D");
        graph.addEdge("Task D", "Task B"); // Creates cycle: B -> C -> D -> B

        return graph;
    }
}

