package algorithm.filters.graph;

import algorithm.graph.Graph;

/**
 * Class representing a GraphFilter that does not filter graph.
 */
public class NoGraphFilter implements GraphFilter {
    /**
     * @inheritDoc
     */
    @Override
    public boolean pass(Graph graph) {
        return true;
    }
}
