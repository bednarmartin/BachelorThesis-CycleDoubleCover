package algorithm.filters.graph;

import algorithm.determiners.bridge.BridgeDeterminer;
import algorithm.graph.Graph;

/**
 * Class representing a GraphFilter for filtering graphs with a bridge.
 */
public class BridgeGraphFilter extends GraphFilterDecorator {
    /**
     * algorithm to determine whether the graph has a bridge
     */
    private final BridgeDeterminer bridgeDeterminer;

    /**
     * Constructor
     *
     * @param graphFilter      graph filter
     * @param bridgeDeterminer bridge determiner
     */
    public BridgeGraphFilter(GraphFilter graphFilter, BridgeDeterminer bridgeDeterminer) {
        super(graphFilter);
        this.bridgeDeterminer = bridgeDeterminer;
    }

    /**
     * Constructor
     *
     * @param bridgeDeterminer bridge determiner
     */
    public BridgeGraphFilter(BridgeDeterminer bridgeDeterminer) {
        super();
        this.bridgeDeterminer = bridgeDeterminer;
    }

    /**
     * This method filters graph with a bridge.
     *
     * @inheritDoc
     */
    @Override
    public boolean pass(Graph graph) {
        return super.pass(graph) && !graph.hasBridge(bridgeDeterminer);
    }
}
