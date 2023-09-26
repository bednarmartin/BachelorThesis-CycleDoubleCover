package algorithm.determiners.connectivity;

import algorithm.graph.Graph;
import algorithm.graph.Vertex;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing an algorithm for determining whether the graph is connected using modified DFS.
 */
public class DFSConnectedGraphDeterminer implements ConnectedGraphDeterminer {
    /**
     * @inheritDoc
     */
    @Override
    public boolean isConnected(Graph graph) {
        List<Boolean> visited = new ArrayList<>();
        for (int i = 0; i < graph.getNumberOfVertices(); i++) {
            visited.add(false);
        }
        dfs(graph.getVertices().get(0), visited, graph);
        return !visited.contains(false);
    }

    /**
     * The Depth-First algorithm to determine whether there exists a path from the vertex to each other vertex of the graph.
     *
     * @param vertex  vertex to process
     * @param visited List of visited vertices
     * @param graph   graph to be processed
     */
    private void dfs(Vertex vertex, List<Boolean> visited, Graph graph) {
        visited.set(vertex.getNumber(), true);
        for (Vertex neighbor : graph.getVertices().get(vertex.getNumber()).getNeighbors()) {
            Boolean bool = !visited.get(neighbor.getNumber());
            if (Boolean.TRUE.equals(bool))
                dfs(neighbor, visited, graph);
        }
    }
}