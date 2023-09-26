package algorithm.reading.format.circuit;

import algorithm.exceptions.UnsupportedCircuitFormatException;
import algorithm.graph.Graph;
import algorithm.reading.iterator.CircuitIterator;
import application.center.vboxes.CircuitAnalysisVBox;

import java.io.IOException;

/**
 * Interface representing a format of a circuit.
 */
public interface CircuitFormat {
    /**
     * This method grants access to circuit iterator for reading circuits
     *
     * @param path  path to the file
     * @param graph graph
     * @return circuit iterator
     * @throws IOException                       if reading fails
     * @throws UnsupportedCircuitFormatException if the format is unsupported
     */
    CircuitIterator getCircuitIterator(String path, Graph graph) throws IOException, UnsupportedCircuitFormatException;

    /**
     * This method allows graph format to update CircuitAnalysisVBox according to the circuit format
     *
     * @param circuitAnalysisVBox CircuitAnalysisVBox to update
     */
    void updateAnalysisVBox(CircuitAnalysisVBox circuitAnalysisVBox);
}
