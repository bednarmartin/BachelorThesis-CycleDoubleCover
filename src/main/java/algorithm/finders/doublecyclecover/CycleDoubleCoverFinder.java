package algorithm.finders.doublecyclecover;

import algorithm.filters.circuit.CircuitFilter;
import algorithm.graph.Circuit;
import algorithm.graph.Cycle;
import algorithm.graph.CycleDoubleCover;
import algorithm.strategies.CDCStrategy;

import java.util.Set;

/**
 * Interface for finding double cycle covers of a graph
 */
public interface CycleDoubleCoverFinder {

    /**
     * This method finds double cycle covers of a graph
     */
    void find();

    /**
     * This method returns found cycle double covers
     *
     * @return Set of Cycle Double Covers
     */
    Set<CycleDoubleCover> getCycleDoubleCovers();

    /**
     * This method set a cycle as fixed when finding cycle double cover that means,
     * every cycle double cover will contain this cycle.
     *
     * @param cycle cycle to fix
     */
    void addFixedCycle(Cycle cycle);

    /**
     * This method finds the circuits that are contained in every found cycle double cover
     *
     * @return Set of circuits that are in every found cycle double cover
     */
    Set<Circuit> getNecessaryCircuits();

    /**
     * This method allows setting CDC Strategy when the Cycle Double Cover is found.
     *
     * @param cdcStrategy cdc strategy to be added
     */
    void setCDCStrategy(CDCStrategy cdcStrategy);

    /**
     * This method allows setting CircuitFilter.
     *
     * @param circuitFilter circuit filter to be added
     */
    void setCircuitFilter(CircuitFilter circuitFilter);

    /**
     * Stops the algorithm
     */
    void stop();

    /**
     * Whether to find any CDC or all of them.
     *
     * @param any true if the goal is to find any CDC
     */
    void setAny(boolean any);

}
