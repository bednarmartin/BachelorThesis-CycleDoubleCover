package algorithm.finders.threecut;

import algorithm.determiners.connectivity.ConnectedGraphDeterminer;
import algorithm.graph.Edge;
import algorithm.graph.Graph;
import algorithm.graph.Vertex;

import java.util.*;

/**
 * Class representing an algorithm for finding all nontrivial 3-cuts in the graph by checking all possible combinations.
 */

public class CombinationThreeCutFinder implements ThreeCutFinder {
    /**
     * algorithm for checking connectivity of the graph
     */
    private final ConnectedGraphDeterminer connectedGraphDeterminer;
    /**
     * List of 3-cut edges
     */
    private List<List<Edge>> cutEdges;
    /**
     * all the edges of the graph
     */
    private List<Edge> graphEdges;
    /**
     * the graph to be processed
     */
    private Graph graph;

    /**
     * Constructor for the CombinationThreeCutFinder class
     *
     * @param connectedGraphDeterminer algorithm for checking connectivity of the graph
     */
    public CombinationThreeCutFinder(ConnectedGraphDeterminer connectedGraphDeterminer) {
        this.connectedGraphDeterminer = connectedGraphDeterminer;
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<List<Edge>> getThreeCuts(Graph graph) {
        this.cutEdges = new ArrayList<>();
        this.graph = graph;
        this.graphEdges = new ArrayList<>(graph.getEdges());
        Edge[] array = new Edge[3];
        processAllCombinations(array, 0, graphEdges.size() - 1, 0, 3);
        return cutEdges;
    }

    /**
     * This method determines whether the List of edges is a 3-cut.
     *
     * @param edgesToCheck List of 3 edges to check whether they are 3-cut edges
     * @return true if the edges are 3-cut edges
     */
    @SuppressWarnings("Duplicates")
    private boolean isCut(List<Edge> edgesToCheck) {
        for (Edge edge : edgesToCheck) {
            graph.getVertices().get(edge.getFirst().getNumber()).getNeighbors().remove(edge.getSecond());
            graph.getVertices().get(edge.getSecond().getNumber()).getNeighbors().remove(edge.getFirst());
        }
        boolean isCut = !connectedGraphDeterminer.isConnected(graph);
        for (Edge edge : edgesToCheck) {
            graph.getVertices().get(edge.getFirst().getNumber()).getNeighbors().add(edge.getSecond());
            graph.getVertices().get(edge.getSecond().getNumber()).getNeighbors().add(edge.getFirst());
        }
        return isCut;
    }

    /**
     * This method processes all possible combination of 3 edges in order to find all the 3-cut of the graph
     *
     * @param data  actual 3 edges to be processed
     * @param start start point
     * @param end   end point
     * @param index actual index
     * @param size  cut-size
     */
    private void processAllCombinations(Edge[] data, int start, int end, int index, int size) {
        if (index == size) {
            List<Edge> toCheck = new ArrayList<>(Arrays.asList(data));
            Set<Vertex> edgeVertices = new HashSet<>();
            for (Edge edge : toCheck) {
                edgeVertices.add(edge.getFirst());
                edgeVertices.add(edge.getSecond());
            }
            if (edgeVertices.size() == 4) {
                return;
            }
            if (isCut(toCheck)) {
                cutEdges.add(toCheck);
            }
            return;
        }
        for (int i = start; i <= end && end - i + 1 >= size - index; i++) {
            data[index] = graphEdges.get(i);
            processAllCombinations(data, i + 1, end, index + 1, size);
        }

    }
}