package algorithm.filters.graph;

import algorithm.finders.twocut.TwoCutFinder;
import algorithm.graph.Graph;

/**
 * Class representing a GraphFilter for filtering graphs with a 2-cut.
 */
public class TwoCutGraphFilter extends GraphFilterDecorator {
    /**
     * algorithm for finding 2-cuts
     */
    private final TwoCutFinder twoCutFinder;

    /**
     * Constructor
     *
     * @param graphFilter  graph filter
     * @param twoCutFinder 2-cut finder
     */
    public TwoCutGraphFilter(GraphFilter graphFilter, TwoCutFinder twoCutFinder) {
        super(graphFilter);
        this.twoCutFinder = twoCutFinder;
    }

    /**
     * Constructor
     *
     * @param twoCutFinder 2-cut finder
     */
    public TwoCutGraphFilter(TwoCutFinder twoCutFinder) {
        super();
        this.twoCutFinder = twoCutFinder;
    }

    /**
     * This method filters graph with a 2-cut.
     *
     * @inheritDoc
     */
    @Override
    public boolean pass(Graph graph) {
        return super.pass(graph) && graph.get2Cuts(twoCutFinder).isEmpty();
    }
}
