package algorithm.graph;

import algorithm.exceptions.InconsistentGraphException;

import java.util.List;

/**
 * Interface for representing a vertex of a graph.
 */
public interface Vertex extends Comparable<Vertex> {
    /**
     * This method adds an adjacent vertex to the list of the adjacent vertices of the vertex.
     *
     * @param neighbor adjacent vertex
     * @throws InconsistentGraphException if adding this neighbor makes the vertex inconsistent
     */
    void addNeighbor(Vertex neighbor) throws InconsistentGraphException;

    /**
     * This method grants access to the adjacent vertices of the vertex.
     *
     * @return list of adjacent vertices
     */
    List<Vertex> getNeighbors();

    /**
     * This method grants access to the number representation of the vertex
     *
     * @return number of the vertex
     */
    int getNumber();

    /**
     * This method sets the number of the vertex.
     *
     * @param number the number of the vertex
     */
    void setNumber(int number);


}
