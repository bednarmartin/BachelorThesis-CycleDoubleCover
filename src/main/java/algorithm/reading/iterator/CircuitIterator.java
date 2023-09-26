package algorithm.reading.iterator;

import algorithm.exceptions.InconsistentCircuitException;
import algorithm.exceptions.InconsistentGraphException;
import algorithm.graph.Circuit;

import java.io.IOException;

/**
 * Interface for reading circuits of the graph
 */
public interface CircuitIterator {
    /**
     * This method grant access to the circuit count.
     *
     * @return circuit count in the file
     */
    int getNumberOfCircuits();

    /**
     * This method read another graph.
     *
     * @return circuit
     * @throws IOException                  if reading fails
     * @throws InconsistentCircuitException if the circuit is inconsistent
     */
    Circuit next() throws IOException, InconsistentCircuitException;

    /**
     * This method allows checking if the reading can continue.
     *
     * @return true if another circuit can be read
     */
    boolean hasNext();

    /**
     * This method allows getting actual index of read circuit.
     *
     * @return actual index
     */
    int actualIndex();

}
