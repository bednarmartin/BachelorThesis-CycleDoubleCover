package reading;

import algorithm.exceptions.InconsistentCircuitException;
import algorithm.exceptions.InconsistentGraphException;
import algorithm.exceptions.UnsupportedGraphFormatException;
import algorithm.graph.Circuit;
import algorithm.graph.Graph;
import algorithm.reading.*;
import algorithm.reading.iterator.CircuitIterator;
import algorithm.reading.iterator.VerticesFormatCircuitIterator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * Tests for VerticesFormatCircuitIterator
 */
public class VerticesFormatCircuitIteratorTest {

    @Test
    @DisplayName("Test reading circuits of 4g3e.txt graph")
    public void test4g3e() throws UnsupportedGraphFormatException, IOException, InconsistentGraphException, InconsistentCircuitException {
        Graph graph = new GraphFileFormatAnalyser().analyseFile("src/test/resources/4g3e.txt", 0).getGraphIterator().next();
        CircuitIterator circuitIterator = new VerticesFormatCircuitIterator("src/test/resources/4g3e-circuits.txt", graph);
        Assertions.assertEquals(7, circuitIterator.getNumberOfCircuits());
        int counter = 0;
        while (circuitIterator.hasNext()) {
            Circuit circuit = circuitIterator.next();
            Assertions.assertTrue(graph.getCircuits().contains(circuit));
            counter++;
        }
        Assertions.assertEquals(7, counter);
    }

    @Test
    @DisplayName("Test reading bad circuits of 4g3e.txt graph")
    public void testBad4g3e() throws UnsupportedGraphFormatException, IOException, InconsistentGraphException {
        Graph graph = new GraphFileFormatAnalyser().analyseFile("src/test/resources/4g3e.txt", 0).getGraphIterator().next();
        String[] files = {"4g3e-circuitsBad1.txt", "4g3e-circuitsBad2.txt", "4g3e-circuitsBad3.txt", "4g3e-circuitsBad4.txt", "4g3e-circuitsBad5.txt"};
        for (String file : files) {
            CircuitIterator circuitIterator = new VerticesFormatCircuitIterator("src/test/resources/" + file, graph);
            boolean exception = false;
            try {
                while (circuitIterator.hasNext()) {
                    circuitIterator.next();
                }
            } catch (Exception e) {
                exception = true;
            }
            Assertions.assertTrue(exception);

        }

    }

    @Test
    @DisplayName("Test reading circuits with ranges of 4g3e.txt graph")
    public void testRanges4g3e() throws UnsupportedGraphFormatException, IOException, InconsistentGraphException, InconsistentCircuitException {
        Graph graph = new GraphFileFormatAnalyser().analyseFile("src/test/resources/4g3e.txt", 0).getGraphIterator().next();
        CircuitIterator circuitIterator = new VerticesFormatCircuitIterator("src/test/resources/4g3e-circuits.txt", graph, 1);
        Assertions.assertEquals(7, circuitIterator.getNumberOfCircuits());
        int counter = 0;
        while (circuitIterator.hasNext()) {
            Circuit circuit = circuitIterator.next();
            Assertions.assertTrue(graph.getCircuits().contains(circuit));
            counter++;
        }
        Assertions.assertEquals(7, counter);


        circuitIterator = new VerticesFormatCircuitIterator("src/test/resources/4g3e-circuits.txt", graph, 2);
        Assertions.assertEquals(7, circuitIterator.getNumberOfCircuits());
        counter = 0;
        while (circuitIterator.hasNext()) {
            Circuit circuit = circuitIterator.next();
            Assertions.assertTrue(graph.getCircuits().contains(circuit));
            counter++;
        }
        Assertions.assertEquals(6, counter);


        circuitIterator = new VerticesFormatCircuitIterator("src/test/resources/4g3e-circuits.txt", graph, 2, 4);
        Assertions.assertEquals(7, circuitIterator.getNumberOfCircuits());
        counter = 0;
        while (circuitIterator.hasNext()) {
            Circuit circuit = circuitIterator.next();
            Assertions.assertTrue(graph.getCircuits().contains(circuit));
            counter++;
        }
        Assertions.assertEquals(3, counter);
    }

}
