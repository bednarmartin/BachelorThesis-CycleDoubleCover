package graph;

import algorithm.determiners.bridge.DFSBridgeDeterminer;
import algorithm.determiners.connectivity.DFSConnectedGraphDeterminer;
import algorithm.exceptions.InconsistentCircuitException;
import algorithm.exceptions.InconsistentGraphException;
import algorithm.exceptions.UnsupportedGraphFormatException;
import algorithm.graph.*;
import algorithm.reading.GraphFileFormatAnalyser;
import algorithm.reading.iterator.CircuitIterator;
import algorithm.reading.iterator.GraphIterator;
import algorithm.reading.iterator.VerticesFormatCircuitIterator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

/**
 * Tests for Cubic Graph
 */
public class CubicGraphTest {

    @Test
    @DisplayName("Ensure that getCircuits() works correctly")
    public void testGetCircuits() throws UnsupportedGraphFormatException, IOException, InconsistentGraphException {
        GraphIterator graphIterator = new GraphFileFormatAnalyser().analyseFile("src/test/resources/10g3e.txt", 0).getGraphIterator();
        while (graphIterator.hasNext()) {
            Assertions.assertFalse(graphIterator.next().getCircuits().isEmpty());
        }
    }

    @Test
    @DisplayName("Ensure that hasBridge() works correctly")
    public void testHasBridge() throws UnsupportedGraphFormatException, IOException, InconsistentGraphException {
        GraphIterator graphIterator = new GraphFileFormatAnalyser().analyseFile("src/test/resources/bridge_10.txt", 0).getGraphIterator();
        Assertions.assertTrue(graphIterator.next().hasBridge(new DFSBridgeDeterminer()));
    }

    @Test
    @DisplayName("Ensure that isConnected() works correctly")
    public void testIsConnected() throws UnsupportedGraphFormatException, IOException, InconsistentGraphException {
        GraphIterator graphIterator = new GraphFileFormatAnalyser().analyseFile("src/test/resources/disconnected_8.txt", 0).getGraphIterator();
        Assertions.assertFalse(graphIterator.next().isConnected(new DFSConnectedGraphDeterminer()));
    }

    @Test
    @DisplayName("Ensure that isCycleOfTheGraph() works correctly")
    public void testIsCycleOfTheGraph() throws UnsupportedGraphFormatException, IOException, InconsistentGraphException, InconsistentCircuitException {
        Graph K4 = new GraphFileFormatAnalyser().analyseFile("src/test/resources/k4_1.txt", 0).getGraphIterator().next();
        K4.findCycles();
        CircuitIterator circuitIterator = new VerticesFormatCircuitIterator("src/test/resources/K4cycles1.txt", K4);
        Set<Circuit> circuits1 = new HashSet<>(Arrays.asList(circuitIterator.next(), circuitIterator.next(), circuitIterator.next()));
        Set<Circuit> circuits2 = new HashSet<>(List.of(circuitIterator.next()));
        Set<Circuit> circuits3 = new HashSet<>(Arrays.asList(circuitIterator.next(), circuitIterator.next(), circuitIterator.next()));
        Set<Edge> edges1 = new HashSet<>();
        for (Circuit circuit : circuits1) {
            edges1.addAll(circuit.getEdges());
        }

        Set<Edge> edges2 = new HashSet<>();
        for (Circuit circuit : circuits2) {
            edges2.addAll(circuit.getEdges());
        }

        Set<Edge> edges3 = new HashSet<>();
        for (Circuit circuit : circuits3) {
            edges3.addAll(circuit.getEdges());
        }
        Cycle cycle1 = new CubicCycle(edges1, circuits1);
        Cycle cycle2 = new CubicCycle(edges2, circuits2);
        Cycle cycle3 = new CubicCycle(edges3, circuits3);
        Assertions.assertFalse(K4.isCycleOfTheGraph(cycle1));
        Assertions.assertTrue(K4.isCycleOfTheGraph(cycle2));
        Assertions.assertFalse(K4.isCycleOfTheGraph(cycle3));
        cycle2.getEdges().add(new CubicEdge(new CubicVertex(0), new CubicVertex(3)));
        Assertions.assertFalse(K4.isCycleOfTheGraph(cycle2));

        Graph G20 = new GraphFileFormatAnalyser().analyseFile("src/test/resources/16g3e5c.txt", 0).getGraphIterator().next();

        CircuitIterator circuitIterator2 = new VerticesFormatCircuitIterator("src/test/resources/G20cycles.txt", G20);
        Set<Circuit> circuits11 = new HashSet<>(Arrays.asList(circuitIterator2.next(), circuitIterator2.next()));
        Set<Circuit> circuits21 = new HashSet<>(List.of(circuitIterator2.next()));
        circuits21.addAll(circuits11);
        Set<Edge> edges11 = new HashSet<>();
        for (Circuit circuit : circuits11) {
            edges11.addAll(circuit.getEdges());
        }

        Set<Edge> edges21 = new HashSet<>();
        for (Circuit circuit : circuits21) {
            edges21.addAll(circuit.getEdges());
        }

        Cycle cycle11 = new CubicCycle(edges11, circuits11);
        Cycle cycle21 = new CubicCycle(edges21, circuits21);
        Assertions.assertTrue(G20.isCycleOfTheGraph(cycle11));
        Assertions.assertFalse(K4.isCycleOfTheGraph(cycle21));

    }
}
