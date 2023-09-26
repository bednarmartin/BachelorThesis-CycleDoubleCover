package filters.graph;

import algorithm.determiners.bridge.BridgeDeterminer;
import algorithm.determiners.bridge.DFSBridgeDeterminer;
import algorithm.determiners.connectivity.ConnectedGraphDeterminer;
import algorithm.determiners.connectivity.DFSConnectedGraphDeterminer;
import algorithm.exceptions.InconsistentGraphException;
import algorithm.exceptions.UnsupportedGraphFormatException;
import algorithm.filters.graph.BridgeGraphFilter;
import algorithm.filters.graph.GraphFilter;
import algorithm.filters.graph.ThreeCutGraphFilter;
import algorithm.filters.graph.TwoCutGraphFilter;
import algorithm.finders.threecut.CombinationThreeCutFinder;
import algorithm.finders.threecut.ThreeCutFinder;
import algorithm.finders.twocut.CombinationTwoCutFinder;
import algorithm.graph.Graph;
import algorithm.reading.GraphFileFormatAnalyser;
import algorithm.reading.iterator.GraphIterator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * Tests for ThreeCutGraphFilter class
 */
public class ThreeCutFilterTest {

    private GraphFilter graphFilter;

    private ThreeCutFinder threeCutFinder;

    private ConnectedGraphDeterminer connectedGraphDeterminer;

    @BeforeEach
    public void init() {
        connectedGraphDeterminer = new DFSConnectedGraphDeterminer();
        threeCutFinder = new CombinationThreeCutFinder(connectedGraphDeterminer);
        graphFilter = new ThreeCutGraphFilter(threeCutFinder);
    }

    @Test
    @DisplayName("Ensure that the ThreeCutGraphFilter works correctly 4g3e.txt file")
    public void test4g3e() throws UnsupportedGraphFormatException, IOException, InconsistentGraphException {
        GraphIterator graphIterator = new GraphFileFormatAnalyser().analyseFile("src/test/resources/4g3e.txt", 0).getGraphIterator();
        Assertions.assertTrue(graphFilter.pass(graphIterator.next()));
    }

    @Test
    @DisplayName("Ensure that the ThreeCutGraphFilter works correctly 6g3e.txt file")
    public void test6g3e() throws UnsupportedGraphFormatException, IOException, InconsistentGraphException {
        GraphIterator graphIterator = new GraphFileFormatAnalyser().analyseFile("src/test/resources/6g3e.txt", 0).getGraphIterator();
        Assertions.assertFalse(graphFilter.pass(graphIterator.next()));
        Assertions.assertTrue(graphFilter.pass(graphIterator.next()));
    }

    @Test
    @DisplayName("Ensure that the ThreeCutGraphFilter works correctly bridge_10.txt file")
    public void testbridge_10() throws UnsupportedGraphFormatException, IOException, InconsistentGraphException {
        GraphIterator graphIterator = new GraphFileFormatAnalyser().analyseFile("src/test/resources/bridge_10.txt",0 ).getGraphIterator();
        Assertions.assertFalse(graphFilter.pass(graphIterator.next()));
    }

    @Test
    @DisplayName("Ensure that the ThreeCutGraphFilter decorated by another BridgeGraphFilter works correctly bridge_10.txt file")
    public void testbridge_10_2() throws UnsupportedGraphFormatException, IOException, InconsistentGraphException {
        GraphIterator graphIterator = new GraphFileFormatAnalyser().analyseFile("src/test/resources/bridge_10.txt", 0).getGraphIterator();
        BridgeDeterminer bridgeDeterminer = new DFSBridgeDeterminer();
        graphFilter = new BridgeGraphFilter(graphFilter, bridgeDeterminer);
        Assertions.assertFalse(graphFilter.pass(graphIterator.next()));
    }

    @Test
    @DisplayName("Ensure that the ThreeCutGraphFilter decorated by another ThreeCutGraphFilter works correctly 6g3e.txt file")
    public void test6g3e_2() throws UnsupportedGraphFormatException, IOException, InconsistentGraphException {
        GraphIterator graphIterator = new GraphFileFormatAnalyser().analyseFile("src/test/resources/6g3e.txt", 0).getGraphIterator();
        graphFilter = new ThreeCutGraphFilter(graphFilter, threeCutFinder);
        Assertions.assertFalse(graphFilter.pass(graphIterator.next()));
        Assertions.assertTrue(graphFilter.pass(graphIterator.next()));
    }

    @Test
    @DisplayName("Ensure that ThreeCutGraphFilter and BridgeGraphFilter works correctly together")
    public void testTwoTogether() throws UnsupportedGraphFormatException, IOException, InconsistentGraphException {
        GraphIterator graphIterator = new GraphFileFormatAnalyser().analyseFile("src/test/resources/14g3e.txt", 0).getGraphIterator();
        graphFilter = new BridgeGraphFilter(graphFilter, new DFSBridgeDeterminer());
        while (graphIterator.hasNext()) {
            Graph graph = graphIterator.next();
            if (graphFilter.pass(graph)) {
                Assertions.assertFalse(new DFSBridgeDeterminer().hasBridge(graph));
                Assertions.assertTrue(new CombinationThreeCutFinder(connectedGraphDeterminer).getThreeCuts(graph).isEmpty());
            }
        }
    }

    @Test
    @DisplayName("Ensure that ThreeCutGraphFilter, TwoCutGraphFilter and BridgeGraphFilter works correctly together")
    public void testAllTogether() throws UnsupportedGraphFormatException, IOException, InconsistentGraphException {
        GraphIterator graphIterator = new GraphFileFormatAnalyser().analyseFile("src/test/resources/14g3e.txt", 0).getGraphIterator();
        graphFilter = new BridgeGraphFilter(graphFilter, new DFSBridgeDeterminer());
        graphFilter = new TwoCutGraphFilter(graphFilter, new CombinationTwoCutFinder(new DFSConnectedGraphDeterminer()));
        while (graphIterator.hasNext()) {
            Graph graph = graphIterator.next();
            if (graphFilter.pass(graph)) {
                Assertions.assertFalse(new DFSBridgeDeterminer().hasBridge(graph));
                Assertions.assertTrue(new CombinationTwoCutFinder(connectedGraphDeterminer).getTwoCuts(graph).isEmpty());
                Assertions.assertTrue(new CombinationThreeCutFinder(connectedGraphDeterminer).getThreeCuts(graph).isEmpty());
            } else {
                Assertions.assertTrue(new DFSBridgeDeterminer().hasBridge(graph) ||
                        !new CombinationTwoCutFinder(connectedGraphDeterminer).getTwoCuts(graph).isEmpty() ||
                        !new CombinationThreeCutFinder(connectedGraphDeterminer).getThreeCuts(graph).isEmpty());
            }
        }
    }
}
