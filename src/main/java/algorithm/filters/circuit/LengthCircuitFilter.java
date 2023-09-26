package algorithm.filters.circuit;

import algorithm.graph.Circuit;

import java.util.ArrayList;
import java.util.List;

/**
 * Circuit Filter for filtering circuits that are shorter than desired length.
 */
public class LengthCircuitFilter extends CircuitFilterDecorator {
    /***
     * desired length
     */
    private final int length;

    /**
     * Constructor
     *
     * @param length minimal length of the circuits.
     */
    public LengthCircuitFilter(int length) {
        super();
        this.length = length;
    }

    /**
     * Constructor
     *
     * @param circuitFilter Circuit Filter
     * @param length        minimal length of the circuits.
     */
    public LengthCircuitFilter(CircuitFilter circuitFilter, int length) {
        super(circuitFilter);
        this.length = length;
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<Circuit> getFilteredCircuits(List<Circuit> circuits) {
        List<Circuit> newCircuits = new ArrayList<>((super.getFilteredCircuits(circuits) == null) ? circuits : super.getFilteredCircuits(circuits));
        newCircuits.removeIf(circuit -> circuit.getEdges().size() < length);
        return newCircuits;

    }
}
