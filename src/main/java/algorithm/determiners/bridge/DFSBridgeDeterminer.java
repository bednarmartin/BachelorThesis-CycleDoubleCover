package algorithm.determiners.bridge;

import algorithm.graph.Graph;
import algorithm.graph.Vertex;


/**
 * Class representing an algorithm to determine whether the graph has a bridge using modified DFS algorithm.
 */
public class DFSBridgeDeterminer implements BridgeDeterminer {
    /**
     * counter for the DFS algorithm
     */
    private int counter = 0;

    /**
     * @inheritDoc
     */
    @Override
    public boolean hasBridge(Graph graph) {
        int numberOfVertices = graph.getNumberOfVertices();
        boolean[] visited = new boolean[numberOfVertices];
        int[] distance = new int[numberOfVertices];
        int[] low = new int[numberOfVertices];
        Vertex[] parent = new Vertex[numberOfVertices];
        for (int i = 0; i < numberOfVertices; i++) {
            parent[i] = null;
            visited[i] = false;
        }
        for (int i = 0; i < numberOfVertices; i++) {
            if (!visited[i] && dfs(graph.getVertices().get(i), visited, distance, low, parent)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Modified DFS to find a bridge
     *
     * @param vertex   vertex to be processed
     * @param visited  boolean array indicating visited vertices
     * @param distance distance from the start
     * @param low      low link
     * @param parent   array indicating parents of the vertices
     * @return true if there exists a bridge
     */
    private boolean dfs(Vertex vertex, boolean[] visited, int[] distance, int[] low, Vertex[] parent) {
        visited[vertex.getNumber()] = true;
        distance[vertex.getNumber()] = low[vertex.getNumber()] = ++counter;
        for (Vertex neighbor : vertex.getNeighbors()) {
            if (!visited[neighbor.getNumber()]) {
                parent[neighbor.getNumber()] = vertex;
                dfs(neighbor, visited, distance, low, parent);
                low[vertex.getNumber()] = Math.min(low[vertex.getNumber()], low[neighbor.getNumber()]);
                if (low[neighbor.getNumber()] > distance[vertex.getNumber()]) {
                    return true;
                }
            } else if (neighbor != parent[vertex.getNumber()])
                low[vertex.getNumber()] = Math.min(low[vertex.getNumber()], distance[neighbor.getNumber()]);
        }
        return false;
    }

}
