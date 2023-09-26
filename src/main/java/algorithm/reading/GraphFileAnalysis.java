package algorithm.reading;

import algorithm.exceptions.UnsupportedGraphFormatException;
import algorithm.reading.format.graph.GraphFormat;
import algorithm.reading.iterator.GraphIterator;

import java.io.IOException;

/**
 * Class representing an analysis of a graph file.
 */
public class GraphFileAnalysis {
    /**
     * number of graphs in the file.
     */
    private final int numberOfGraphs;
    /**
     * number of vertices of the graphs in the file.
     */
    private final int numberOfVertices;

    /**
     * graph format of the file.
     */
    private final GraphFormat graphFormat;
    /**
     * path to the file.
     */
    private final String path;
    /**
     * if the vertices are denoted from 0
     */
    private final boolean zeroFirst;
    /**
     * the excess from 0
     */
    private final int excess;

    /**
     * Constructor for FileAnalysis class.
     *
     * @param graphFormat      format of the file
     * @param path             path to the file
     * @param numberOfGraphs   number of graphs in the file
     * @param numberOfVertices number of vertices of the graphs in the file
     * @param zeroFirst        if the vertices are denoted from 0
     * @param excess           the excess from 0
     */
    public GraphFileAnalysis(GraphFormat graphFormat, String path, int numberOfGraphs, int numberOfVertices, boolean zeroFirst, int excess) {
        this.graphFormat = graphFormat;
        this.numberOfGraphs = numberOfGraphs;
        this.numberOfVertices = numberOfVertices;
        this.path = path;
        this.zeroFirst = zeroFirst;
        this.excess = excess;
    }

    /**
     * This method grant access to GraphIterator for reading graphs.
     *
     * @return GraphIterator for reading graphs
     * @throws IOException                     if reading fails
     * @throws UnsupportedGraphFormatException if format is unsupported
     */
    public GraphIterator getGraphIterator() throws IOException, UnsupportedGraphFormatException {
        return graphFormat.getGraphIterator(path, zeroFirst, excess);
    }

    /**
     * This method grant access to GraphIterator for reading graphs with option to select start.
     *
     * @param start index of graph to begin with
     * @return GraphIterator for reading graphs
     * @throws IOException                     if reading fails
     * @throws UnsupportedGraphFormatException if format is unsupported
     */
    public GraphIterator getGraphIterator(int start) throws IOException, UnsupportedGraphFormatException {
        return graphFormat.getGraphIterator(path, start, zeroFirst, excess);
    }

    /**
     * This method grant access to GraphIterator for reading graphs with option to select start and end.
     *
     * @param start index of graph to begin with
     * @param end   index of graph to end with
     * @return GraphIterator for reading graphs
     * @throws IOException                     if reading fails
     * @throws UnsupportedGraphFormatException if format is unsupported
     */
    public GraphIterator getGraphIterator(int start, int end) throws IOException, UnsupportedGraphFormatException {
        return graphFormat.getGraphIterator(path, start, end, zeroFirst, excess);
    }

    /**
     * This method grant access to number of graphs.
     *
     * @return number of graphs
     */
    public int getNumberOfGraphs() {
        return numberOfGraphs;
    }

    /**
     * This method grant access to number of vertices of the graphs.
     *
     * @return number of vertices of the graphs
     */
    public int getNumberOfVertices() {
        return numberOfVertices;
    }

    /**
     * This method grant access to format of the file.
     *
     * @return format of the file
     */
    public GraphFormat getFormat() {
        return graphFormat;
    }
}
