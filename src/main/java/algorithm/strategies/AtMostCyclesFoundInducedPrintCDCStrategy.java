package algorithm.strategies;

import algorithm.exceptions.InconsistentGraphException;
import algorithm.exceptions.StopRecursionException;
import algorithm.graph.*;

import java.util.*;

/**
 * CDCStrategy that represents a goal to find any cycle double cover with selected maximum number of (induced) cycles consisting of induced circuits.
 */
public class AtMostCyclesFoundInducedPrintCDCStrategy extends PrintCDCStrategy {
    /**
     * maximum number of cycles in Cycle Double Cover
     */
    private final int maxCycles;
    /**
     * whether the cycles have to be induced
     */
    private final boolean induced;
    /**
     * Map that represents already processed pairs of cycles
     */
    private final Map<Cycle, List<Cycle>> pairs;
    /**
     * True if such CDC was found
     */
    private boolean found;
    /**
     * the graph
     */
    private final Graph graph;
    /**
     * List of CDC strategies to be applied.
     */
    private List<CDCStrategy> cdcStrategies;
    /**
     * List of found CDCs.
     */
    private List<CycleDoubleCover> CDCs;
    /**
     * if just any CDC to be found
     */
    private boolean any;

    /**
     * Constructor
     *
     * @param printCDCStrategy printing CDCStrategy
     * @param graph            the graph
     * @param maxCycles        the maximum number of cycles
     * @param induced          if the CDC ought to be induced
     * @param any              if to find just any CDC.
     * @param strategies       other CDCStrategies
     */
    public AtMostCyclesFoundInducedPrintCDCStrategy(CDCStrategy printCDCStrategy, Graph graph, int maxCycles, boolean induced, boolean any, CDCStrategy... strategies) {
        super(printCDCStrategy);
        this.maxCycles = maxCycles;
        this.induced = induced;
        this.pairs = new HashMap<>();
        this.found = false;
        this.graph = graph;
        this.cdcStrategies = new ArrayList<>(Arrays.asList(strategies));
        this.CDCs = new ArrayList<>();
        this.any = any;
    }

    /**
     * Constructor
     *
     * @param graph      the graph
     * @param maxCycles  the maximum number of cycles
     * @param induced    if the CDC ought to be induced
     * @param any        if to find just any CDC.
     * @param strategies other CDCStrategies
     */
    public AtMostCyclesFoundInducedPrintCDCStrategy(Graph graph, int maxCycles, boolean induced, boolean any, CDCStrategy... strategies) {
        super();
        this.maxCycles = maxCycles;
        this.induced = induced;
        this.pairs = new HashMap<>();
        this.found = false;
        this.graph = graph;
        this.cdcStrategies = new ArrayList<>(Arrays.asList(strategies));
        this.CDCs = new ArrayList<>();
        this.any = any;
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean processCDC(CycleDoubleCover cycleDoubleCover) {
        for (Cycle cycle : cycleDoubleCover.getCycles()) {
            for (Circuit circuit : cycle.getCircuits()) {
                if (!circuit.isInduced(graph)) {
                    return false;
                }
            }
        }
        boolean good = printCDCStrategy.processCDC(cycleDoubleCover);
        if (good) {
            if (cycleDoubleCover.getCycles().size() <= maxCycles) {
                found = true;
                for (CDCStrategy cdcStrategy : cdcStrategies) {
                    if (!cdcStrategy.processCDC(cycleDoubleCover)) {
                        found = false;
                        break;
                    }
                }
                if (found) {
                    CDCs.add(cycleDoubleCover);
                    if (any) {
                        return true;
                    }
                }
            }

            for (Cycle cycle : cycleDoubleCover.getCycles()) {
                pairs.put(cycle, new ArrayList<>());
            }
            try {
                findSuchCycles(cycleDoubleCover);
            } catch (StopRecursionException ignored) {
            }
        }
        return found && good;
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<CycleDoubleCover> getCDCs() {
        return CDCs;
    }

    /**
     * This method tries to find cycle double cover with desired number of (induced) cycles.
     *
     * @param cycleDoubleCover cycle double cover to be processed.
     */
    private void findSuchCycles(CycleDoubleCover cycleDoubleCover) throws StopRecursionException{
        for (Cycle cycle : cycleDoubleCover.getCycles()) {
            for (Cycle cycleToCompare : cycleDoubleCover.getCycles()) {
                if (Collections.disjoint(cycle.getVertices(), cycleToCompare.getVertices())) {
                    if (pairs.get(cycle).contains(cycleToCompare) || pairs.get(cycleToCompare).contains(cycle)) {
                        continue;
                    }
                    pairs.get(cycle).add(cycleToCompare);
                    pairs.get(cycleToCompare).add(cycle);

                    Set<Cycle> cycles = new HashSet<>(cycleDoubleCover.getCycles());
                    cycles.remove(cycle);
                    cycles.remove(cycleToCompare);
                    Cycle newCycle = combineCycles(cycle, cycleToCompare);
                    if (induced) {
                        if (newCycle.isInduced(graph)) {
                            cycles.add(newCycle);
                        } else {
                            continue;
                        }
                    } else {
                        cycles.add(newCycle);
                    }
                    CycleDoubleCover newCycleDoubleCover = new CubicCycleDoubleCover(cycles);
                    if (cycles.size() <= maxCycles) {
                        found = true;
                        for (CDCStrategy cdcStrategy : cdcStrategies) {
                            if (!cdcStrategy.processCDC(newCycleDoubleCover)) {
                                found = false;
                                break;
                            }
                        }
                        if (found) {
                            CDCs.add(newCycleDoubleCover);
                            if (any) {
                                throw new StopRecursionException();
                            }
                        }
                    }
                    pairs.put(newCycle, new ArrayList<>());
                    findSuchCycles(newCycleDoubleCover);
                }
            }
        }
    }

    /**
     * This method combine two cycles into one cycle.
     *
     * @param cycle1 first cycle
     * @param cycle2 second cycle
     * @return combined cycle
     */
    private Cycle combineCycles(Cycle cycle1, Cycle cycle2) {
        Set<Edge> listOfEdges = new HashSet<>();
        listOfEdges.addAll(cycle1.getEdges());
        listOfEdges.addAll(cycle2.getEdges());

        Set<Circuit> newCircuits = new HashSet<>();
        newCircuits.addAll(cycle1.getCircuits());
        newCircuits.addAll(cycle2.getCircuits());

        return new CubicCycle(listOfEdges, newCircuits);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (!induced) {
            stringBuilder.append("SEMI-");
        }
        stringBuilder.append("INDUCED ");
        stringBuilder.append(maxCycles);
        stringBuilder.append("-CDC WAS ");
        if (!found) {
            stringBuilder.append("NOT ");
        }
        stringBuilder.append("FOUND\n");
        return printCDCStrategy.toString() + stringBuilder;
    }
}
