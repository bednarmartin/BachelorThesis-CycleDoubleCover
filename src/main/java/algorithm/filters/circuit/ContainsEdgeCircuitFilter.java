package algorithm.filters.circuit;

import algorithm.graph.Circuit;
import algorithm.graph.Edge;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Circuit Filter for filtering circuits that do not contain the desired edge.
 */
public class ContainsEdgeCircuitFilter extends CircuitFilterDecorator {

    /**
     * The desired edge
     */
    private final Edge edge;

    /**
     * Constructor
     *
     * @param edge considered edge
     */
    public ContainsEdgeCircuitFilter(Edge edge) {
        super();
        this.edge = edge;
    }

    /**
     * Constructor
     *
     * @param circuitFilter Circuit Filter
     * @param edge          considered edges
     */
    public ContainsEdgeCircuitFilter(CircuitFilter circuitFilter, Edge edge) {
        super(circuitFilter);
        this.edge = edge;
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<Circuit> getFilteredCircuits(List<Circuit> circuits) {
        List<Circuit> newCircuits = new ArrayList<>((super.getFilteredCircuits(circuits) == null) ? circuits : super.getFilteredCircuits(circuits));
        for (Circuit circuit : new HashSet<>(newCircuits)) {
            if (!circuit.getEdges().contains(edge)) {
                newCircuits.remove(circuit);
            }
        }
        return newCircuits;

    }
}
