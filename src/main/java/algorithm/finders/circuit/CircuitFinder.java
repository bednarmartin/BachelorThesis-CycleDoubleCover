package algorithm.finders.circuit;

import algorithm.exceptions.InconsistentGraphException;
import algorithm.graph.Circuit;
import algorithm.graph.Graph;

import java.util.List;

/**
 * Interface for algorithm for finding all the circuits of the graph
 */
public interface CircuitFinder {
    /**
     * This method finds all the circuits of the graph
     *
     * @param graph Graph of which circuits will be found
     * @return List of circuits of the graph
     */
    List<Circuit> getCircuits(Graph graph);
}
