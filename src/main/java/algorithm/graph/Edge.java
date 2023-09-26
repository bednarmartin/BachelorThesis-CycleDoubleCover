package algorithm.graph;

import java.util.Set;

/**
 * Interface for representing an edge of a graph.
 */
public interface Edge extends Comparable<Edge> {
    /**
     * This method grants access to the first vertex of the edge.
     *
     * @return first vertex of the edge
     */
    Vertex getFirst();

    /**
     * This method grants access to the second vertex of the edge.
     *
     * @return second vertex of the edge
     */
    Vertex getSecond();

    /**
     * This method grants access to the vertices of the edge
     *
     * @return set of vertices of the edge
     */
    Set<Vertex> getVertices();
}
