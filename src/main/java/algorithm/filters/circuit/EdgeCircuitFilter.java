package algorithm.filters.circuit;

import algorithm.graph.*;

import java.util.*;

/**
 * Circuit Filter for filtering circuits that do not contain 'goodEdges' or contain 'badEdges'.
 */
public class EdgeCircuitFilter extends CircuitFilterDecorator {
    /**
     * set of good edges
     */
    private final Set<Edge> goodEdges;
    /**
     * set of bad edges
     */
    private final Set<Edge> badEdges;

    /**
     * Constructor
     *
     * @param goodEdges set of good edges
     * @param badEdges  set of bad edges
     */
    public EdgeCircuitFilter(Set<Edge> goodEdges, Set<Edge> badEdges) {
        super();
        this.goodEdges = goodEdges;
        this.badEdges = badEdges;
    }

    /**
     * Constructor
     *
     * @param circuitFilter circuit filter
     * @param goodEdges     set of good edges
     * @param badEdges      set of bad edges
     */
    public EdgeCircuitFilter(
            CircuitFilter circuitFilter, Set<Edge> goodEdges, Set<Edge> badEdges) {
        super(circuitFilter);
        this.goodEdges = goodEdges;
        this.badEdges = badEdges;
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<Circuit> getFilteredCircuits(List<Circuit> circuits) {
        List<Circuit> newCircuits =
                new ArrayList<>(
                        (super.getFilteredCircuits(circuits) == null)
                                ? circuits
                                : super.getFilteredCircuits(circuits));
        newCircuits.removeIf(
                circuit ->
                        !Collections.disjoint(circuit.getEdges(), badEdges)
                                || !circuit.getEdges().containsAll(goodEdges));
        return newCircuits;
    }
}
