package algorithm.strategies;

import algorithm.exceptions.StopRecursionException;
import algorithm.finders.correspondingedges.CombinationCorrespondingEdgesFinder;
import algorithm.finders.correspondingedges.CorrespondingEdgesFinder;
import algorithm.graph.*;

import java.util.*;

/**
 * This CDCStrategy tries to find a combined CDC for the graph obtained by two graphs.
 */
public class MergeCDCStrategy implements CDCStrategy {
    /**
     * Map of the corresponding edges
     */
    private final Map<Set<Edge>, Set<Edge>> correspondingEdges;
    /**
     * the new obtained graph
     */
    private final Graph newGraph;
    /**
     * the original graph
     */
    private final Graph originalGraph;
    /**
     * the linking graph
     */
    private final Graph linkingGraph;
    /**
     * set of unaffected cycles
     */
    private final Set<Cycle> unaffectedCycles;
    /**
     * set of unaffected cycles of the linking graph.
     */
    private final Set<Cycle> linkingUnaffectedCycles;

    /**
     * list of keys
     */
    private final List<Set<Edge>> keys;
    /**
     * list of the cycle double covers of linking graph
     */
    private final List<CycleDoubleCover> linkingCycleDoubleCovers;
    /**
     * set of the removed edges
     */
    private final Set<Edge> allRemovedEdges;
    /**
     * set of the added edges
     */
    private final Set<Edge> allAddedEdges;
    /**
     * the maximum number of cycles in the CDC
     */
    private final int numCycles;
    /**
     * if the CDC ought to be induced
     */
    private final boolean induced;
    /**
     * if to find just one CDC
     */
    private final boolean any;

