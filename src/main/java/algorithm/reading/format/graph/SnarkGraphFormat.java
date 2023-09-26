package algorithm.reading.format.graph;

import algorithm.exceptions.UnsupportedGraphFormatException;
import algorithm.reading.iterator.SnarkFormatGraphIterator;
import algorithm.reading.iterator.GraphIterator;

import java.io.IOException;

/**
 * Class representing a graph format with snarks.
 */
public class SnarkGraphFormat extends GoodGraphFormat {
    /**
     * @inheritDoc
     */
    @Override
    public GraphIterator getGraphIterator(String path, boolean zeroFirst, int excess) throws IOException{
        return new SnarkFormatGraphIterator(path, zeroFirst, excess);
    }

    /**
     * @inheritDoc
     */
    @Override
    public GraphIterator getGraphIterator(String path, int start, boolean zeroFirst, int excess) throws IOException{
        return new SnarkFormatGraphIterator(path, start, zeroFirst, excess);
    }

    /**
     * @inheritDoc
     */
    @Override
    public GraphIterator getGraphIterator(String path, int start, int end, boolean zeroFirst, int excess) throws IOException{
        return new SnarkFormatGraphIterator(path, start, end, zeroFirst, excess);
    }
}
