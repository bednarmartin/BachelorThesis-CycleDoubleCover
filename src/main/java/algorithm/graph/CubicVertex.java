package algorithm.graph;


import algorithm.exceptions.InconsistentGraphException;

import java.util.ArrayList;
import java.util.List;


/**
 * Class representing a vertex with three adjacent vertices
 */

public class CubicVertex implements Vertex {

    /**
     * the number representation of the vertex
     */
    private int number;

    /**
     * List of the adjacent vertices
     */
    private final List<Vertex> neighbors;

    /**
     * Constructor of the class CubicVertex
     *
     * @param number number representation of the vertex
     */
    public CubicVertex(int number) {
        this.number = number;
        this.neighbors = new ArrayList<>(3);
    }


    /**
     * @inheritDoc
     */
    @Override
    public int getNumber() {
        return this.number;
    }

    @Override
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * @inheritDoc Vertex should have 3 adjacent vertices.
     */
    @Override
    public void addNeighbor(Vertex neighbor) throws InconsistentGraphException {
        if (this.neighbors.size() >= 3) {
            throw new InconsistentGraphException("Trying to add more than 3 adjacent vertices!");
        }
        if (this.neighbors.contains(neighbor)) {
            throw new InconsistentGraphException("Trying to add an adjacent vertex that's already in the list");
        }
        if (neighbor.getNumber() == this.number) {
            throw new InconsistentGraphException("Trying to add a loop");
        }
        if (!neighbor.getNeighbors().contains(this)) {
            neighbor.getNeighbors().add(this);
        }
        this.neighbors.add(neighbor);
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<Vertex> getNeighbors() {
        return this.neighbors;
    }


    @Override
    public String toString() {
        return String.valueOf(this.number);
    }

    @Override
    public int hashCode() {
        return this.number;
    }

    @Override
    public boolean equals(Object objectToCompare) {
        if (this == objectToCompare) {
            return true;
        }
        if (objectToCompare == null) {
            return false;
        }
        if (getClass() != objectToCompare.getClass()) {
            return false;
        } else {
            return ((Vertex) objectToCompare).getNumber() == this.number;
        }
    }

    @Override
    public int compareTo(Vertex anotherVertex) {
        return Integer.compare(this.number, anotherVertex.getNumber());
    }
}
