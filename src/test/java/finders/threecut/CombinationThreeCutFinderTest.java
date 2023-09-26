package finders.threecut;

import algorithm.determiners.connectivity.ConnectedGraphDeterminer;
import algorithm.determiners.connectivity.DFSConnectedGraphDeterminer;
import algorithm.exceptions.InconsistentGraphException;
import algorithm.exceptions.UnsupportedGraphFormatException;
import algorithm.finders.threecut.CombinationThreeCutFinder;
import algorithm.graph.Edge;
import algorithm.graph.Graph;
import algorithm.reading.GraphFileFormatAnalyser;
import algorithm.reading.iterator.GraphIterator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

/**
 * Tests for CombinationThreeCutFinder
 */
public class CombinationThreeCutFinderTest {

    private ConnectedGraphDeterminer connectedGraphDeterminer;

    private CombinationThreeCutFinder combinationThreeCutFinder;

    @BeforeEach
    public void init() {
        this.connectedGraphDeterminer = new DFSConnectedGraphDeterminer();
        this.combinationThreeCutFinder = new CombinationThreeCutFinder(connectedGraphDeterminer);
    }

    @Test
    @DisplayName("Ensure that CombinationTwoCutFinder works correctly on 4g3e.txt file")
    public void test4g3e() throws UnsupportedGraphFormatException, IOException, InconsistentGraphException {
        GraphIterator graphIterator = new GraphFileFormatAnalyser().analyseFile("src/test/resources/4g3e.txt", 0).getGraphIterator();
        Assertions.assertTrue(combinationThreeCutFinder.getThreeCuts(graphIterator.next()).isEmpty());
    }

    @Test
    @DisplayName("Ensure that CombinationTwoCutFinder works correctly on 6g3e.txt file")
    public void test6g3e() throws UnsupportedGraphFormatException, IOException, InconsistentGraphException {
        GraphIterator graphIterator = new GraphFileFormatAnalyser().analyseFile("src/test/resources/6g3e.txt", 0).getGraphIterator();
        Assertions.assertFalse(combinationThreeCutFinder.getThreeCuts(graphIterator.next()).isEmpty());
        Assertions.assertTrue(combinationThreeCutFinder.getThreeCuts(graphIterator.next()).isEmpty());
    }

    @Test
    @DisplayName("Ensure that CombinationTwoCutFinder works correctly on 14g3e.txt file")
    public void test14g3e() throws UnsupportedGraphFormatException, IOException, InconsistentGraphException {
        GraphIterator graphIterator = new GraphFileFormatAnalyser().analyseFile("src/test/resources/14g3e.txt",0).getGraphIterator();
        while (graphIterator.hasNext()) {
            Graph graph = graphIterator.next();
            List<List<Edge>> cutEdges = combinationThreeCutFinder.getThreeCuts(graph);
            if (!cutEdges.isEmpty()) {
                for (List<Edge> edges : cutEdges) {
                    edges.forEach(graph.getEdges()::remove);
                    for (Edge e : edges) {
                        e.getFirst().getNeighbors().remove(e.getSecond());
                        e.getSecond().getNeighbors().remove(e.getFirst());
                    }
                    Assertions.assertFalse(connectedGraphDeterminer.isConnected(graph));
                    graph.getEdges().addAll(edges);
                    for (Edge e : edges) {
                        e.getFirst().getNeighbors().add(e.getSecond());
                        e.getSecond().getNeighbors().add(e.getFirst());
                    }
                }

            }

        }
    }
}
