package edu.postech.csed332.homework2;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * An abstract test class for MutableGraph with blackbox test methods
 *
 * @param <V> type of vertices
 * @param <G> type of Graph
 */
@Disabled
public abstract class AbstractMutableGraphTest<V extends Comparable<V>, G extends MutableGraph<V>> {

    G graph;
    V v1, v2, v3, v4, v5, v6, v7, v8;

    abstract boolean checkInv();    // call checkInv of graph

    @Test
    void testAddVertex() {
        assertTrue(graph.addVertex(v1));
        assertTrue(graph.containsVertex(v1));
        assertTrue(checkInv());
    }

    @Test
    void testAddDuplicateVertices() {
        assertTrue(graph.addVertex(v6));
        assertTrue(graph.addVertex(v7));
        assertFalse(graph.addVertex(v6));
        assertTrue(graph.containsVertex(v6));
        assertTrue(graph.containsVertex(v7));
        assertTrue(checkInv());
    }

    @Test
    void testFindReachableVertices() {
        graph.addEdge(v1, v2);
        graph.addEdge(v1, v3);
        graph.addVertex(v4);

        assertEquals(Set.of(v1, v2, v3), graph.findReachableVertices(v1));
        assertEquals(Set.of(v1, v2, v3), graph.findReachableVertices(v2));
        assertEquals(Set.of(v1, v2, v3), graph.findReachableVertices(v3));
        assertEquals(Set.of(v4), graph.findReachableVertices(v4));
        assertTrue(graph.findReachableVertices(v5).isEmpty());
        assertTrue(checkInv());
    }

    // TODO: write black-box test cases for each method of MutableGraph with respect to
    //  the specification, including the methods of Graph that MutableGraph extends.
    @AfterEach
    void tearDown() {
        assertTrue(checkInv());
    }
    @Test
    void testAddEdgeShouldCreateUndirectedEdge() {
        assertTrue(graph.addVertex(v1));
        assertTrue(graph.addVertex(v2));
        assertTrue(graph.addEdge(v1, v2));
        assertTrue(graph.containsEdge(v1, v2));
        assertTrue(graph.containsEdge(v2, v1));
    }
    @Test
    void testAddEdgeWithoutVertices() {
        assertTrue(graph.addEdge(v1, v2));
        assertTrue(graph.containsEdge(v2, v1));
        assertTrue(graph.containsEdge(v1, v2));
    }
    @Test
    void testAddDuplicateEdges() {
        assertTrue(graph.addVertex(v1));
        assertTrue(graph.addVertex(v2));
        assertTrue(graph.addEdge(v1, v2));
        assertFalse(graph.addEdge(v1, v2));
        assertTrue(graph.containsEdge(v1, v2));
    }
    @Test
    void testRemoveEdge() {
        assertTrue(graph.addVertex(v1));
        assertTrue(graph.addVertex(v2));
        assertTrue(graph.addEdge(v1, v2));
        assertTrue(graph.removeEdge(v1, v2));
        assertFalse(graph.containsEdge(v1, v2));
    }
    @Test
    void testRemoveNonExistingEdge() {
        assertTrue(graph.addVertex(v1));
        assertTrue(graph.addVertex(v2));
        assertFalse(graph.removeEdge(v1, v2));
        assertFalse(graph.containsEdge(v1, v2));
    }
    @Test
    void testRemoveVertex() {
        assertTrue(graph.addVertex(v1));
        assertTrue(graph.addVertex(v2));
        assertTrue(graph.addEdge(v1, v2));
        assertTrue(graph.removeVertex(v1));
        assertFalse(graph.containsVertex(v1));
        assertFalse(graph.containsEdge(v1, v2));
        assertFalse(graph.containsEdge(v2, v1));
    }
    @Test
    void testRemoveVertexShouldRemoveRelatedEdges() {
        assertTrue(graph.addVertex(v1));
        assertTrue(graph.addVertex(v2));
        assertTrue(graph.addVertex(v3));
        assertTrue(graph.addEdge(v1, v2));
        assertTrue(graph.addEdge(v1, v3));
        assertTrue(graph.removeVertex(v1));
        assertFalse(graph.containsVertex(v1));
        assertFalse(graph.containsEdge(v1, v2));
        assertFalse(graph.containsEdge(v2, v1));
        assertFalse(graph.containsEdge(v1, v3));
        assertFalse(graph.containsEdge(v3, v1));
    }
    @Test
    void testRemoveNonExistingVertex() {
        assertFalse(graph.removeVertex(v3));
        assertFalse(graph.containsVertex(v3));
    }
    @Test
    void testContainsVertex() {
        assertTrue(graph.addVertex(v1));
        assertTrue(graph.containsVertex(v1));
        assertFalse(graph.containsVertex(v2));
    }
    @Test
    void testContainsNonExistingVertex() {
        assertFalse(graph.containsVertex(v1));
    }
    @Test
    void testContainsEdge() {
        assertTrue(graph.addVertex(v1));
        assertTrue(graph.addVertex(v2));
        assertTrue(graph.addEdge(v1, v2));
        assertTrue(graph.containsEdge(v1, v2));
    }
    @Test
    void testContainsNonExistingEdge() {
        assertTrue(graph.addVertex(v1));
        assertFalse(graph.containsEdge(v1, v2));
        assertFalse(graph.containsEdge(v2, v3));
    }
    @Test
    void testGetNeighborhood() {
        assertTrue(graph.addVertex(v1));
        assertTrue(graph.addVertex(v2));
        assertTrue(graph.addVertex(v3));
        assertTrue(graph.addEdge(v1, v2));
        assertTrue(graph.addEdge(v1, v3));
        assertEquals(Set.of(v2, v3), graph.getNeighborhood(v1));
    }
    @Test
    void testGetNeighborhoodOfNonExistingVertex() {
        assertTrue(graph.addVertex(v1));
        assertTrue(graph.addVertex(v2));
        assertTrue(graph.addVertex(v3));
        assertTrue(graph.addEdge(v1, v2));
        assertTrue(graph.addEdge(v1, v3));
        assertEquals(Set.of(), graph.getNeighborhood(v4));
    }
    @Test
    void testGetVertices() {
        assertEquals(Set.of(), graph.getVertices());
        assertTrue(graph.addVertex(v1));
        assertTrue(graph.addVertex(v2));
        assertTrue(graph.addVertex(v3));
        assertEquals(Set.of(v1, v2, v3), graph.getVertices());
    }
    @Test
    void testGetEdges() {
        assertEquals(Set.of(), graph.getEdges());
        assertTrue(graph.addVertex(v1));
        assertTrue(graph.addVertex(v2));
        assertTrue(graph.addVertex(v3));
        assertTrue(graph.addEdge(v1, v2));
        assertTrue(graph.addEdge(v1, v3));
        assertEquals(Set.of(new Edge<>(v1, v2), new Edge<>(v1, v3), new Edge<>(v2, v1), new Edge<>(v3, v1)), graph.getEdges());
    }
}
