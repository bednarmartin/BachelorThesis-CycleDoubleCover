package determiners.bridge;

import algorithm.determiners.bridge.BridgeDeterminer;
import algorithm.determiners.bridge.DFSBridgeDeterminer;
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
 * Tests for DFSBridgeDeterminer class
 */
public class DFSBridgeDeterminerTest {

    private BridgeDeterminer bridgeDeterminer;

    @BeforeEach
    public void init() {
        this.bridgeDeterminer = new DFSBridgeDeterminer();
    }

    @Test
    @DisplayName("Make sure that DFSBridgeDeterminer works correctly on 4g3e.txt file")
    public void test4g3e() throws UnsupportedGraphFormatException, IOException, InconsistentGraphException {
        GraphIterator graphIterator = new GraphFileFormatAnalyser().analyseFile("src/test/resources/4g3e.txt", 0).getGraphIterator();
        Assertions.assertFalse(bridgeDeterminer.hasBridge(graphIterator.next()));
    }

    @Test
    @DisplayName("Make sure that DFSBridgeDeterminer works correctly on 6g3e.txt file")
    public void test6g3e() throws UnsupportedGraphFormatException, IOException, InconsistentGraphException {
        GraphIterator graphIterator = new GraphFileFormatAnalyser().analyseFile("src/test/resources/6g3e.txt", 0).getGraphIterator();
        Assertions.assertFalse(bridgeDeterminer.hasBridge(graphIterator.next()));
        Assertions.assertFalse(bridgeDeterminer.hasBridge(graphIterator.next()));
    }

    @Test
    @DisplayName("Make sure that DFSBridgeDeterminer works correctly on bridge_10.txt file")
    public void testbridge_10() throws UnsupportedGraphFormatException, IOException, InconsistentGraphException {
        GraphIterator graphIterator = new GraphFileFormatAnalyser().analyseFile("src/test/resources/bridge_10.txt", 0).getGraphIterator();
        Assertions.assertTrue(bridgeDeterminer.hasBridge(graphIterator.next()));
    }
}
