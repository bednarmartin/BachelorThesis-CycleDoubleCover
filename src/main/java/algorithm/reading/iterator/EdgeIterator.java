package algorithm.reading.iterator;

import algorithm.graph.Edge;

import java.io.IOException;

/**
 * Interface for iterator of edges.
 */
public interface EdgeIterator {

    /**
     * This method grant access to the number of edges to be read.
     *
     * @return number of edges
     */
    int getNumberOfEdges();

    /**
     * This method read another edge.
     *
     * @return edge
     * @throws IOException if reading fails
     */
    Edge next() throws IOException;

    /**
     * This method allows checking if the reading can continue.
     *
     * @return true if another edge can be read
     */
    boolean hasNext();

    /**
     * This method allows getting actual index of read edge.
     *
     * @return actual index
     */
    int actualIndex();

}


