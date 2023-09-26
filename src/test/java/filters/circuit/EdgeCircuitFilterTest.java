package filters.circuit;

import algorithm.exceptions.InconsistentGraphException;
import algorithm.exceptions.UnsupportedGraphFormatException;
import algorithm.filters.circuit.CircuitFilter;
import algorithm.filters.circuit.EdgeCircuitFilter;
import algorithm.finders.circuit.DFSCircuitFinder;
import algorithm.graph.*;
import algorithm.reading.GraphFileFormatAnalyser;
import algorithm.reading.iterator.GraphIterator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Tests for EdgeCircuitFilter
 */
public class EdgeCircuitFilterTest {

    @Test
    @DisplayName("Ensure that EdgeCircuitFilter works correctly")
    public void testTheMethod() throws UnsupportedGraphFormatException, IOException, InconsistentGraphException {
        GraphIterator graphIterator = new GraphFileFormatAnalyser().analyseFile("src/test/resources/J3.txt", 16).getGraphIterator();
        while (graphIterator.hasNext()) {
            Graph graph = graphIterator.next();
            List<Circuit> circuits = new DFSCircuitFinder().getCircuits(graph);
            Set<Edge> goodEdges = new HashSet<>();
            goodEdges.add(new CubicEdge(new CubicVertex(18), new CubicVertex(17)));
            goodEdges.add(new CubicEdge(new CubicVertex(26), new CubicVertex(25)));
            Set<Edge> badEdges = new HashSet<>();
            badEdges.add(new CubicEdge(new CubicVertex(27), new CubicVertex(24)));
            CircuitFilter circuitFilter = new EdgeCircuitFilter(goodEdges, badEdges);
            List<Circuit> filtered = circuitFilter.getFilteredCircuits(circuits);
            for (Circuit filteredCircuit : filtered) {
                Assertions.assertTrue(filteredCircuit.getEdges().contains(new CubicEdge(new CubicVertex(18), new CubicVertex(17))) && filteredCircuit.getEdges().contains(new CubicEdge(new CubicVertex(26), new CubicVertex(25))));
                Assertions.assertFalse(filteredCircuit.getEdges().contains(new CubicEdge(new CubicVertex(27), new CubicVertex(24))));
            }
            Assertions.assertEquals(10, filtered.size());
        }
    }

}
