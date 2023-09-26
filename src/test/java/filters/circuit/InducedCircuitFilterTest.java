package filters.circuit;

import algorithm.exceptions.InconsistentGraphException;
import algorithm.exceptions.UnsupportedGraphFormatException;
import algorithm.filters.circuit.CircuitFilter;
import algorithm.filters.circuit.InducedCircuitFilter;
import algorithm.filters.circuit.LengthCircuitFilter;
import algorithm.finders.chord.CombinationChordFinder;
import algorithm.finders.circuit.DFSCircuitFinder;
import algorithm.graph.Circuit;
import algorithm.graph.Graph;
import algorithm.reading.GraphFileFormatAnalyser;
import algorithm.reading.iterator.GraphIterator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Tests for InducedCircuitFilter
 */
public class InducedCircuitFilterTest {
    @Test
    @DisplayName("Ensure that InducedCircuitFilter works correctly")
    public void testAll() throws UnsupportedGraphFormatException, IOException, InconsistentGraphException {
        String[] files = {"8g3e.txt", "10g3e.txt", "12g3e.txt"};
        for (String file : files) {
            GraphIterator graphIterator = new GraphFileFormatAnalyser().analyseFile("src/test/resources/" + file, 0).getGraphIterator();
            while (graphIterator.hasNext()) {
                Graph graph = graphIterator.next();
                List<Circuit> circuits = new DFSCircuitFinder().getCircuits(graph);
                CircuitFilter circuitFilter = new InducedCircuitFilter(new CombinationChordFinder(graph));
                List<Circuit> filtered = circuitFilter.getFilteredCircuits(circuits);
                for (Circuit filteredCircuit : filtered) {
                    Assertions.assertFalse(filteredCircuit.getChords(new CombinationChordFinder(graph)).size() > 0);
                }
            }
        }
    }

    @Test
    @DisplayName("Ensure that InducedCircuitFilter and LengthCircuitFilter works correctly")
    public void testAllTogether() throws UnsupportedGraphFormatException, IOException, InconsistentGraphException {
        String[] files = {"8g3e.txt", "10g3e.txt", "12g3e.txt"};
        for (String file : files) {
            GraphIterator graphIterator = new GraphFileFormatAnalyser().analyseFile("src/test/resources/" + file, 0).getGraphIterator();
            while (graphIterator.hasNext()) {
                Graph graph = graphIterator.next();
                List<Circuit> circuits = new DFSCircuitFinder().getCircuits(graph);
                CircuitFilter circuitFilter = new InducedCircuitFilter(new CombinationChordFinder(graph));
                circuitFilter = new LengthCircuitFilter(circuitFilter, 4);
                List<Circuit> filtered = circuitFilter.getFilteredCircuits(circuits);
                for (Circuit filteredCircuit : filtered) {
                    Assertions.assertFalse(filteredCircuit.getChords(new CombinationChordFinder(graph)).size() > 0 && filteredCircuit.getEdges().size() < 3);
                }
            }
        }
    }

    @Test
    @DisplayName("Ensure that InducedCircuitFilter returns all induced circuits")
    public void testcircuits() throws UnsupportedGraphFormatException, IOException, InconsistentGraphException {
        List<Integer> numberOfCircuits = new ArrayList<>(List.of(
                59,
                57,
                54,
                56,
                57,
                55,
                56,
                55,
                58,
                60,
                56,
                52,
                57,
                60,
                57,
                57,
                57,
                62,
                56,
                59,
                56,
                55,
                58,
                62,
                58,
                60,
                65,
                61,
                60,
                59,
                60,
                61,
                60,
                60,
                56,
                56,
                62,
                62,
                60,
                60,
                60,
                60,
                61,
                58,
                60,
                60,
                60,
                59,
                58));
        GraphIterator graphIterator = new GraphFileFormatAnalyser().analyseFile("src/test/resources/16g3e5c.txt", 0).getGraphIterator();
        while (graphIterator.hasNext()) {
            Graph graph = graphIterator.next();
            List<Circuit> circuits = new DFSCircuitFinder().getCircuits(graph);
            CircuitFilter circuitFilter = new InducedCircuitFilter(new CombinationChordFinder(graph));
            List<Circuit> filtered = circuitFilter.getFilteredCircuits(circuits);
            Assertions.assertEquals(numberOfCircuits.get(graphIterator.actualIndex() - 1), filtered.size());
        }

    }
}
