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
import java.util.List;

/**
 * Tests for LengthCircuitFilter
 */
public class LengthCircuitFilterTest {

    @Test
    @DisplayName("Ensure that LengthCircuitFilter works correctly")
    public void testAll() throws UnsupportedGraphFormatException, IOException, InconsistentGraphException {
        String[] files = {"8g3e.txt", "10g3e.txt", "12g3e.txt"};
        for (String file : files) {
            GraphIterator graphIterator = new GraphFileFormatAnalyser().analyseFile("src/test/resources/" + file, 0).getGraphIterator();
            while (graphIterator.hasNext()) {
                Graph graph = graphIterator.next();
                List<Circuit> circuits = new DFSCircuitFinder().getCircuits(graph);
                CircuitFilter circuitFilter = new LengthCircuitFilter(5);
                List<Circuit> filtered = circuitFilter.getFilteredCircuits(circuits);
                for (Circuit filteredCircuit : filtered) {
                    Assertions.assertFalse(filteredCircuit.getEdges().size() < 5);
                }
            }
        }
    }

    @Test
    @DisplayName("Ensure that LengthCircuitFilter and InducedCircuitFilter works correctly")
    public void testAllTogether() throws UnsupportedGraphFormatException, IOException, InconsistentGraphException {
        String[] files = {"8g3e.txt", "10g3e.txt", "12g3e.txt"};
        for (String file : files) {
            GraphIterator graphIterator = new GraphFileFormatAnalyser().analyseFile("src/test/resources/" + file, 0).getGraphIterator();
            while (graphIterator.hasNext()) {
                Graph graph = graphIterator.next();
                List<Circuit> circuits = new DFSCircuitFinder().getCircuits(graph);
                CircuitFilter circuitFilter = new LengthCircuitFilter(5);
                circuitFilter = new InducedCircuitFilter(circuitFilter, new CombinationChordFinder(graph));
                List<Circuit> filtered = circuitFilter.getFilteredCircuits(circuits);
                for (Circuit filteredCircuit : filtered) {
                    Assertions.assertFalse(filteredCircuit.getChords(new CombinationChordFinder(graph)).size() > 0 && filteredCircuit.getEdges().size() < 4);
                }
            }
        }
    }
}
