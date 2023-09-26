package algorithm.graphjoiner;

import algorithm.graph.Graph;

/**
 * An interface for a joiner of two graphs.
 */
public interface GraphJoiner {
    /**
     * This method joins two graphs.
     *
     * @param graph1 the first graph to be joined
     * @param graph2 the second graph to be joined
     * @return the joined graph
     */
    Graph joinGraphs(Graph graph1, Graph graph2);
}
