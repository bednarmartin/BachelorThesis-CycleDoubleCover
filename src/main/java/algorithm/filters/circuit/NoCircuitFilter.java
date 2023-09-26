package algorithm.filters.circuit;

import algorithm.graph.Circuit;

import java.util.List;

/**
 * Circuit Filter for no filtering.
 */
public class NoCircuitFilter implements CircuitFilter {
    /**
     * @inheritDoc
     */
    @Override
    public List<Circuit> getFilteredCircuits(List<Circuit> circuits) {
        return circuits;
    }

}
