package algorithm.reading;

import algorithm.reading.format.circuit.CircuitFormat;
import algorithm.reading.iterator.CircuitIterator;

/**
 * Class representing an analysis of a circuit file.
 */
public class CircuitFileAnalysis {
    /**
     * the circuit format
     */
    private final CircuitFormat circuitFormat;
    /**
     * the circuit iterator
     */
    private final CircuitIterator circuitIterator;
    /**
     * whether the circuit iterator was already called
     */
    private boolean getCircuitIteratorAlreadyCalled;

    /**
     * Constructor
     *
     * @param circuitFormat   the format of the circuit
     * @param circuitIterator the circuit iterator
     */
    public CircuitFileAnalysis(CircuitFormat circuitFormat, CircuitIterator circuitIterator) {
        this.circuitFormat = circuitFormat;
        this.circuitIterator = circuitIterator;
        getCircuitIteratorAlreadyCalled = false;
    }

    public CircuitFormat getCircuitFormat() {
        return circuitFormat;
    }

    /**
     * This method grants access to the circuit iterator.
     *
     * @return the circuit iterator
     */
    public CircuitIterator getCircuitIterator() {
        if (getCircuitIteratorAlreadyCalled) {
            throw new UnsupportedOperationException("Can't call it more than once");
        }
        getCircuitIteratorAlreadyCalled = true;
        return circuitIterator;
    }


}
