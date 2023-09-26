package graph;

import algorithm.exceptions.InconsistentGraphException;
import algorithm.exceptions.UnsupportedGraphFormatException;
import algorithm.finders.chord.ChordFinder;
import algorithm.finders.chord.CombinationChordFinder;
import algorithm.finders.circuit.DFSCircuitFinder;
import algorithm.graph.*;
import algorithm.reading.GraphFileFormatAnalyser;
import algorithm.reading.iterator.GraphIterator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

/**
 * Test for CubicCycle
 */
public class CubicCycleTest {

    @Test
    @DisplayName("Ensure that isInduced() method works correctly")
    public void testIsInduced() throws UnsupportedGraphFormatException, IOException, InconsistentGraphException {
        String[] files = {"8g3e.txt", "10g3e.txt", "12g3e.txt"};
        for (String file : files) {
            GraphIterator graphIterator = new GraphFileFormatAnalyser().analyseFile("src/test/resources/" + file, 0).getGraphIterator();
            while (graphIterator.hasNext()) {
                Graph graph = graphIterator.next();
                List<Circuit> circuits = new DFSCircuitFinder().getCircuits(graph);
                List<Cycle> cycles = new ArrayList<>();
                for (Circuit circuit : circuits) {
                    for (Circuit circuit2 : circuits) {
                        if (Collections.disjoint(circuit.getVertices(), circuit2.getVertices())) {
                            Assertions.assertTrue(Collections.disjoint(circuit.getEdges(), circuit2.getEdges()));
                            Set<Edge> edges = new HashSet<>();
                            edges.addAll(circuit.getEdges());
                            edges.addAll(circuit2.getEdges());
                            Set<Circuit> newCircuits = new HashSet<>();
                            newCircuits.addAll(circuit.getCircuits());
                            newCircuits.addAll(circuit2.getCircuits());
                            Cycle newCycle = new CubicCycle(edges, newCircuits);
                            cycles.add(newCycle);
                        }
                    }
                }
                while (true) {
                    List<Cycle> newCycles = new ArrayList<>();
                    for (Cycle cycle : cycles) {
                        for (Circuit circuit : circuits) {
                            if (Collections.disjoint(cycle.getVertices(), circuit.getVertices())) {
                                Assertions.assertTrue(Collections.disjoint(circuit.getEdges(), cycle.getEdges()));
                                Set<Edge> edges = new HashSet<>();
                                edges.addAll(circuit.getEdges());
                                edges.addAll(cycle.getEdges());
                                Set<Circuit> newCircuits = new HashSet<>();
                                newCircuits.addAll(circuit.getCircuits());
                                newCircuits.addAll(cycle.getCircuits());
                                Cycle newCycle = new CubicCycle(edges, newCircuits);
                                if (!cycles.contains(newCycle)) {
                                    newCycles.add(newCycle);
                                }

                            }
                        }
                    }
                    if (newCycles.isEmpty()) {
                        break;
                    } else {
                        cycles.addAll(newCycles);
                    }
                }
                for (Cycle cycle : cycles) {
                    if (cycle.isInduced(graph)) {
                        for (Edge edge : graph.getEdges()) {
                            for (Circuit circuit1 : cycle.getCircuits()) {
                                for (Circuit circuit2 : cycle.getCircuits()) {
                                    if (!circuit1.equals(circuit2)) {
                                        Assertions.assertFalse(circuit1.getVertices().contains(edge.getFirst()) && circuit2.getVertices().contains(edge.getSecond()));
                                        Assertions.assertFalse(circuit2.getVertices().contains(edge.getFirst()) && circuit1.getVertices().contains(edge.getSecond()));
                                    }
                                }
                            }
                        }
                    } else {
                        boolean circuitsAreInduced = true;
                        ChordFinder chordFinder = new CombinationChordFinder(graph);
                        for (Circuit circuit : cycle.getCircuits()) {
                            Set<Edge> chords = chordFinder.getChords(circuit);
                            Assertions.assertEquals(circuit.isInduced(graph), chords.size() == 0);
                            if (chords.size() != 0) {
                                circuitsAreInduced = false;
                            }
                        }

                        if (circuitsAreInduced) {
                            boolean was = false;
                            for (Edge edge : graph.getEdges()) {
                                for (Circuit circuit1 : cycle.getCircuits()) {
                                    for (Circuit circuit2 : cycle.getCircuits()) {
                                        if (!circuit1.equals(circuit2)) {
                                            if (circuit1.getVertices().contains(edge.getFirst()) && circuit2.getVertices().contains(edge.getSecond())) {
                                                was = true;
                                            }
                                            if (circuit2.getVertices().contains(edge.getFirst()) && circuit1.getVertices().contains(edge.getSecond())) {
                                                was = true;
                                            }
                                        }
                                    }
                                }
                            }
                            Assertions.assertTrue(was);
                        }
                    }
                }
            }
        }
    }

}
