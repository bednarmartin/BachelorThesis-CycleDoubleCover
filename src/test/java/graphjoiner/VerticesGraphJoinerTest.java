package graphjoiner;

import algorithm.exceptions.InconsistentGraphException;
import algorithm.exceptions.UnsupportedGraphFormatException;
import algorithm.graph.CubicVertex;
import algorithm.graph.Graph;
import algorithm.graph.Vertex;
import algorithm.graphjoiner.GraphJoiner;
import algorithm.graphjoiner.VerticesGraphJoiner;
import algorithm.reading.GraphFileFormatAnalyser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class VerticesGraphJoinerTest {
    @Test
    @DisplayName("Ensure that the VerticesGraphJoiner works correctly")
    public void testgraphjoiner() {
        try {
            int excess = 4;
            Graph K4_1 = new GraphFileFormatAnalyser().analyseFile("src/test/resources/k4_1.txt", 0).getGraphIterator().next();
            Graph K4_2 = new GraphFileFormatAnalyser().analyseFile("src/test/resources/k4_2.txt", excess).getGraphIterator().next();
            Map<Vertex, Vertex> toConnectVertices = new HashMap<>();
            toConnectVertices.put(K4_1.getVertices().get(2), K4_2.getVertices().get(7 - excess));
            toConnectVertices.put(K4_1.getVertices().get(1), K4_2.getVertices().get(0));
            toConnectVertices.put(K4_1.getVertices().get(3), K4_2.getVertices().get(6 - excess));
            toConnectVertices.put(K4_1.getVertices().get(0), K4_2.getVertices().get(5 - excess));

            Map<Vertex, Vertex> toDisconnectVertices = new HashMap<>();
            toDisconnectVertices.put(K4_2.getVertices().get(7 - excess), K4_2.getVertices().get(5 - excess));
            toDisconnectVertices.put(K4_2.getVertices().get(0), K4_2.getVertices().get(6 - excess));
            toDisconnectVertices.put(K4_1.getVertices().get(3), K4_1.getVertices().get(1));
            toDisconnectVertices.put(K4_1.getVertices().get(0), K4_1.getVertices().get(2));

            GraphJoiner graphJoiner = new VerticesGraphJoiner(toConnectVertices, toDisconnectVertices);
            Graph newGraph = graphJoiner.joinGraphs(K4_1, K4_2);
            Assertions.assertEquals("""
                    0 3
                    0 1
                    0 5
                    1 2
                    1 4
                    2 3
                    2 7
                    3 6
                    4 7
                    4 5
                    5 6
                    6 7""", newGraph.getRepresentationForVisualization());

            Assertions.assertEquals(newGraph.getVertices(),Arrays.asList(new CubicVertex(0), new CubicVertex(1), new CubicVertex(2), new CubicVertex(3), new CubicVertex(4), new CubicVertex(5), new CubicVertex(6), new CubicVertex(7)));

        } catch (UnsupportedGraphFormatException | IOException | InconsistentGraphException e) {
            e.printStackTrace();
        }
    }
}
