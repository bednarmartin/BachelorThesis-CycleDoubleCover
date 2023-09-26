package algorithm.filters.graph;

import algorithm.graph.Circuit;
import algorithm.graph.Graph;

import java.util.List;

/**
 * Class representing a GraphFilter for filtering graphs which girth is less than desired.
 */
public class GirthGraphFilter extends GraphFilterDecorator {

    /**
     * desired girth
     */
    private final int girth;

    /**
     * Constructor
     *
     * @param graphFilter graph filter
     * @param girth       desired girth
     */
    public GirthGraphFilter(GraphFilter graphFilter, int girth) {
        super(graphFilter);
        this.girth = girth;
    }

    /**
     * Constructor
     *
     * @param girth desired girth
     */
    public GirthGraphFilter(int girth) {
        super();
        this.girth = girth;
    }

    /**
     * This method filters graphs with the girth less than desired.
     *
     * @inheritDoc
     */
    @Override
    public boolean pass(Graph graph) {
        List<Circuit> circuits = graph.getCircuits();
        boolean hasSuchCircuit = false;
        for (Circuit circuit : circuits) {
            if (circuit.getEdges().size() < girth) {
                hasSuchCircuit = true;
                break;
            }
        }
        return super.pass(graph) && !hasSuchCircuit;
    }
}
