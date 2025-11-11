package algorithms.assignment;

import algorithms.assignment.dag_paths.DAGPathFinder;
import algorithms.assignment.dag_paths.result.DAGPathResult;
import algorithms.assignment.data.DatasetParser;
import algorithms.assignment.strongly_connected_components.KosarajuSCC;
import algorithms.assignment.strongly_connected_components.result.SCCResult;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            List<DatasetParser.GraphDataset<String>> datasets = DatasetParser.parse("data/data.json");
            for (DatasetParser.GraphDataset<String> ds : datasets) {
                System.out.println(ds);
                System.out.println(ds.graph());

                KosarajuSCC<String> kosaraju = new KosarajuSCC<>();
                SCCResult<String> scc = kosaraju.findSCCs(ds.graph());
                System.out.println("SCC count: " + scc.components().size());

                if (!ds.cyclic()) {
                    DAGPathFinder<String> finder = new DAGPathFinder<>();
                    DAGPathResult<String> shortest = finder.shortestPaths(ds.graph(), "A");
                    DAGPathResult<String> longest = finder.longestPaths(ds.graph(), "A");
                    System.out.println(shortest);
                    System.out.println(longest);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading dataset file: " + e.getMessage());
        }
    }
}
