package algorithm.determiners.bridge;

import algorithm.graph.Graph;


/**
 * Interface for an algorithm to determine whether a graph has a bridge.
 */
public interface BridgeDeterminer {
    /**
     * This method determines whether the graph has a bridge.
     *
     * @param graph Graph to be processed
     * @return true if the graph has a bridge
     */
    boolean hasBridge(Graph graph);
}
