package algorithm.reading.format.graph;

import algorithm.reading.iterator.AdjacentFormatGraphIterator;
import algorithm.reading.iterator.GraphIterator;

import java.io.IOException;

/**
 * Class representing a graph format of a file where each row represents a list of adjacent vertices
 */
public class AdjacentGraphFormat extends GoodGraphFormat {
    /**
     * @inheritDoc
     */
    @Override
    public GraphIterator getGraphIterator(String path, boolean zeroFirst, int excess) throws IOException {
        return new AdjacentFormatGraphIterator(path, zeroFirst, excess);
    }

    /**
     * @inheritDoc
     */
    @Override
    public GraphIterator getGraphIterator(String path, int start, boolean zeroFirst, int excess) throws IOException {
        return new AdjacentFormatGraphIterator(path, start, zeroFirst, excess);
    }

    /**
     * @inheritDoc
     */
    @Override
    public GraphIterator getGraphIterator(String path, int start, int end, boolean zeroFirst, int excess) throws IOException {
        return new AdjacentFormatGraphIterator(path, start, end, zeroFirst, excess);
    }


}
