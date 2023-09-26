package algorithm.reading.format.graph;

import algorithm.exceptions.UnsupportedGraphFormatException;
import algorithm.reading.iterator.GraphIterator;
import application.center.vboxes.AnalysisVBox;

import java.io.IOException;

/**
 * Interface for representing format of the graph.
 */
public interface GraphFormat {
    /**
     * This method grants access to graph iterator for reading graphs
     *
     * @param path      path to the file
     * @param zeroFirst if the vertices are denoted from 0
     * @param excess    the excess from 0
     * @return graph iterator
     * @throws IOException                     if reading fails
     * @throws UnsupportedGraphFormatException if the format is unsupported
     */
    GraphIterator getGraphIterator(String path, boolean zeroFirst, int excess) throws IOException, UnsupportedGraphFormatException;

    /**
     * This method grants access to graph iterator for reading graphs with option to select start
     *
     * @param path      path to the file
     * @param start     index of the  first graph
     * @param zeroFirst if the vertices are denoted from 0
     * @param excess    the excess from 0
     * @return graph iterator
     * @throws IOException                     if reading fails
     * @throws UnsupportedGraphFormatException if the format is unsupported
     */
    GraphIterator getGraphIterator(String path, int start, boolean zeroFirst, int excess) throws IOException, UnsupportedGraphFormatException;

    /**
     * This method grants access to graph iterator for reading graphs with option to select start and the end
     *
     * @param path      path to the file
     * @param start     index of the first graph
     * @param end       index of the last graph
     * @param zeroFirst if the vertices are denoted from 0
     * @param excess    the excess from 0
     * @return graph iterator
     * @throws IOException                     if reading fails
     * @throws UnsupportedGraphFormatException if the format is unsupported
     */
    GraphIterator getGraphIterator(String path, int start, int end, boolean zeroFirst, int excess) throws IOException, UnsupportedGraphFormatException;

    /**
     * This method allows graph format to update AnalysisVBox according to the graph format
     *
     * @param analysisVBox AnalysisVBox to update
     */
    void updateAnalysisVBox(AnalysisVBox analysisVBox);
}
