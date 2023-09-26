package algorithm.filters.graph;

import algorithm.finders.threecut.ThreeCutFinder;
import algorithm.graph.Graph;

/**
 * Class representing a GraphFilter for filtering graphs with a nontrivial 3-cut.
 */
public class ThreeCutGraphFilter extends GraphFilterDecorator {
    /**
     * algorithm for finding nontrivial 3-cuts
     */
    private final ThreeCutFinder threeCutFinder;

    /**
     * Constructor
     *
     * @param graphFilter    graph filter
     * @param threeCutFinder 3-cut finder
     */
    public ThreeCutGraphFilter(GraphFilter graphFilter, ThreeCutFinder threeCutFinder) {
        super(graphFilter);
        this.threeCutFinder = threeCutFinder;
    }

    /**
     * Constructor
     *
     * @param threeCutFinder 3-cut finder
     */
    public ThreeCutGraphFilter(ThreeCutFinder threeCutFinder) {
        super();
        this.threeCutFinder = threeCutFinder;
    }

    /**
     * This method filters graph with a 3-cut.
     *
     * @inheritDoc
     */
    @Override
    public boolean pass(Graph graph) {
        return super.pass(graph) && graph.get3Cuts(threeCutFinder).isEmpty();
    }
}
