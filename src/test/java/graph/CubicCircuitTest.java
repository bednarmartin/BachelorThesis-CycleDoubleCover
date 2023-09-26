package graph;

import algorithm.exceptions.InconsistentGraphException;
import algorithm.graph.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Tests for CubicCircuit class
 */
public class CubicCircuitTest {
    private Vertex vertex1;
    private Vertex vertex2;
    private Vertex vertex3;
    private Vertex vertex4;
    private Edge edge1;
    private Edge edge2;
    private Edge edge3;
    private Edge edge4;
    private Edge edge5;
    private Circuit circuit1;
    private Circuit circuit2;

    @BeforeEach
    public void init() throws InconsistentGraphException {
        vertex1 = new CubicVertex(1);
        vertex2 = new CubicVertex(2);
        vertex3 = new CubicVertex(3);
        vertex4 = new CubicVertex(4);

        vertex1.addNeighbor(vertex2);
        vertex2.addNeighbor(vertex3);
        vertex3.addNeighbor(vertex4);
        vertex3.addNeighbor(vertex1);
        vertex4.addNeighbor(vertex1);

        edge1 = new CubicEdge(vertex1, vertex2);
        edge2 = new CubicEdge(vertex2, vertex3);
        edge3 = new CubicEdge(vertex3, vertex4);
        edge4 = new CubicEdge(vertex4, vertex1);
        edge5 = new CubicEdge(vertex1, vertex3);

        circuit1 = new CubicCircuit(new HashSet<>(Arrays.asList(edge1, edge2, edge3, edge4)));
        circuit2 = new CubicCircuit(new HashSet<>(Arrays.asList(edge1, edge2, edge5)));
    }

    @Test
    @DisplayName("Ensure that getVertices() method works correctly")
    public void testGetVertices(){
        Set<Vertex> vertices1 = circuit1.getVertices();
        Set<Vertex> vertices2 = circuit2.getVertices();
        Assertions.assertNotEquals(vertices1, vertices2);
        Assertions.assertTrue(vertices1.contains(vertex1));
        Assertions.assertTrue(vertices1.contains(vertex2));
        Assertions.assertTrue(vertices1.contains(vertex3));
        Assertions.assertTrue(vertices1.contains(vertex4));
        Assertions.assertTrue(vertices1.contains(new CubicVertex(4)));
        Assertions.assertFalse(vertices1.contains(new CubicVertex(6)));

        Assertions.assertTrue(vertices2.contains(vertex1));
        Assertions.assertTrue(vertices2.contains(vertex2));
        Assertions.assertTrue(vertices2.contains(vertex3));

        Assertions.assertFalse(vertices2.contains(vertex4));
    }

    @Test
    @DisplayName("Ensure that getEdges() method works correctly")
    public void testGetVEdges() {
        Set<Edge> edges1 = circuit1.getEdges();
        Set<Edge> edges2 = circuit2.getEdges();

        Assertions.assertNotEquals(edges1, edges2);
        Assertions.assertTrue(edges1.contains(edge1));
        Assertions.assertTrue(edges1.contains(edge2));
        Assertions.assertTrue(edges1.contains(edge3));
        Assertions.assertTrue(edges1.contains(edge4));

        Assertions.assertTrue(edges2.contains(edge1));
        Assertions.assertTrue(edges2.contains(edge2));
        Assertions.assertTrue(edges2.contains(edge5));

        Assertions.assertFalse(edges2.contains(edge3));
        Assertions.assertFalse(edges2.contains(edge4));
    }

    @Test
    @DisplayName("Ensure that the equals() method works correctly")
    public void testEquals() {
        Assertions.assertEquals(circuit1, new CubicCircuit(new HashSet<>(Arrays.asList(edge1, edge2, edge3, edge4))));
        Assertions.assertEquals(circuit2, new CubicCircuit(new HashSet<>(Arrays.asList(edge1, edge2, edge5))));

        Assertions.assertNotEquals(circuit1, circuit2);
        Assertions.assertNotEquals(circuit1, new CubicCircuit(new HashSet<>(Arrays.asList(edge1, edge2))));
        Assertions.assertNotEquals(circuit2, new CubicCircuit(new HashSet<>(Arrays.asList(edge1, edge2, edge3))));

    }

    @Test
    @DisplayName("Ensure that the getCircuit() method works correctly")
    public void testGetCircuit() {
        Assertions.assertEquals(new HashSet<>(List.of(circuit1)), circuit1.getCircuits());
        Assertions.assertEquals(new HashSet<>(List.of(circuit2)), circuit2.getCircuits());

    }

}
