package algorithm.graph;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Class representing an edge of the cubic graph.
 */

public class CubicEdge implements Edge {

    /**
     * The first vertex of the edge
     */
    private final Vertex first;
    /**
     * The second vertex of the edge
     */
    private final Vertex second;

    /**
     * Constructor of the class CubicEdge
     *
     * @param first  the first vertex of the edge
     * @param second the second vertex of the edge
     */
    public CubicEdge(Vertex first, Vertex second) {
        if (first.getNumber() < second.getNumber()) {
            this.first = first;
            this.second = second;
        } else {
            this.first = second;
            this.second = first;
        }
    }

    /**
     * @return the first vertex of the edge
     */
    @Override
    public Vertex getFirst() {
        return this.first;
    }

    /**
     * @return the second vertex of the edge
     */
    @Override
    public Vertex getSecond() {
        return this.second;
    }

    /**
     * @return set of the vertices of the edge
     */
    @Override
    public Set<Vertex> getVertices() {
        return new HashSet<>(Arrays.asList(first, second));
    }

    @Override
    public String toString() {
        return "[" + this.first + ", " + this.second + "]";
    }

    @Override
    public int hashCode() {
        return 20 * this.first.hashCode() + 20 * this.second.hashCode() + 36;
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
        }
        boolean firstIsEqual = ((Edge) objectToCompare).getFirst().getNumber() == this.first.getNumber() ||
                ((Edge) objectToCompare).getSecond().getNumber() == this.first.getNumber();
        boolean secondIsEqual = ((Edge) objectToCompare).getFirst().getNumber() == this.second.getNumber() ||
                ((Edge) objectToCompare).getSecond().getNumber() == this.second.getNumber();
        return firstIsEqual && secondIsEqual;
    }

    @Override
    public int compareTo(Edge anotherEdge) {
        int comparison = Integer.compare(this.first.getNumber(), anotherEdge.getFirst().getNumber());
        if (comparison == 0) {
            return Integer.compare(this.second.getNumber(), anotherEdge.getSecond().getNumber());
        }
        return comparison;
    }
}
