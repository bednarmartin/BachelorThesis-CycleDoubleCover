package algorithm.strategies;

import algorithm.finders.strongedge.StrongEdgesFinder;
import algorithm.graph.CycleDoubleCover;
import algorithm.graph.Graph;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * CDCStrategy that represents a goal to find possible numbers of strong edges in CDCs.
 */
public class StrongEdgesPrintCDCStrategy extends PrintCDCStrategy {
    /**
     * sorted set of possible numbers of strong edges.
     */
    private final Set<Integer> possibleStrongEdges;
    /**
     * strong edges finder.
     */
    private final StrongEdgesFinder strongEdgesFinder;
    /**
     * the graph
     */
    private final Graph graph;

    /**
     * Constructor
     *
     * @param strongEdgesFinder strong edges finder
     */
    public StrongEdgesPrintCDCStrategy(CDCStrategy printCDCStrategy, StrongEdgesFinder strongEdgesFinder, Graph graph) {
        super(printCDCStrategy);
        possibleStrongEdges = new TreeSet<>();
        this.strongEdgesFinder = strongEdgesFinder;
        this.graph = graph;
    }

    /**
     * Constructor
     *
     * @param strongEdgesFinder strong edges finder
     */
    public StrongEdgesPrintCDCStrategy(StrongEdgesFinder strongEdgesFinder, Graph graph) {
        super();
        possibleStrongEdges = new TreeSet<>();
        this.strongEdgesFinder = strongEdgesFinder;
        this.graph = graph;
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean processCDC(CycleDoubleCover cycleDoubleCover) {
        boolean good = printCDCStrategy.processCDC(cycleDoubleCover);
        if (good) {
            possibleStrongEdges.add(cycleDoubleCover.getStrongEdges(strongEdgesFinder).size());
        }
        return good;
    }

    /**
     * @InheritDoc
     */
    @Override
    public List<CycleDoubleCover> getCDCs() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return printCDCStrategy.toString() + "POSSIBLE NUMBERS OF STRONG EDGES: " + possibleStrongEdges.toString() + " OUT OF " + graph.getEdges().size() + " EDGES\n";
    }

}
