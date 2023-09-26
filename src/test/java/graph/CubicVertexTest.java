package graph;

import algorithm.exceptions.InconsistentGraphException;
import algorithm.graph.CubicVertex;
import algorithm.graph.Vertex;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for CubicVertex class
 */
public class CubicVertexTest {

    private Vertex vertex1;
    private Vertex vertex2;
    private Vertex vertex3;
    private Vertex vertex4;

    @BeforeEach
    public void init(){
        vertex1 = new CubicVertex(1);
        vertex2 = new CubicVertex(2);
        vertex3 = new CubicVertex(3);
        vertex4 = new CubicVertex(4);
    }

    @Test
    @DisplayName("Ensure that getNumber() method works correctly")
    public void testGetNumber() {
        Assertions.assertEquals(1, vertex1.getNumber());
        Assertions.assertEquals(2, vertex2.getNumber());
        Assertions.assertEquals(3, vertex3.getNumber());
        Assertions.assertEquals(4, vertex4.getNumber());

    }

    @Test
    @DisplayName("Ensure that getNeighbors() method works correctly")
    public void testGetNeighbors() {
        Assertions.assertTrue(vertex1.getNeighbors().isEmpty());
        Assertions.assertTrue(vertex2.getNeighbors().isEmpty());
        Assertions.assertTrue(vertex3.getNeighbors().isEmpty());
        Assertions.assertTrue(vertex4.getNeighbors().isEmpty());
        try {
            vertex1.addNeighbor(vertex2);
            vertex1.addNeighbor(vertex3);
            vertex1.addNeighbor(vertex4);
        } catch (InconsistentGraphException e) {
            Assertions.fail("Exception shouldn't be thrown");
        }

        Assertions.assertFalse(vertex1.getNeighbors().isEmpty());
        Assertions.assertFalse(vertex2.getNeighbors().isEmpty());
        Assertions.assertFalse(vertex3.getNeighbors().isEmpty());
        Assertions.assertFalse(vertex4.getNeighbors().isEmpty());

        Assertions.assertEquals(3, vertex1.getNeighbors().size());


    }

    @Test
    @DisplayName("Ensure that a cubic vertex cannot have more than 3 neighbors")
    public void testNoMoreThanThreeNeighbors() {
        Assertions.assertTrue(vertex1.getNeighbors().isEmpty());
        try {
            vertex1.addNeighbor(vertex2);
            vertex1.addNeighbor(vertex3);
            vertex1.addNeighbor(vertex4);
            vertex1.addNeighbor(new CubicVertex(5));
            Assertions.fail("Exception should be thrown");
        } catch (InconsistentGraphException ignored) {
        }

    }

    @Test
    @DisplayName("Ensure that a cubic vertex cannot have the same vertices as neighbors")
    public void testNoDuplicateNeighbors() {
        Assertions.assertTrue(vertex1.getNeighbors().isEmpty());
        try {
            vertex1.addNeighbor(vertex2);
            vertex1.addNeighbor(new CubicVertex(2));
            Assertions.fail("Exception should be thrown");
        } catch (InconsistentGraphException ignored) {
        }

    }

    @Test
    @DisplayName("Ensure that a cubic vertex cannot be a neighbors of itself")
    public void testNoLoops() {
        Assertions.assertTrue(vertex1.getNeighbors().isEmpty());
        try {
            vertex1.addNeighbor(new CubicVertex(1));
            Assertions.fail("Exception should be thrown");
        } catch (InconsistentGraphException ignored) {
        }

    }

    @Test
    @DisplayName("Ensure that equals() method works correctly")
    public void testEquals(){
        Assertions.assertNotEquals(vertex1, vertex2);
        Assertions.assertEquals(vertex1, new CubicVertex(1));
    }

    @Test
    @DisplayName("Ensure that compareTo method works correctly")
    public void testCompareTo(){
        Assertions.assertTrue(vertex1.compareTo(vertex2) < 0);
        Assertions.assertTrue(vertex1.compareTo(vertex3) < 0);
        Assertions.assertTrue(vertex2.compareTo(vertex1) > 0);
        Assertions.assertEquals(0, vertex3.compareTo(new CubicVertex(3)));


    }

}
