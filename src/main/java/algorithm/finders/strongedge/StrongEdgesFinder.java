package algorithm.finders.strongedge;

import algorithm.graph.CycleDoubleCover;
import algorithm.graph.Edge;

import java.util.Set;

/**
 * Interface for finding the strong edges of the Cycle Double Cover.
 */
public interface StrongEdgesFinder {
    /**
     * This method finds such edges that their end vertices are not covered by the same circuits.
     *
     * @param cycleDoubleCover Cycle Double Cover to be processed
     * @return Set of strong edges
     */
    Set<Edge> getStrongEdges(CycleDoubleCover cycleDoubleCover);
}
