package algorithm.reading;

import algorithm.graph.Graph;
import algorithm.reading.format.circuit.ErrorCircuitFormat;
import algorithm.reading.format.circuit.VerticesCircuitFormat;
import algorithm.reading.iterator.CircuitIterator;
import algorithm.reading.iterator.VerticesFormatCircuitIterator;

/**
 * Class representing the analyser of circuit file
 */
public class CircuitFileFormatAnalyser {
    /**
     * This method analyses the file with circuits.
     *
     * @param path  path to the file with circuits
     * @param graph the graph of which circuits are in the file
     * @return circuit file analysis
     */
    public CircuitFileAnalysis analyseFile(String path, Graph graph) {
        try {
            if (isVerticesCircuitFormat(path, graph)) {
                CircuitIterator circuitIterator = new VerticesFormatCircuitIterator(path, graph);
                return new CircuitFileAnalysis(new VerticesCircuitFormat(), circuitIterator);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new CircuitFileAnalysis(new ErrorCircuitFormat(), null);
    }

    /**
     * This method determines if the circuits has vertices format.
     *
     * @param path path to the file
     * @return true if the circuit format is 'Each row = list of vertices of the circuit'
     */
    private boolean isVerticesCircuitFormat(String path, Graph graph) {
        try {
            CircuitIterator circuitIterator = new VerticesFormatCircuitIterator(path, graph);
            if (!circuitIterator.hasNext()) {
                return false;
            }
            while (circuitIterator.hasNext()) {
                circuitIterator.next();
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
