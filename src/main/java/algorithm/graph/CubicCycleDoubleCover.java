package algorithm.graph;

import algorithm.finders.strongedge.StrongEdgesFinder;

import java.util.HashSet;
import java.util.Set;

/**
 * Class representing a cycle double cover of cubic graph.
 */
public class CubicCycleDoubleCover implements CycleDoubleCover {
    /**
     * The cycles of the CDC.
     */
    private final Set<Cycle> cycles;

    /**
     * Constructor
     *
     * @param cycles the cycles of the CDC
     */
    public CubicCycleDoubleCover(Set<Cycle> cycles) {
        this.cycles = cycles;
    }


    /**
     * @inheritDoc
     */
    @Override
    public Set<Cycle> getCycles() {
        return cycles;
    }

    /**
     * @inheritDoc
     */
    @Override
    public Set<Edge> getStrongEdges(StrongEdgesFinder strongEdgesFinder) {
        return strongEdgesFinder.getStrongEdges(this);
    }

    /**
     * @inheritDoc
     */
    @Override
    public Set<Cycle> getCyclesOfEdge(Edge edge) {
        Set<Cycle> cyclesOfEdge = new HashSet<>();
        for (Cycle cycle : cycles) {
            if (cycle.getEdges().contains(edge) || cycle.getRemovedEdges().contains(edge)) {
                cyclesOfEdge.add(cycle);
            }
        }
        return cyclesOfEdge;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Cycle cycle : cycles) {
            stringBuilder.append(cycle);
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

}
