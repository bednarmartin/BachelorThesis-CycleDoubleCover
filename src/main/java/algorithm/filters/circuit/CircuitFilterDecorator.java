package algorithm.filters.circuit;

import algorithm.graph.Circuit;

import java.util.List;

/**
 * Abstract class for Circuit Filter Decorator
 */
public abstract class CircuitFilterDecorator implements CircuitFilter {
    /**
     * Circuit filter
     */
    private final CircuitFilter circuitFilter;

    /**
     * Constructor
     *
     * @param circuitFilter circuit filter
     */
    protected CircuitFilterDecorator(CircuitFilter circuitFilter) {
        this.circuitFilter = circuitFilter;
    }

    /**
     * Constructor without parameters
     */
    protected CircuitFilterDecorator() {
        this.circuitFilter = null;
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<Circuit> getFilteredCircuits(List<Circuit> circuits) {
        return (circuitFilter == null) ? null : circuitFilter.getFilteredCircuits(circuits);
    }

}
