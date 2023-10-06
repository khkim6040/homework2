package edu.postech.csed332.homework2;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * An abstract test class for MutableTree with blackbox test methods
 *
 * @param <V> type of vertices
 * @param <T> type of Tree
 */
@Disabled
public abstract class AbstractMutableTreeTest<V extends Comparable<V>, T extends MutableTree<V>> {

    T tree;
    V v1, v2, v3, v4, v5, v6, v7, v8;      // vertices which are not a root of tree

    abstract boolean checkInv();    // call checkInv of tree

    @Test
    void testGetDepthRoot() {
        assertEquals(tree.getDepth(tree.getRoot()), 0);
    }

    @Test
    void testGetDepthTwo() {
        tree.addEdge(tree.getRoot(), v1);
        assertEquals(tree.getDepth(v1), 1);
    }

    @Test
    void testGetDepthNoVertex() {
        assertThrows(IllegalArgumentException.class, () -> tree.getDepth(v1));
    }

    // TODO: write black-box test cases for each method of MutableTree with respect to
    //  the specification, including the methods of Tree that MutableTree extends.
    @AfterEach
    void tearDown() {
        assertTrue(checkInv());
    }
    // Test for Graph
    @Test
    void testContainsVertex() {
        assertFalse(tree.containsVertex(v1));
        tree.addEdge(tree.getRoot(), v1);
        assertTrue(tree.containsVertex(v1));
    }
    @Test
    void testContainsEdge() {
        assertFalse(tree.containsEdge(tree.getRoot(), v1));
        tree.addEdge(tree.getRoot(), v1);
        assertTrue(tree.containsEdge(tree.getRoot(), v1));
    }
    @Test
    void testGetNeighborhood() {
        tree.addEdge(tree.getRoot(), v1);
        tree.addEdge(v1, v2);
        tree.addEdge(v1, v3);
        tree.addEdge(v2, v4);
        assertEquals(tree.getNeighborhood(tree.getRoot()), Set.of(v1));
        assertEquals(tree.getNeighborhood(v1), Set.of(tree.getRoot(), v2, v3));
        assertEquals(tree.getNeighborhood(v2), Set.of(v1, v4));
        assertEquals(tree.getNeighborhood(v3), Set.of(v1));
        assertEquals(tree.getNeighborhood(v4), Set.of(v2));
    }
    @Test
    void testGetVertices() {
        assertEquals(tree.getVertices(), Set.of(tree.getRoot()));
        tree.addEdge(tree.getRoot(), v1);
        assertEquals(tree.getVertices(), Set.of(tree.getRoot(), v1));
        tree.addEdge(v1, v2);
        assertEquals(tree.getVertices(), Set.of(tree.getRoot(), v1, v2));
        tree.addEdge(v1, v3);
        assertEquals(tree.getVertices(), Set.of(tree.getRoot(), v1, v2, v3));
        tree.addEdge(v2, v4);
        assertEquals(tree.getVertices(), Set.of(tree.getRoot(), v1, v2, v3, v4));
    }
    @Test
    void testGetEdges() {
        assertEquals(tree.getEdges(), Set.of());
        tree.addEdge(tree.getRoot(), v1);
        assertEquals(tree.getEdges(), Set.of(new Edge<>(tree.getRoot(), v1)));
        tree.addEdge(v1, v2);
        assertEquals(tree.getEdges(), Set.of(new Edge<>(tree.getRoot(), v1), new Edge<>(v1, v2)));
        tree.addEdge(v1, v3);
        assertEquals(tree.getEdges(), Set.of(new Edge<>(tree.getRoot(), v1), new Edge<>(v1, v2), new Edge<>(v1, v3)));
        tree.addEdge(v2, v4);
        assertEquals(tree.getEdges(), Set.of(new Edge<>(tree.getRoot(), v1), new Edge<>(v1, v2), new Edge<>(v1, v3), new Edge<>(v2, v4)));
    }
    @Test
    void testFindReachableVertices() {
        tree.addEdge(tree.getRoot(), v1);
        tree.addEdge(v1, v2);
        tree.addEdge(v1, v3);
        tree.addEdge(v2, v4);
        assertEquals(tree.findReachableVertices(tree.getRoot()), Set.of(tree.getRoot(), v1, v2, v3, v4));
        assertEquals(tree.findReachableVertices(v1), Set.of(tree.getRoot(), v1, v2, v3, v4));
        assertEquals(tree.findReachableVertices(v2), Set.of(tree.getRoot(), v1, v2, v3, v4));
        assertEquals(tree.findReachableVertices(v3), Set.of(tree.getRoot(), v1, v2, v3, v4));
        assertEquals(tree.findReachableVertices(v4), Set.of(tree.getRoot(), v1, v2, v3, v4));
    }
    @Test
    void testToRepr() {
        assertEquals(tree.toRepr(), "()");
        tree.addEdge(tree.getRoot(), v1);
        assertEquals(tree.toRepr(), "(v1)"); // TODO: check if this is correct
    }
    @Test
    void testGetHeight() {
        assertEquals(tree.getHeight(), 0);
        tree.addEdge(tree.getRoot(), v1);
        assertEquals(tree.getHeight(), 1);
        tree.addEdge(v1, v2);
        assertEquals(tree.getHeight(), 2);
        tree.addEdge(v1, v3);
        assertEquals(tree.getHeight(), 2);
        tree.addEdge(v2, v4);
        assertEquals(tree.getHeight(), 3);
    }
    @Test
    void testGetChildren() {
        tree.addEdge(tree.getRoot(), v1);
        tree.addEdge(v1, v2);
        tree.addEdge(v1, v3);
        tree.addEdge(v2, v4);
        assertEquals(tree.getChildren(tree.getRoot()), Set.of(v1));
        assertEquals(tree.getChildren(v1), Set.of(v2, v3));
        assertEquals(tree.getChildren(v2), Set.of(v4));
        assertEquals(tree.getChildren(v3), Set.of());
        assertEquals(tree.getChildren(v4), Set.of());
    }
    @Test
    void testGetParent() {
        tree.addEdge(tree.getRoot(), v1);
        tree.addEdge(v1, v2);
        tree.addEdge(v1, v3);
        tree.addEdge(v2, v4);
        assertEquals(tree.getParent(tree.getRoot()), Optional.empty());
        assertEquals(tree.getParent(v1), Optional.of(tree.getRoot()));
        assertEquals(tree.getParent(v2), Optional.of(v1));
        assertEquals(tree.getParent(v3), Optional.of(v1));
        assertEquals(tree.getParent(v4), Optional.of(v2));
    }
    @Test
    void testAddVertex() {
        assertTrue(tree.addVertex(v1));
        assertTrue(tree.containsVertex(v1));
        assertFalse(tree.addVertex(v1));
        assertTrue(tree.containsVertex(v1));
    }
    @Test
    void removeVertexShouldRemoveVertexAndItsEdge() {
        tree.addEdge(tree.getRoot(), v1);
        assertTrue(tree.removeVertex(v1));
        assertFalse(tree.containsVertex(v1));
        assertFalse(tree.containsEdge(tree.getRoot(), v1));
        assertFalse(tree.containsEdge(v1, tree.getRoot()));
    }
    @Test
    void removeVertexShouldRemoveRelatedVerticesAndRelatedEdges() {
        tree.addEdge(tree.getRoot(), v1);
        tree.addEdge(v1, v2);
        tree.addEdge(v1, v3);
        tree.addEdge(v2, v4);
        assertTrue(tree.removeVertex(v1));
        assertFalse(tree.containsVertex(v1));
        assertFalse(tree.containsEdge(tree.getRoot(), v1));
        assertFalse(tree.containsEdge(v1, tree.getRoot()));
        assertFalse(tree.containsEdge(v1, v2));
        assertFalse(tree.containsEdge(v2, v1));
        assertFalse(tree.containsEdge(v1, v3));
        assertFalse(tree.containsEdge(v3, v1));
        assertFalse(tree.containsEdge(v2, v4));
        assertFalse(tree.containsEdge(v4, v2));
    }
    @Test
    void removeNonExistingVertex() {
        assertFalse(tree.removeVertex(v1));
        assertFalse(tree.containsVertex(v1));
    }
    @Test
    void removeAlreadyRemovedVertex() {
        tree.addEdge(tree.getRoot(), v1);
        assertTrue(tree.removeVertex(v1));
        assertFalse(tree.removeVertex(v1));
        assertFalse(tree.containsVertex(v1));
    }
    @Test
    void testAddEdge() {
        assertTrue(tree.addEdge(tree.getRoot(), v2));
        assertTrue(tree.containsEdge(tree.getRoot(), v2));
    }
    @Test
    void testAddEdgeShouldNotAddEdgeIfSourceIsNotInTree() {
        assertFalse(tree.addEdge(v1, v2));
        assertFalse(tree.containsEdge(v1, v2));
    }
    @Test
    void testAddEdgeShouldNotAddEdgeIfTargetIsAlreadyInTree() {
        tree.addEdge(tree.getRoot(), v1);
        assertFalse(tree.addEdge(tree.getRoot(), v1));
        assertFalse(tree.containsEdge(tree.getRoot(), v1));
    }
    @Test
    void testAddEdgeShouldNotAddEdgeIfBothSourceAndTargetAreAlreadyInTree() {
        tree.addEdge(tree.getRoot(), v1);
        assertTrue(tree.containsEdge(tree.getRoot(), v1));
        assertFalse(tree.addEdge(tree.getRoot(), v1));
    }
    @Test
    void testAddEdgeShouldNotAddEdgeIfSourceIsTarget() {
        assertFalse(tree.addEdge(tree.getRoot(), tree.getRoot()));
        assertFalse(tree.containsEdge(tree.getRoot(), tree.getRoot()));
    }
    @Test
    void testRemoveEdgeShouldRemoveEdgeAndChildVertex() {
        tree.addEdge(tree.getRoot(), v1);
        assertTrue(tree.removeEdge(tree.getRoot(), v1));
        assertFalse(tree.containsEdge(tree.getRoot(), v1));
        assertFalse(tree.containsEdge(v1, tree.getRoot()));
        assertTrue(tree.containsVertex(tree.getRoot()));
        assertFalse(tree.containsVertex(v1));
    }
    @Test
    void testRemoveEdgeShouldRemoveDescendants() {
        tree.addEdge(tree.getRoot(), v1);
        tree.addEdge(v1, v2);
        tree.addEdge(v1, v3);
        tree.addEdge(v2, v4);
        assertTrue(tree.removeEdge(tree.getRoot(), v1));
        assertFalse(tree.containsEdge(tree.getRoot(), v1));
        assertFalse(tree.containsEdge(v1, tree.getRoot()));
        assertFalse(tree.containsEdge(v1, v2));
        assertFalse(tree.containsEdge(v2, v1));
        assertFalse(tree.containsEdge(v1, v3));
        assertFalse(tree.containsEdge(v3, v1));
        assertFalse(tree.containsEdge(v2, v4));
        assertFalse(tree.containsEdge(v4, v2));
        assertTrue(tree.containsVertex(tree.getRoot()));
        assertFalse(tree.containsVertex(v1));
        assertFalse(tree.containsVertex(v2));
        assertFalse(tree.containsVertex(v3));
        assertFalse(tree.containsVertex(v4));
    }
    @Test
    void testRemoveEdgeShouldNotRemoveEdgeIfSourceOrTargetIsNotInTree() {
        tree.addEdge(tree.getRoot(), v1);
        assertFalse(tree.removeEdge(v1, v2));
        assertFalse(tree.removeEdge(v2, v1));
        assertFalse(tree.removeEdge(v2, v3));
    }
    @Test
    void testRemoveEdgeShouldNotRemoveEdgeIfSourceIsNotParentOfTarget() {
        tree.addEdge(tree.getRoot(), v1);
        assertFalse(tree.removeEdge(v1, tree.getRoot()));
    }
    @Test
    void testRemoveEdgeShouldNotRemoveEdgeIfSourceIsTarget() {
        assertFalse(tree.removeEdge(tree.getRoot(), tree.getRoot()));
    }
}
