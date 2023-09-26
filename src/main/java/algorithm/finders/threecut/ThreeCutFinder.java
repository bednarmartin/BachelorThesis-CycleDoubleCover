package algorithm.finders.threecut;

import algorithm.graph.Edge;
import algorithm.graph.Graph;

import java.util.List;

/**
 * Interface for finding nontrivial 3-cuts of the graph.
 */
public interface ThreeCutFinder {
    /**
     * This method finds all the 3-cuts of the graph.
     *
     * @param graph graph to be processed
     * @return List of triples of edges that are part of 3-cut.
     */
    List<List<Edge>> getThreeCuts(Graph graph);
}
