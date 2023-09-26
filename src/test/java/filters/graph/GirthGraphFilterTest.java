package filters.graph;

import algorithm.determiners.bridge.DFSBridgeDeterminer;
import algorithm.determiners.connectivity.ConnectedGraphDeterminer;
import algorithm.determiners.connectivity.DFSConnectedGraphDeterminer;
import algorithm.exceptions.InconsistentGraphException;
import algorithm.exceptions.UnsupportedGraphFormatException;
import algorithm.filters.graph.*;
import algorithm.finders.threecut.CombinationThreeCutFinder;
import algorithm.finders.twocut.CombinationTwoCutFinder;
import algorithm.graph.Circuit;
import algorithm.graph.Graph;
import algorithm.reading.GraphFileFormatAnalyser;
import algorithm.reading.iterator.GraphIterator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import java.util.List;

/**
 * Tests for GirthGraphFilter
 */
public class GirthGraphFilterTest {

    @Test
    @DisplayName("Ensure that the GirthGraphFilter works correctly on 4g3e.txt file")
    public void test4g3e() throws UnsupportedGraphFormatException, IOException, InconsistentGraphException {
        GraphFilter graphFilter = new GirthGraphFilter(3);
        GraphIterator graphIterator = new GraphFileFormatAnalyser().analyseFile("src/test/resources/4g3e.txt", 0).getGraphIterator();
        Graph graph = graphIterator.next();
        Assertions.assertTrue(graphFilter.pass(graph));
        graphFilter = new GirthGraphFilter(4);
        Assertions.assertFalse(graphFilter.pass(graph));
    }

    @Test
    @DisplayName("Ensure that the GirthGraphFilter works correctly on 16g3e5c file")
    public void test16g3e5c() throws UnsupportedGraphFormatException, IOException, InconsistentGraphException {
        GraphFilter graphFilter = new GirthGraphFilter(5);
        GraphIterator graphIterator = new GraphFileFormatAnalyser().analyseFile("src/test/resources/16g3e5c.txt", 0).getGraphIterator();
        while (graphIterator.hasNext()) {
            Graph graph = graphIterator.next();
            Assertions.assertTrue(graphFilter.pass(graph));
        }
    }

    @Test
    @DisplayName("Ensure that the GirthGraphFilter works correctly on 12g3e file")
    public void test14g3e() throws UnsupportedGraphFormatException, IOException, InconsistentGraphException {
        GraphIterator graphIterator = new GraphFileFormatAnalyser().analyseFile("src/test/resources/12g3e.txt", 0).getGraphIterator();
        while (graphIterator.hasNext()) {
            Graph graph = graphIterator.next();
            for (int girth = 4; girth < 7; girth++) {
                GraphFilter graphFilter = new GirthGraphFilter(girth);
                List<Circuit> circuits = graph.getCircuits();
                if (graphFilter.pass(graph)) {
                    for (Circuit circuit : circuits) {
                        if (circuit.getEdges().size() < girth) {
                            Assertions.fail();
                        }
                    }
                } else {
                    boolean hasSuchCircuit = false;
                    for (Circuit circuit : circuits) {
                        if (circuit.getEdges().size() < girth) {
                            hasSuchCircuit = true;
                            break;
                        }
                    }
                    Assertions.assertTrue(hasSuchCircuit);
                }
            }

        }
    }

    @Test
    @DisplayName("Ensure that GirthGraphFilter, ThreeCutGraphFilter, TwoCutGraphFilter and BridgeGraphFilter works correctly together")
    public void testAllTogether() throws UnsupportedGraphFormatException, IOException, InconsistentGraphException {
        GraphIterator graphIterator = new GraphFileFormatAnalyser().analyseFile("src/test/resources/14g3e.txt", 0).getGraphIterator();
        GraphFilter graphFilter = new GirthGraphFilter(5);
        graphFilter = new BridgeGraphFilter(graphFilter, new DFSBridgeDeterminer());
        graphFilter = new TwoCutGraphFilter(graphFilter, new CombinationTwoCutFinder(new DFSConnectedGraphDeterminer()));
        graphFilter = new ThreeCutGraphFilter(graphFilter, new CombinationThreeCutFinder(new DFSConnectedGraphDeterminer()));
        ConnectedGraphDeterminer connectedGraphDeterminer = new DFSConnectedGraphDeterminer();
        while (graphIterator.hasNext()) {
            Graph graph = graphIterator.next();
            List<Circuit> circuits = graph.getCircuits();
            if (graphFilter.pass(graph)) {
                Assertions.assertFalse(new DFSBridgeDeterminer().hasBridge(graph));
                Assertions.assertTrue(new CombinationTwoCutFinder(connectedGraphDeterminer).getTwoCuts(graph).isEmpty());
                Assertions.assertTrue(new CombinationThreeCutFinder(connectedGraphDeterminer).getThreeCuts(graph).isEmpty());
                for (Circuit circuit : circuits) {
                    if (circuit.getEdges().size() < 5) {
                        Assertions.fail();
                    }
                }
            } else {
                boolean hasSuchCircuit = false;
                for (Circuit circuit : circuits) {
                    if (circuit.getEdges().size() < 5) {
                        hasSuchCircuit = true;
                        break;
                    }
                }
                Assertions.assertTrue(hasSuchCircuit || new DFSBridgeDeterminer().hasBridge(graph) ||
                        !new CombinationTwoCutFinder(connectedGraphDeterminer).getTwoCuts(graph).isEmpty() ||
                        !new CombinationThreeCutFinder(connectedGraphDeterminer).getThreeCuts(graph).isEmpty());

            }
        }
    }
}
