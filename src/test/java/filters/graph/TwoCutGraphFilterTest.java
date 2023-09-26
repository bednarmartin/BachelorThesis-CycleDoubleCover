package filters.graph;

import algorithm.determiners.bridge.BridgeDeterminer;
import algorithm.determiners.bridge.DFSBridgeDeterminer;
import algorithm.determiners.connectivity.ConnectedGraphDeterminer;
import algorithm.determiners.connectivity.DFSConnectedGraphDeterminer;
import algorithm.exceptions.InconsistentGraphException;
import algorithm.exceptions.UnsupportedGraphFormatException;
import algorithm.filters.graph.BridgeGraphFilter;
import algorithm.filters.graph.GraphFilter;
import algorithm.filters.graph.TwoCutGraphFilter;
import algorithm.finders.twocut.CombinationTwoCutFinder;
import algorithm.finders.twocut.TwoCutFinder;
import algorithm.graph.Graph;
import algorithm.reading.GraphFileFormatAnalyser;
import algorithm.reading.iterator.GraphIterator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * Tests for TwoCutGraphFilter class
 */
public class TwoCutGraphFilterTest {

    private GraphFilter graphFilter;

    private TwoCutFinder twoCutFinder;

    private ConnectedGraphDeterminer connectedGraphDeterminer;

    @BeforeEach
    public void init() {
        connectedGraphDeterminer = new DFSConnectedGraphDeterminer();
        twoCutFinder = new CombinationTwoCutFinder(connectedGraphDeterminer);
        graphFilter = new TwoCutGraphFilter(twoCutFinder);
    }

    @Test
    @DisplayName("Ensure that the TwoCutGraphFilter works correctly 4g3e.txt file")
    public void test4g3e() throws UnsupportedGraphFormatException, IOException, InconsistentGraphException {
        GraphIterator graphIterator = new GraphFileFormatAnalyser().analyseFile("src/test/resources/4g3e.txt", 0).getGraphIterator();
        Assertions.assertTrue(graphFilter.pass(graphIterator.next()));
    }

    @Test
    @DisplayName("Ensure that the TwoCutGraphFilter works correctly 6g3e.txt file")
    public void test6g3e() throws UnsupportedGraphFormatException, IOException, InconsistentGraphException {
        GraphIterator graphIterator = new GraphFileFormatAnalyser().analyseFile("src/test/resources/6g3e.txt", 0).getGraphIterator();
        Assertions.assertTrue(graphFilter.pass(graphIterator.next()));
        Assertions.assertTrue(graphFilter.pass(graphIterator.next()));
    }

    @Test
    @DisplayName("Ensure that the TwoCutGraphFilter works correctly bridge_10.txt file")
    public void testbridge_10() throws UnsupportedGraphFormatException, IOException, InconsistentGraphException {
        GraphIterator graphIterator = new GraphFileFormatAnalyser().analyseFile("src/test/resources/bridge_10.txt", 0).getGraphIterator();
        Assertions.assertFalse(graphFilter.pass(graphIterator.next()));
    }

    @Test
    @DisplayName("Ensure that the TwoCutGraphFilter decorated by another BridgeGraphFilter works correctly bridge_10.txt file")
    public void testbridge_10_2() throws UnsupportedGraphFormatException, IOException, InconsistentGraphException {
        GraphIterator graphIterator = new GraphFileFormatAnalyser().analyseFile("src/test/resources/bridge_10.txt",0 ).getGraphIterator();
        BridgeDeterminer bridgeDeterminer = new DFSBridgeDeterminer();
        graphFilter = new BridgeGraphFilter(graphFilter, bridgeDeterminer);
        Assertions.assertFalse(graphFilter.pass(graphIterator.next()));
    }

    @Test
    @DisplayName("Ensure that the TwoCutGraphFilter decorated by another BridgeGraphFilter works correctly 6g3e.txt file")
    public void test6g3e_2() throws UnsupportedGraphFormatException, IOException, InconsistentGraphException {
        GraphIterator graphIterator = new GraphFileFormatAnalyser().analyseFile("src/test/resources/6g3e.txt",0 ).getGraphIterator();
        BridgeDeterminer bridgeDeterminer = new DFSBridgeDeterminer();
        graphFilter = new BridgeGraphFilter(graphFilter, bridgeDeterminer);
        Assertions.assertTrue(graphFilter.pass(graphIterator.next()));
        Assertions.assertTrue(graphFilter.pass(graphIterator.next()));
    }

    @Test
    @DisplayName("Ensure that TwoCutGraphFilter and BridgeGraphFilter works correctly together")
    public void testTogether() throws UnsupportedGraphFormatException, IOException, InconsistentGraphException {
        GraphIterator graphIterator = new GraphFileFormatAnalyser().analyseFile("src/test/resources/14g3e.txt", 0).getGraphIterator();
        graphFilter = new BridgeGraphFilter(graphFilter, new DFSBridgeDeterminer());
        while (graphIterator.hasNext()) {
            Graph graph = graphIterator.next();
            if (graphFilter.pass(graph)) {
                Assertions.assertFalse(new DFSBridgeDeterminer().hasBridge(graph));
                Assertions.assertTrue(new CombinationTwoCutFinder(connectedGraphDeterminer).getTwoCuts(graph).isEmpty());
            }
        }
    }
}
