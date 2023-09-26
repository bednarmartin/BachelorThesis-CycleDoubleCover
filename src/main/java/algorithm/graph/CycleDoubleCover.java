package algorithm.graph;

import algorithm.finders.strongedge.StrongEdgesFinder;

import java.util.Set;

/**
 * Interface for representing a cycle double cover.
 */
public interface CycleDoubleCover {
    /**
     * This method grant access to the cycles of the cycle double cover.
     *
     * @return set of cycles of the cycle double cover
     */
    Set<Cycle> getCycles();

    /**
     * This method grant access to the strong edges of the cycle double cover.
     *
     * @param strongEdgesFinder algorithm for finding strong edges
     * @return set of strong edges of the cycle double cover.
     */
    Set<Edge> getStrongEdges(StrongEdgesFinder strongEdgesFinder);

    /**
     * This method grants access to the cycles that contain the edge.
     *
     * @param edge the considered edge
     * @return set of cycles that contain the edge
     */
    Set<Cycle> getCyclesOfEdge(Edge edge);

}
