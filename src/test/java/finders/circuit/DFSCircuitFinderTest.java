package finders.circuit;

import algorithm.finders.circuit.CircuitFinder;
import algorithm.finders.circuit.DFSCircuitFinder;
import algorithm.graph.Circuit;
import algorithm.graph.Graph;
import algorithm.reading.iterator.AdjacentFormatGraphIterator;
import algorithm.reading.iterator.GraphIterator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * Tests for DFSCircuitFinder
 */
public class DFSCircuitFinderTest {

    @Test
    @DisplayName("Ensure that DFSCircuitFinder works correctly")
    public void testDFSCircuitFinder() {
        try {
            GraphIterator graphIterator = new AdjacentFormatGraphIterator("src/test/resources/4g3e.txt", true, 0);
            CircuitFinder dfsCircuitFinder = new DFSCircuitFinder();
            while (graphIterator.hasNext()) {
                Graph graph = graphIterator.next();
                List<Circuit> circuits = dfsCircuitFinder.getCircuits(graph);
                Assertions.assertEquals(7, circuits.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assertions.fail();
        }

    }
}
