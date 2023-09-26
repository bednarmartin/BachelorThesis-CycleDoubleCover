package algorithm.reading.iterator;


import algorithm.exceptions.InconsistentGraphException;
import algorithm.graph.Graph;

import java.io.IOException;

/**
 * Interface for reading graphs
 */
public interface GraphIterator {
    /**
     * This method grant access to the graph count.
     *
     * @return graph count in the file
     */
    int getNumberOfGraphs();

    /**
     * This method grant access to the number of vertices of the graph.
     *
     * @return number of vertices of the graph
     */
    int getNumberOfVertices();

    /**
     * This method grant access to the number of edges of the graph
     *
     * @return number of edges of the graph
     */
    int getNumberOfEdges();

    /**
     * This method read another graph.
     *
     * @return graph
     * @throws IOException                if reading fails
     * @throws InconsistentGraphException if the graph is inconsistent
     */
    Graph next() throws IOException, InconsistentGraphException;

    /**
     * This method allows checking if the reading can continue.
     *
     * @return true if another graph can be read
     */
    boolean hasNext();

    /**
     * This method allows getting actual index of read graph.
     *
     * @return actual index
     */
    int actualIndex();

}