    /**
     * Constructor
     *
     * @param newGraph                 the new obtained graph.
     * @param originalGraph            the original graph.
     * @param linkingGraph             the linking graph.
     * @param linkingCycleDoubleCovers the CDCs of the linking graph.
     * @param numCycles                the max number of cycles in CDC.
     * @param induced                  if the CDC ought to be induced.
     * @param any                      if just any CDC is enough
     */
    public MergeCDCStrategy(Graph newGraph, Graph originalGraph, Graph linkingGraph, List<CycleDoubleCover> linkingCycleDoubleCovers, int numCycles, boolean induced, boolean any) {
        CorrespondingEdgesFinder correspondingEdgesFinder = new CombinationCorrespondingEdgesFinder();
        this.correspondingEdges = correspondingEdgesFinder.findCorrespondingEdges(newGraph, originalGraph, linkingGraph);
        this.newGraph = newGraph;
        this.originalGraph = originalGraph;
        this.linkingGraph = linkingGraph;
        this.keys = new ArrayList<>(correspondingEdges.keySet());
        this.linkingCycleDoubleCovers = linkingCycleDoubleCovers;
        this.allRemovedEdges = new HashSet<>();
        this.allAddedEdges = new HashSet<>();
        this.linkingUnaffectedCycles = new HashSet<>();
        this.unaffectedCycles = new HashSet<>();
        this.numCycles = numCycles;
        this.induced = induced;
        this.any = any;

        for (Set<Edge> removedEdges : correspondingEdges.keySet()) {
            allRemovedEdges.addAll(removedEdges);
            allAddedEdges.addAll(correspondingEdges.get(removedEdges));
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean processCDC(CycleDoubleCover cycleDoubleCover) {
        for (Cycle cycle : cycleDoubleCover.getCycles()) {
            if (Collections.disjoint(cycle.getEdges(), allRemovedEdges)) {
                unaffectedCycles.add(cycle);
            }
        }

        Set<Cycle> cyclesToPreprocess = new HashSet<>(cycleDoubleCover.getCycles());
        cyclesToPreprocess.removeAll(unaffectedCycles);
        processAllCycles(cyclesToPreprocess);

        for (CycleDoubleCover linkingCycleDoubleCover : linkingCycleDoubleCovers) {
            for (Cycle cycle : linkingCycleDoubleCover.getCycles()) {
                if (Collections.disjoint(cycle.getEdges(), allRemovedEdges)) {
                    linkingUnaffectedCycles.add(cycle);
                }
            }
            Set<Cycle> otherCyclesToPreprocess = new HashSet<>(linkingCycleDoubleCover.getCycles());
            otherCyclesToPreprocess.removeAll(linkingUnaffectedCycles);
            processAllCycles(otherCyclesToPreprocess);
            try {
                recursion(cycleDoubleCover, linkingCycleDoubleCover, new HashSet<>(), 0);
            } catch (StopRecursionException e) {
                if (any) {
                    return true;
                }
            }

            unprocessAllCycles(otherCyclesToPreprocess);
            linkingUnaffectedCycles.clear();
        }

        unprocessAllCycles(cyclesToPreprocess);
        unaffectedCycles.clear();
        return false;
    }

    /**
     * This method tries to find correct CDC.
     *
     * @param cycleDoubleCover        actual cycle double cover
     * @param linkingCycleDoubleCover actual linking cycle double cover
     * @param processedCycles         cycles that were processed
     * @param index                   actual index
     * @throws StopRecursionException if the recursion ought to stop
     */
    public void recursion(CycleDoubleCover cycleDoubleCover, CycleDoubleCover linkingCycleDoubleCover, Set<Cycle> processedCycles, int index) throws StopRecursionException {
        if (index == keys.size()) {
            boolean allAreGood = true;
            for (Cycle cycle : processedCycles) {
                if (!newGraph.isCycleOfTheGraph(cycle)) {
                    allAreGood = false;
                    break;
                }
            }
            if (allAreGood) {
                processedCycles.addAll(unaffectedCycles);
                processedCycles.addAll(linkingUnaffectedCycles);
                int numberOfEdges = 0;
                for (Cycle cycle : new HashSet<>(processedCycles)) {
                    numberOfEdges += cycle.getEdges().size();
                }
                if (numberOfEdges != newGraph.getNumberOfVertices() * 3) {
                    return;
                }

                Map<Edge, Integer> counts = new HashMap<>();
                for (Edge edge : newGraph.getEdges()) {
                    counts.put(edge, 0);
                }
                for (Cycle cycle : processedCycles) {
                    for (Edge edge : cycle.getEdges()) {
                        if (counts.containsKey(edge)) {
                            counts.put(edge, counts.get(edge) + 1);
                        }
                    }
                }
                for (Edge edge : counts.keySet()) {
                    if (counts.get(edge) != 2) {
                        return;
                    }
                }
                CycleDoubleCover newCDC = new CubicCycleDoubleCover(processedCycles);
                CDCStrategy cdcStrategy = new AtMostCyclesFoundInducedPrintCDCStrategy(new NoCDCStrategy(), newGraph, numCycles, induced, true);

                if (cdcStrategy.processCDC(newCDC)) {
                    List<CycleDoubleCover> cycleDoubleCovers = cdcStrategy.getCDCs();
                    for (CycleDoubleCover cycleDoubleCover1 : cycleDoubleCovers) {
                        System.out.println("\n\nFOUND CDC OF OBTAINED GRAPH:");
                        for (Cycle cycle : cycleDoubleCover1.getCycles()) {
                            System.out.println(cycle);
                        }
                    }

                    for (Cycle cycle : linkingCycleDoubleCover.getCycles()) {
                        List<Edge> wrongEdges = new ArrayList<>();
                        List<Edge> goodEdges = new ArrayList<>();
                        if (!Collections.disjoint(cycle.getEdges(), allAddedEdges)) {
                            for (Edge edge : allAddedEdges) {
                                if (cycle.getEdges().contains(edge)) {
                                    wrongEdges.add(edge);
                                }
                            }
                        }
                        for (Edge edge : wrongEdges) {
                            for (Set<Edge> keys : correspondingEdges.keySet()) {
                                if (correspondingEdges.get(keys).contains(edge)) {
                                    for (Edge goodEdge : keys) {
                                        if (linkingGraph.getEdges().contains(goodEdge)) {
                                            goodEdges.add(goodEdge);
                                        }
                                    }
                                }
                            }
                        }
                        wrongEdges.forEach(cycle.getEdges()::remove);
                        cycle.getEdges().addAll(goodEdges);
                    }

                    for (Cycle cycle : cycleDoubleCover.getCycles()) {
                        List<Edge> wrongEdges = new ArrayList<>();
                        List<Edge> goodEdges = new ArrayList<>();
                        if (!Collections.disjoint(cycle.getEdges(), allAddedEdges)) {
                            for (Edge edge : allAddedEdges) {
                                if (cycle.getEdges().contains(edge)) {
                                    wrongEdges.add(edge);
                                }
                            }
                        }
                        for (Edge edge : wrongEdges) {
                            for (Set<Edge> keys : correspondingEdges.keySet()) {
                                if (correspondingEdges.get(keys).contains(edge)) {
                                    for (Edge goodEdge : keys) {
                                        if (originalGraph.getEdges().contains(goodEdge)) {
                                            goodEdges.add(goodEdge);
                                        }
                                    }
                                }
                            }
                        }
                        wrongEdges.forEach(cycle.getEdges()::remove);
                        cycle.getEdges().addAll(goodEdges);
                    }
                    System.out.println("CORRESPONDING CDC OF THE SMALLER GRAPH");
                    for (Cycle cycle : linkingCycleDoubleCover.getCycles()) {
                        System.out.println(cycle);
                    }
                    System.out.println("CORRESPONDING CDC OF THE LARGER GRAPH");
                    for (Cycle cycle : cycleDoubleCover.getCycles()) {
                        System.out.println(cycle);
                    }
                    if (any) {
                        throw new StopRecursionException();
                    }

                }

            }
            return;
        }
        for (int i = index; i < keys.size(); i++) {
            Set<Edge> removedEdges = keys.get(i);
            List<Edge> correspondingRemovedEdges = new ArrayList<>(removedEdges);

            Edge removedEdge1 = correspondingRemovedEdges.get(0);
            Edge removedEdge2 = correspondingRemovedEdges.get(1);

            List<Cycle> cyclesOfRemovedEdge1, cyclesOfRemovedEdge2;
            if (originalGraph.getEdges().contains(removedEdge1)) {
                cyclesOfRemovedEdge1 = new ArrayList<>(cycleDoubleCover.getCyclesOfEdge(removedEdge1));
                cyclesOfRemovedEdge2 = new ArrayList<>(linkingCycleDoubleCover.getCyclesOfEdge(removedEdge2));
            } else {
                cyclesOfRemovedEdge1 = new ArrayList<>(linkingCycleDoubleCover.getCyclesOfEdge(removedEdge1));
                cyclesOfRemovedEdge2 = new ArrayList<>(cycleDoubleCover.getCyclesOfEdge(removedEdge2));
            }
            Cycle cycle1_1 = cyclesOfRemovedEdge1.get(0);
            Cycle cycle1_2 = cyclesOfRemovedEdge1.get(1);

            Cycle cycle2_1 = cyclesOfRemovedEdge2.get(0);
            Cycle cycle2_2 = cyclesOfRemovedEdge2.get(1);

            Cycle newCycle1 = new CubicCycle(cycle1_1, cycle2_1);
            Cycle newCycle2 = new CubicCycle(cycle1_2, cycle2_2);

            processedCycles.add(newCycle1);
            processedCycles.add(newCycle2);

            recursion(cycleDoubleCover, linkingCycleDoubleCover, processedCycles, index + 1);

            processedCycles.remove(newCycle1);
            processedCycles.remove(newCycle2);
        }


    }

    /**
     * @inheritDoc
     */
    @Override
    public List<CycleDoubleCover> getCDCs() {
        throw new UnsupportedOperationException();
    }


    private void processAllCycles(Set<Cycle> cycles) {
        for (Cycle cycle : cycles) {
            for (Edge edge : allAddedEdges) {
                cycle.addEdge(edge);
            }
            for (Edge edge : allRemovedEdges) {
                cycle.removeEdge(edge);
            }
        }

    }

    private void unprocessAllCycles(Set<Cycle> cycles) {
        for (Cycle cycle : cycles) {
            cycle.undoRemoveEdge();
            cycle.undoAddedEdge();
        }

    }
}


