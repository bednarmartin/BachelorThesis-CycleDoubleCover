package algorithm.filters.graph;

import algorithm.graph.Graph;

/**
 * Interface for filtering graphs
 */
public interface GraphFilter {
    /**
     * @param graph graph to be checked
     * @return true if the graph passed (it doesn't have the property thus it's good to go)
     */
    boolean pass(Graph graph);
}
