package algorithm.determiners.connectivity;

import algorithm.graph.Graph;

/**
 * Interface for an algorithm to determine whether the graph is connected.
 */
public interface ConnectedGraphDeterminer {
    /**
     * This method finds out if the graph is connected.
     *
     * @param graph graph to be processed
     * @return true if the graph is connected
     */
    boolean isConnected(Graph graph);

}
