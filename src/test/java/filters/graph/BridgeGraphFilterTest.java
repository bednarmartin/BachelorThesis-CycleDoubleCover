package filters.graph;

import algorithm.determiners.bridge.BridgeDeterminer;
import algorithm.determiners.bridge.DFSBridgeDeterminer;
import algorithm.exceptions.InconsistentGraphException;
import algorithm.exceptions.UnsupportedGraphFormatException;
import algorithm.filters.graph.BridgeGraphFilter;
import algorithm.filters.graph.GraphFilter;
import algorithm.reading.GraphFileFormatAnalyser;
import algorithm.reading.iterator.GraphIterator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * Test for BridgeGraphFilter class
 */
public class BridgeGraphFilterTest {

    private GraphFilter graphFilter;

    @BeforeEach
    public void init() {
        BridgeDeterminer bridgeDeterminer = new DFSBridgeDeterminer();
        graphFilter = new BridgeGraphFilter(bridgeDeterminer);
    }

    @Test
    @DisplayName("Ensure that the BridgeGraphFilter works correctly 4g3e.txt file")
    public void test4g3e() throws UnsupportedGraphFormatException, IOException, InconsistentGraphException {
        GraphIterator graphIterator = new GraphFileFormatAnalyser().analyseFile("src/test/resources/4g3e.txt", 0).getGraphIterator();
        Assertions.assertTrue(graphFilter.pass(graphIterator.next()));
    }

    @Test
    @DisplayName("Ensure that the BridgeGraphFilter works correctly 6g3e.txt file")
    public void test6g3e() throws UnsupportedGraphFormatException, IOException, InconsistentGraphException {
        GraphIterator graphIterator = new GraphFileFormatAnalyser().analyseFile("src/test/resources/6g3e.txt", 0).getGraphIterator();
        Assertions.assertTrue(graphFilter.pass(graphIterator.next()));
        Assertions.assertTrue(graphFilter.pass(graphIterator.next()));
    }

    @Test
    @DisplayName("Ensure that the BridgeGraphFilter works correctly bridge_10.txt file")
    public void testbridge_10() throws UnsupportedGraphFormatException, IOException, InconsistentGraphException {
        GraphIterator graphIterator = new GraphFileFormatAnalyser().analyseFile("src/test/resources/bridge_10.txt", 0).getGraphIterator();
        Assertions.assertFalse(graphFilter.pass(graphIterator.next()));
    }

    @Test
    @DisplayName("Ensure that the BridgeGraphFilter decorated by another BridgeGraphFilter works correctly bridge_10.txt file")
    public void testbridge_10_2() throws UnsupportedGraphFormatException, IOException, InconsistentGraphException {
        GraphIterator graphIterator = new GraphFileFormatAnalyser().analyseFile("src/test/resources/bridge_10.txt", 0).getGraphIterator();
        BridgeDeterminer bridgeDeterminer = new DFSBridgeDeterminer();
        graphFilter = new BridgeGraphFilter(graphFilter, bridgeDeterminer);
        Assertions.assertFalse(graphFilter.pass(graphIterator.next()));
    }

    @Test
    @DisplayName("Ensure that the BridgeGraphFilter decorated by another BridgeGraphFilter works correctly 6g3e.txt file")
    public void test6g3e_2() throws UnsupportedGraphFormatException, IOException, InconsistentGraphException {
        GraphIterator graphIterator = new GraphFileFormatAnalyser().analyseFile("src/test/resources/6g3e.txt", 0).getGraphIterator();
        BridgeDeterminer bridgeDeterminer = new DFSBridgeDeterminer();
        graphFilter = new BridgeGraphFilter(graphFilter, bridgeDeterminer);
        Assertions.assertTrue(graphFilter.pass(graphIterator.next()));
        Assertions.assertTrue(graphFilter.pass(graphIterator.next()));
    }
}