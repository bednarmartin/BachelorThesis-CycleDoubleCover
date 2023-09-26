package algorithm.finders.doublecyclecover;

import algorithm.exceptions.StopRecursionException;
import algorithm.filters.circuit.CircuitFilter;
import algorithm.filters.circuit.NoCircuitFilter;
import algorithm.graph.*;
import algorithm.strategies.CDCStrategy;
import algorithm.strategies.NoCDCStrategy;

import java.util.*;

/**
 * Class representing algorithm for finding cycle double covers of a graph using finding all the combinations of circuits
 */
public class CombinationCycleDoubleCoverFinder implements CycleDoubleCoverFinder {
    /**
     * Set of found double cycle covers
     */
    protected Set<CycleDoubleCover> foundCDCs;
    /**
     * List of cycles that has to be in every double cycle cover.
     */
    protected List<Cycle> fixedCycles;
    /**
     * graph of which double cycle covers will be found
     */
    protected Graph graph;
    /**
     * list of circuits of the graph
     */
    protected List<Circuit> circuits;
    /**
     * What should happen when a Cycle Double Cover is found.
     */
    protected CDCStrategy cdcStrategy;
    /**
     * Filter for filtering circuits.
     */
    protected CircuitFilter circuitFilter;
    /**
     * for stopping the computation
     */
    protected volatile boolean exit = false;
    /**
     * whether the goal is to find all the CDC or just any.
     */
    protected boolean any;

    /**
     * Constructor for CombinationDoubleCycleCoverFinder class
     *
     * @param graph Graph of which double cycle covers are supposed to be found
     */
    public CombinationCycleDoubleCoverFinder(Graph graph) {
        this.graph = graph;
        this.circuits = graph.getCircuits();
        foundCDCs = new HashSet<>();
        fixedCycles = new ArrayList<>();
        cdcStrategy = new NoCDCStrategy();
        circuitFilter = new NoCircuitFilter();
        any = false;

    }

    /**
     * @inheritDoc
     */
    @Override
    public void find() {
        circuits = circuitFilter.getFilteredCircuits(circuits);
        List<Cycle> data = new ArrayList<>();
        Set<Edge> twoTimesUsedEdges = new HashSet<>();
        for (Cycle fixedCycle : fixedCycles) {
            data.add(fixedCycle);
            processTwoTimesUsedEdges(data, twoTimesUsedEdges, fixedCycle);
        }
        try {
            findCycleDoubleCovers(data, 0, fixedCycles.size(), twoTimesUsedEdges);
        } catch (StopRecursionException ignored) {
        }

    }

    /**
     * @inheritDoc
     */
    @Override
    public Set<CycleDoubleCover> getCycleDoubleCovers() {
        return foundCDCs;
    }

    /**
     * This method generates all combinations of circuits and checks every time when adding a new circuits
     * if the set of circuits covers every edge at most two times.
     *
     * @param data  actual combination
     * @param start start
     * @param index actual index
     */
    private void findCycleDoubleCovers(List<Cycle> data, int start, int index, Set<Edge> twoTimesUsedEdges) throws StopRecursionException {
        if (exit) {
            throw new StopRecursionException();
        }
        if (twoTimesUsedEdges.size() == graph.getEdges().size()) {
            CycleDoubleCover circuitDoubleCover = new CubicCycleDoubleCover(new HashSet<>(data));
            if (cdcStrategy.processCDC(circuitDoubleCover)) {
                foundCDCs.add(circuitDoubleCover);
                if (any) {
                    throw new StopRecursionException();
                }
            }
            return;
        }

        for (int i = start; i <= circuits.size() - 1; i++) {
            if (Collections.disjoint(twoTimesUsedEdges, circuits.get(i).getEdges())) {
                List<Cycle> newData = (data.size() < index) ? data : new ArrayList<>(data.subList(0, index));
                newData.add(circuits.get(i));
                Set<Edge> newTwoTimesUsedEdges = new HashSet<>(twoTimesUsedEdges);
                processTwoTimesUsedEdges(newData, newTwoTimesUsedEdges, circuits.get(i));
                findCycleDoubleCovers(newData, i + 1, index + 1, newTwoTimesUsedEdges);
            }
        }
    }

    /**
     * Process the last added circuit in order to find new edges that are already double covered
     *
     * @param data              actual List of circuits to be checked
     * @param twoTimesUsedEdges Set of Edges that are already double covered
     * @param last              Circuit added last
     */
    protected void processTwoTimesUsedEdges(List<Cycle> data, Set<Edge> twoTimesUsedEdges, Cycle last) {
        for (Cycle cycle : data) {
            if (cycle.equals(last)) {
                continue;
            }
            for (Edge edge : cycle.getEdges()) {
                if (last.getEdges().contains(edge)) {
                    twoTimesUsedEdges.add(edge);
                }
            }
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void addFixedCycle(Cycle cycle) {
        fixedCycles.add(cycle);

    }

    /**
     * @inheritDoc
     */
    @Override
    public Set<Circuit> getNecessaryCircuits() {
        Set<Circuit> necessaryCircuits = new HashSet<>(circuits);
        for (Circuit circuit : circuits) {
            for (CycleDoubleCover cycleDoubleCover : foundCDCs) {
                if (!cycleDoubleCover.getCycles().contains(circuit)) {
                    necessaryCircuits.remove(circuit);
                    break;
                }
            }
        }
        return necessaryCircuits;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setCDCStrategy(CDCStrategy cdcStrategy) {
        this.cdcStrategy = cdcStrategy;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setCircuitFilter(CircuitFilter circuitFilter) {
        this.circuitFilter = circuitFilter;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void stop() {
        exit = true;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setAny(boolean any) {
        this.any = any;
    }


}
