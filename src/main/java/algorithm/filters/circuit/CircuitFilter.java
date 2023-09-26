package algorithm.filters.circuit;

import algorithm.graph.Circuit;

import java.util.List;

/**
 * Interface for filtering circuits of the graph.
 */
public interface CircuitFilter {
    /**
     * This method removes circuits that don't have required properties.
     *
     * @param circuits circuits of the graph
     * @return list of filtered circuits
     */
    List<Circuit> getFilteredCircuits(List<Circuit> circuits);

}
