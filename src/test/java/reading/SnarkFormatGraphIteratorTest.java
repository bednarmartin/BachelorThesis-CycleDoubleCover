package reading;

import algorithm.exceptions.InconsistentGraphException;
import algorithm.graph.Graph;
import algorithm.reading.iterator.GraphIterator;
import algorithm.reading.iterator.SnarkFormatGraphIterator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * Tests for SnarkFormatGraphIterator
 */
public class SnarkFormatGraphIteratorTest {


    @DisplayName("Ensure that reading snarks in s18_c4 file works correctly")
    @Test
    public void tests18_c4() throws IOException, InconsistentGraphException {
        GraphIterator graphIterator = new SnarkFormatGraphIterator("src/test/resources/s18_c4.txt", false,0);
        Assertions.assertEquals(2, graphIterator.getNumberOfGraphs());
        while (graphIterator.hasNext()) {
            Graph graph = graphIterator.next();
            Assertions.assertEquals(18, graph.getNumberOfVertices());
            Assertions.assertTrue(graph.isConsistent());
        }
    }

    @DisplayName("Ensure that reading snarks in s20_c4 file works correctly")
    @Test
    public void tests20_c4() throws IOException, InconsistentGraphException {
        GraphIterator graphIterator = new SnarkFormatGraphIterator("src/test/resources/s20_c4.txt", false,0);
        while (graphIterator.hasNext()) {
            Graph graph = graphIterator.next();
            Assertions.assertEquals(20, graph.getNumberOfVertices());
            Assertions.assertTrue(graph.isConsistent());
        }
    }

    @DisplayName("Ensure that reading snarks in s20_c4 file with ranges works correctly")
    @Test
    public void tests20_c4Ranges() throws IOException, InconsistentGraphException {
        GraphIterator graphIterator = new SnarkFormatGraphIterator("src/test/resources/s20_c4.txt", 3, false, 0);
        int counter = 0;
        while (graphIterator.hasNext()) {
            Graph graph = graphIterator.next();
            Assertions.assertEquals(20, graph.getNumberOfVertices());
            Assertions.assertTrue(graph.isConsistent());
            counter++;
        }
        Assertions.assertEquals(4, counter);

        graphIterator = new SnarkFormatGraphIterator("src/test/resources/s20_c4.txt", 3, 5, false, 0);
        counter = 0;
        while (graphIterator.hasNext()) {
            Graph graph = graphIterator.next();
            Assertions.assertEquals(20, graph.getNumberOfVertices());
            Assertions.assertTrue(graph.isConsistent());
            counter++;
        }
        Assertions.assertEquals(3, counter);
    }


}
