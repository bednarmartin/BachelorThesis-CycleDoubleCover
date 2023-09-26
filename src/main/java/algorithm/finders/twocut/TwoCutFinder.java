package algorithm.finders.twocut;

import algorithm.graph.Edge;
import algorithm.graph.Graph;

import java.util.List;

/**
 * Interface for finding 2-cuts of the graph.
 */
public interface TwoCutFinder {
    /**
     * This method finds all the 2-cuts of the graph.
     *
     * @param graph graph to be processed
     * @return List of pairs of edges that are part of 2-cut.
     */
    List<List<Edge>> getTwoCuts(Graph graph);
}
