package determiners.connectivity;

import algorithm.determiners.connectivity.ConnectedGraphDeterminer;
import algorithm.determiners.connectivity.DFSConnectedGraphDeterminer;
import algorithm.exceptions.InconsistentGraphException;
import algorithm.exceptions.UnsupportedGraphFormatException;
import algorithm.reading.GraphFileFormatAnalyser;
import algorithm.reading.iterator.GraphIterator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * Tests for DFSConnectedGraphDeterminer class
 */
public class DFSConnectedGraphDeterminerTest {

    private ConnectedGraphDeterminer connectedGraphDeterminer;

    @BeforeEach
    public void init() {
        this.connectedGraphDeterminer = new DFSConnectedGraphDeterminer();
    }

    @Test
    @DisplayName("Make sure that DFSConnectedGraphDeterminer works correctly on 4g3e.txt file")
    public void test4g3e() throws UnsupportedGraphFormatException, IOException, InconsistentGraphException {
        GraphIterator graphIterator = new GraphFileFormatAnalyser().analyseFile("src/test/resources/4g3e.txt", 0).getGraphIterator();
        Assertions.assertTrue(connectedGraphDeterminer.isConnected(graphIterator.next()));
    }

    @Test
    @DisplayName("Make sure that DFSConnectedGraphDeterminer works correctly on 6g3e.txt file")
    public void test6g3e() throws UnsupportedGraphFormatException, IOException, InconsistentGraphException {
        GraphIterator graphIterator = new GraphFileFormatAnalyser().analyseFile("src/test/resources/6g3e.txt", 0).getGraphIterator();
        Assertions.assertTrue(connectedGraphDeterminer.isConnected(graphIterator.next()));
        Assertions.assertTrue(connectedGraphDeterminer.isConnected(graphIterator.next()));
    }

    @Test
    @DisplayName("Make sure that DFSConnectedGraphDeterminer works correctly on bridge_10.txt file")
    public void testbridge_10() throws UnsupportedGraphFormatException, IOException, InconsistentGraphException {
        GraphIterator graphIterator = new GraphFileFormatAnalyser().analyseFile("src/test/resources/bridge_10.txt", 0).getGraphIterator();
        Assertions.assertTrue(connectedGraphDeterminer.isConnected(graphIterator.next()));
    }

    @Test
    @DisplayName("Make sure that DFSConnectedGraphDeterminer works correctly on disconnected_8.txt file")
    public void testdisconnected_8() throws UnsupportedGraphFormatException, IOException, InconsistentGraphException {
        GraphIterator graphIterator = new GraphFileFormatAnalyser().analyseFile("src/test/resources/disconnected_8.txt", 0).getGraphIterator();
        Assertions.assertFalse(connectedGraphDeterminer.isConnected(graphIterator.next()));
    }
}
