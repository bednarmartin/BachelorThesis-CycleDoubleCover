package graph;

import algorithm.exceptions.InconsistentGraphException;
import algorithm.graph.CubicEdge;
import algorithm.graph.CubicVertex;
import algorithm.graph.Edge;
import algorithm.graph.Vertex;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for CubicEdge class
 */
public class CubicEdgeTest {

    private Vertex vertex1;
    private Vertex vertex2;
    private Vertex vertex3;
    private Vertex vertex4;
    private Edge edge1;
    private Edge edge2;

    @BeforeEach
    public void init() throws InconsistentGraphException {
        vertex1 = new CubicVertex(1);
        vertex2 = new CubicVertex(2);
        vertex3 = new CubicVertex(3);
        vertex4 = new CubicVertex(4);
        vertex1.addNeighbor(vertex2);
        vertex1.addNeighbor(vertex3);
        vertex1.addNeighbor(vertex4);
        vertex3.addNeighbor(vertex4);
        edge1 = new CubicEdge(vertex2, vertex1);
        edge2 = new CubicEdge(vertex4, vertex3);
    }

    @Test
    @DisplayName("Ensure that the first vertex is always smaller when it comes to the number representation")
    public void testFirstVertexSmaller() {
        Assertions.assertTrue(edge1.getFirst().getNumber() < edge1.getSecond().getNumber());
        Assertions.assertTrue(edge2.getFirst().getNumber() < edge2.getSecond().getNumber());

    }

    @Test
    @DisplayName("Ensure that the equals() method works correctly")
    public void testEquals(){
        Assertions.assertEquals(edge1, new CubicEdge(vertex1, vertex2));
        Assertions.assertEquals(edge2, new CubicEdge(vertex3, vertex4));

        Assertions.assertNotEquals(edge2, edge1);
        Assertions.assertNotEquals(edge2, new CubicEdge(vertex1, vertex4));
        Assertions.assertNotEquals(edge1, new CubicEdge(vertex1, vertex4));

    }

    @Test
    @DisplayName("Ensure that the compareTo() method works correctly")
    public void testCompareTo(){
        Assertions.assertEquals(0, edge1.compareTo(new CubicEdge(vertex1, vertex2)));
        Assertions.assertEquals(0, edge2.compareTo(new CubicEdge(vertex3, vertex4)));

        Assertions.assertTrue(edge2.compareTo(edge1) > 0);
        Assertions.assertTrue(edge2.compareTo(new CubicEdge(vertex1, vertex4)) > 0);
        Assertions.assertTrue(edge1.compareTo(new CubicEdge(vertex1, vertex4)) < 0);

    }

}
