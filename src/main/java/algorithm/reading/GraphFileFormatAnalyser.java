package algorithm.reading;

import algorithm.reading.format.graph.AdjacentGraphFormat;
import algorithm.reading.format.graph.ErrorGraphFormat;
import algorithm.reading.format.graph.SnarkGraphFormat;
import algorithm.reading.iterator.AdjacentFormatGraphIterator;
import algorithm.reading.iterator.GraphIterator;
import algorithm.reading.iterator.SnarkFormatGraphIterator;

/**
 * Class representing a format analyser for graphs.
 */
public class GraphFileFormatAnalyser {

    /**
     * This method analyse file with graphs.
     *
     * @param path   path to the file with the graphs
     * @param excess the excess from the zero
     * @return the analysis of the graph file
     */
    public GraphFileAnalysis analyseFile(String path, int excess) {
        try {
            for (boolean value : new boolean[]{true, false}) {
                if (isGraphFormat(new AdjacentFormatGraphIterator(path, value, excess))) {
                    GraphIterator graphIterator = new AdjacentFormatGraphIterator(path, value, excess);
                    return new GraphFileAnalysis(new AdjacentGraphFormat(), path, graphIterator.getNumberOfGraphs(), graphIterator.getNumberOfVertices(), value, excess);
                } else if (isGraphFormat(new SnarkFormatGraphIterator(path, value, excess))) {
                    GraphIterator graphIterator = new SnarkFormatGraphIterator(path, value, excess);
                    return new GraphFileAnalysis(new SnarkGraphFormat(), path, graphIterator.getNumberOfGraphs(), graphIterator.getNumberOfVertices(), value, excess);
                }
            }
        } catch (Exception ignored) {
        }
        return new GraphFileAnalysis(new ErrorGraphFormat(), path, 0, 0, false, 0);
    }

    /**
     * This method determine if the graph has adjacent vertices format.
     *
     * @param graphIterator iterator of graphs.
     * @return true if the graph format is 'Each row = list of adjacent vertices'
     */
    private boolean isGraphFormat(GraphIterator graphIterator) {
        try {
            if (!graphIterator.hasNext()) {
                return false;
            }
            while (graphIterator.hasNext()) {
                graphIterator.next();
            }
        } catch (Exception e) {
            return false;
        }
        return true;

    }
}
