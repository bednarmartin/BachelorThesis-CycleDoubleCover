package algorithm.filters.graph;

import algorithm.graph.Graph;

/**
 * Abstract class for Graph Filter Decorator.
 */
public abstract class GraphFilterDecorator implements GraphFilter {
    /**
     * Graph filter
     */
    private final GraphFilter graphFilter;

    /**
     * Constructor
     *
     * @param graphFilter graph filter
     */
    protected GraphFilterDecorator(GraphFilter graphFilter) {
        this.graphFilter = graphFilter;
    }

    /**
     * Constructor
     */
    protected GraphFilterDecorator() {
        this.graphFilter = null;
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean pass(Graph graph) {
        return graphFilter == null || graphFilter.pass(graph);
    }

}
