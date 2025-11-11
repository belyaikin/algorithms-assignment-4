package algorithms.assignment.data;

import algorithms.assignment.graph.Graph;
import algorithms.assignment.graph.Vertex;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class DatasetParser {
    public record GraphDataset<T>(
            int id,
            String category,
            String description,
            boolean cyclic,
            Graph<T> graph
    ) {
        @Override
            public String toString() {
                return "Dataset " + id +
                        " (" + category + "): " + description +
                        " | cyclic=" + cyclic +
                        " | vertices=" + graph.getVertexCount();
            }
        }

    public static List<GraphDataset<String>> parse(String filePath) throws IOException {
        List<GraphDataset<String>> datasets = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath)) {
            JSONObject root = new JSONObject(new JSONTokener(fis));
            JSONArray datasetArray = root.getJSONArray("datasets");

            for (int i = 0; i < datasetArray.length(); i++) {
                JSONObject ds = datasetArray.getJSONObject(i);

                int id = ds.getInt("id");
                String category = ds.getString("category");
                String description = ds.getString("description");
                boolean cyclic = ds.getBoolean("cyclic");

                JSONArray verticesArray = ds.getJSONArray("vertices");
                JSONArray edgesArray = ds.getJSONArray("edges");

                Graph<String> graph = new Graph<>();
                for (int v = 0; v < verticesArray.length(); v++) {
                    String vertexLabel = verticesArray.getString(v);
                    graph.addVertex(new Vertex<>(vertexLabel));
                }

                for (int e = 0; e < edgesArray.length(); e++) {
                    JSONArray edgeArray = edgesArray.getJSONArray(e);
                    String from = edgeArray.getString(0);
                    String to = edgeArray.getString(1);
                    int weight = edgeArray.length() > 2 ? edgeArray.getInt(2) : 1;
                    graph.addEdge(from, to, weight);
                }

                datasets.add(new GraphDataset<>(id, category, description, cyclic, graph));
            }
        }

        return datasets;
    }
}