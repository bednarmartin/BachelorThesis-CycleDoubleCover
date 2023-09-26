package algorithm.graph;

import algorithm.finders.chord.ChordFinder;

import java.util.Set;

/**
 * Interface for representing a cycle of a graph
 */

public interface Cycle {
    /**
     * This method grant access to the edges of the cycle.
     *
     * @return set of the edges of the cycle
     */
    Set<Edge> getEdges();

    /**
     * This method grant access to the chords of the cycle.
     *
     * @param chordFinder algorithm for finding chords
     * @return set of chords of the cycle
     */
    Set<Edge> getChords(ChordFinder chordFinder);

    /**
     * This method grant access to the vertices of the cycle.
     *
     * @return set of the vertices of the cycle
     */
    Set<Vertex> getVertices();

    /**
     * This method allows determining whether the cycle is induced.
     *
     * @param graph the graph
     * @return true if the cycle is induced
     */
    boolean isInduced(Graph graph);

    /**
     * This method grant access to the set of circuits of the cycle.
     *
     * @return set of circuits
     */
    Set<Circuit> getCircuits();

    /**
     * This method allows to remove an edge from the cycle.
     * @param edge the edge to be removed
     */
    void removeEdge(Edge edge);

    /**
     * This method allows to undo the removal of the edges
     */
    void undoRemoveEdge();

    /**
     * This method allows to remove an edge from the cycle permanently.
     * @param edge the edge to be removed permanently.
     */
    void removeEdgePermanently(Edge edge);

    /**
     * This method allows to add an edge to the cycle.
     * @param edge the edge to be added.
     */
    void addEdge(Edge edge);

    /**
     * This method grants access to the added edges.
     * @return set of the added edges
     */
    Set<Edge> getAddedEdges();

    /**
     * This method allows to reverse the addition of the edges.
     */
    void undoAddedEdge();

    /**
     * This method grants access to the removed edges.
     * @return set of the removed edges.
     */
    Set<Edge> getRemovedEdges();

}
