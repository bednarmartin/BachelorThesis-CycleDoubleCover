package reading;

import algorithm.graph.*;
import algorithm.reading.iterator.AdjacentFormatGraphIterator;
import algorithm.reading.iterator.GraphIterator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests for AdjacentFormatGraphIterator
 */
public class AdjacentFormatGraphIteratorTest {

    @Test
    @DisplayName("Test reading all graphs from 4g3e.txt")
    public void test4g3eAll() {
        try {
            GraphIterator graphIterator = new AdjacentFormatGraphIterator("src/test/resources/4g3e.txt", true, 0);

            Assertions.assertEquals(4, graphIterator.getNumberOfVertices());
            Assertions.assertEquals(1, graphIterator.getNumberOfGraphs());

            int counter = 0;
            while (graphIterator.hasNext()) {
                Graph graph = graphIterator.next();
                Assertions.assertEquals(4, graph.getNumberOfVertices());
                Assertions.assertEquals(6, graph.getEdges().size());

                for (Vertex vertex : graph.getVertices()) {
                    Assertions.assertEquals(3, vertex.getNeighbors().size());
                }

                Assertions.assertEquals(
                        """
                                1 2 3
                                0 2 3
                                0 1 3
                                0 1 2
                                """, graph.toString());

                counter++;
            }
            Assertions.assertEquals(1, counter);
        } catch (Exception e) {
            e.printStackTrace();
            Assertions.fail();
        }
    }

    @Test
    @DisplayName("Test reading all graphs from 6g3e.txt")
    public void test6g3eAll() {
        try {
            GraphIterator graphIterator = new AdjacentFormatGraphIterator("src/test/resources/6g3e.txt", true, 0);

            Assertions.assertEquals(6, graphIterator.getNumberOfVertices());
            Assertions.assertEquals(2, graphIterator.getNumberOfGraphs());

            int counter = 0;
            List<Graph> graphs = new ArrayList<>();
            while (graphIterator.hasNext()) {
                Graph graph = graphIterator.next();
                graphs.add(graph);
                Assertions.assertEquals(6, graph.getNumberOfVertices());
                Assertions.assertEquals(9, graph.getEdges().size());

                for (Vertex vertex : graph.getVertices()) {
                    Assertions.assertEquals(3, vertex.getNeighbors().size());
                }

                counter++;
            }
            Assertions.assertEquals(2, counter);

            Assertions.assertEquals(
                    """
                            3 4 5
                            2 3 4
                            1 3 5
                            0 1 2
                            0 1 5
                            0 2 4
                            """, graphs.get(0).toString());

            Assertions.assertEquals(
                    """
                            2 3 4
                            2 3 4
                            0 1 5
                            0 1 5
                            0 1 5
                            2 3 4
                            """, graphs.get(1).toString());

        } catch (Exception e) {
            e.printStackTrace();
            Assertions.fail();
        }
    }

    @Test
    @DisplayName("Test reading a range from start of graphs from 8g3e.txt")
    public void test8g3eStartRange() {
        try {
            GraphIterator graphIterator = new AdjacentFormatGraphIterator("src/test/resources/8g3e.txt", 4, true, 0);

            Assertions.assertEquals(8, graphIterator.getNumberOfVertices());

            int counter = 0;
            List<Graph> graphs = new ArrayList<>();
            while (graphIterator.hasNext()) {
                Graph graph = graphIterator.next();
                graphs.add(graph);
                Assertions.assertEquals(8, graph.getNumberOfVertices());
                Assertions.assertEquals(12, graph.getEdges().size());

                for (Vertex vertex : graph.getVertices()) {
                    Assertions.assertEquals(3, vertex.getNeighbors().size());
                }

                counter++;
            }
            Assertions.assertEquals(2, counter);

            Assertions.assertEquals(
                    """
                            5 6 7
                            2 3 4
                            1 5 6
                            1 5 7
                            1 6 7
                            0 2 3
                            0 2 4
                            0 3 4
                            """, graphs.get(0).toString());

            Assertions.assertEquals(
                    """
                            4 5 7
                            2 3 4
                            1 5 6
                            1 5 7
                            0 1 6
                            0 2 3
                            2 4 7
                            0 3 6
                            """, graphs.get(1).toString());

        } catch (Exception e) {
            e.printStackTrace();
            Assertions.fail();
        }
    }

    @Test
    @DisplayName("Test reading a range from start to the end of graphs from 8g3e.txt")
    public void test8g3eStartEndRange() {
        try {
            GraphIterator graphIterator = new AdjacentFormatGraphIterator("src/test/resources/8g3e.txt", 2, 4, true, 0);

            Assertions.assertEquals(8, graphIterator.getNumberOfVertices());

            int counter = 0;
            List<Graph> graphs = new ArrayList<>();
            while (graphIterator.hasNext()) {
                Graph graph = graphIterator.next();
                graphs.add(graph);
                Assertions.assertEquals(8, graph.getNumberOfVertices());
                Assertions.assertEquals(12, graph.getEdges().size());

                for (Vertex vertex : graph.getVertices()) {
                    Assertions.assertEquals(3, vertex.getNeighbors().size());
                }

                counter++;
            }
            Assertions.assertEquals(3, counter);

            Assertions.assertEquals(
                    """
                            5 6 7
                            2 3 4
                            1 3 5
                            1 2 6
                            1 5 7
                            0 2 4
                            0 3 7
                            0 4 6
                            """, graphs.get(0).toString());

            Assertions.assertEquals(
                    """
                            4 5 6
                            2 3 4
                            1 3 5
                            1 2 6
                            0 1 7
                            0 2 7
                            0 3 7
                            4 5 6
                            """, graphs.get(1).toString());
            Assertions.assertEquals(
                    """
                            5 6 7
                            2 3 4
                            1 5 6
                            1 5 7
                            1 6 7
                            0 2 3
                            0 2 4
                            0 3 4
                            """, graphs.get(2).toString());

        } catch (Exception e) {
            e.printStackTrace();
            Assertions.fail();
        }
    }

    @Test
    @DisplayName("Ensure that reading with the excess works correctly")
    public void testexcess() {

        try {
            GraphIterator graphIterator = new AdjacentFormatGraphIterator("src/test/resources/excess1.txt", true, 7);

            Assertions.assertEquals(4, graphIterator.getNumberOfVertices());
            Assertions.assertEquals(1, graphIterator.getNumberOfGraphs());

            int counter = 0;
            while (graphIterator.hasNext()) {
                Graph graph = graphIterator.next();
                Assertions.assertEquals(4, graph.getNumberOfVertices());
                Assertions.assertEquals(6, graph.getEdges().size());

                for (Vertex vertex : graph.getVertices()) {
                    Assertions.assertEquals(3, vertex.getNeighbors().size());
                }

                Assertions.assertEquals(
                        """
                                8 9 10
                                7 9 10
                                7 8 10
                                7 8 9
                                """, graph.toString());

                counter++;
            }
            Assertions.assertEquals(1, counter);
        } catch (Exception e) {
            e.printStackTrace();
            Assertions.fail();
        }
    }


}
