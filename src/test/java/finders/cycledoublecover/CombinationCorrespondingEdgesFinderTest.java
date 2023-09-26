package finders.cycledoublecover;

import algorithm.exceptions.InconsistentGraphException;
import algorithm.exceptions.UnsupportedGraphFormatException;
import algorithm.finders.correspondingedges.CombinationCorrespondingEdgesFinder;
import algorithm.finders.correspondingedges.CorrespondingEdgesFinder;
import algorithm.graph.*;
import algorithm.graphjoiner.GraphJoiner;
import algorithm.graphjoiner.VerticesGraphJoiner;
import algorithm.reading.GraphFileFormatAnalyser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CombinationCorrespondingEdgesFinderTest {


    @Test
    @DisplayName("Ensure that findCorrespondingEdges works correctly")
    public void testFindCorrespondingEdges() throws UnsupportedGraphFormatException, IOException, InconsistentGraphException {
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

        CorrespondingEdgesFinder correspondingEdgesFinder = new CombinationCorrespondingEdgesFinder();
        Set<Edge> removedEdges1 = new HashSet<>();
        Set<Edge> removedEdges2 = new HashSet<>();

        Set<Edge> addedEdges1 = new HashSet<>();
        Set<Edge> addedEdges2 = new HashSet<>();
        removedEdges1.add(new CubicEdge(new CubicVertex(5), new CubicVertex(7)));
        removedEdges1.add(new CubicEdge(new CubicVertex(0), new CubicVertex(2)));

        removedEdges2.add(new CubicEdge(new CubicVertex(1), new CubicVertex(3)));
        removedEdges2.add(new CubicEdge(new CubicVertex(4), new CubicVertex(6)));

        addedEdges1.add(new CubicEdge(new CubicVertex(2), new CubicVertex(7)));
        addedEdges1.add(new CubicEdge(new CubicVertex(0), new CubicVertex(5)));

        addedEdges2.add(new CubicEdge(new CubicVertex(1), new CubicVertex(4)));
        addedEdges2.add(new CubicEdge(new CubicVertex(3), new CubicVertex(6)));

        Map<Set<Edge>, Set<Edge>> correspondingEdges = correspondingEdgesFinder.findCorrespondingEdges(newGraph, K4_1, K4_2);

        Assertions.assertTrue(correspondingEdges.containsKey(removedEdges1));
        Assertions.assertTrue(correspondingEdges.containsKey(removedEdges2));

        Assertions.assertEquals(addedEdges1,correspondingEdges.get(removedEdges1));
        Assertions.assertEquals(addedEdges2, correspondingEdges.get(removedEdges2));
    }
}
