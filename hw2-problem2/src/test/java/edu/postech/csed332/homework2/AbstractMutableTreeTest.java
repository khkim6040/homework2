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
        assertEquals(Set.of(v1), tree.getNeighborhood(tree.getRoot()));
        assertEquals(Set.of(tree.getRoot(), v2, v3), tree.getNeighborhood(v1));
        assertEquals(Set.of(v1, v4), tree.getNeighborhood(v2));
        assertEquals(Set.of(v1), tree.getNeighborhood(v3));
        assertEquals(Set.of(v2), tree.getNeighborhood(v4));
    }
    @Test
    void testGetVertices() {
        assertEquals(Set.of(tree.getRoot()), tree.getVertices());
        tree.addEdge(tree.getRoot(), v1);
        assertEquals(Set.of(tree.getRoot(), v1), tree.getVertices());
        tree.addEdge(v1, v2);
        assertEquals(Set.of(tree.getRoot(), v1, v2), tree.getVertices());
        tree.addEdge(v1, v3);
        assertEquals(Set.of(tree.getRoot(), v1, v2, v3), tree.getVertices());
        tree.addEdge(v2, v4);
        assertEquals(Set.of(tree.getRoot(), v1, v2, v3, v4), tree.getVertices());
    }
    @Test
    void testGetEdges() {
        assertEquals(Set.of(), tree.getEdges());
        tree.addEdge(tree.getRoot(), v1);
        assertEquals(Set.of(new Edge<>(tree.getRoot(), v1), new Edge<>(v1, tree.getRoot())), tree.getEdges());
        tree.addEdge(v1, v2);
        assertEquals(Set.of(new Edge<>(tree.getRoot(), v1), new Edge<>(v1, v2), new Edge<>(v2, v1), new Edge<>(v1, tree.getRoot())), tree.getEdges());
    }
    @Test
    void testFindReachableVertices() {
        tree.addEdge(tree.getRoot(), v1);
        tree.addEdge(v1, v2);
        tree.addEdge(v1, v3);
        tree.addEdge(v2, v4);
        assertEquals(Set.of(tree.getRoot(), v1, v2, v3, v4), tree.findReachableVertices(tree.getRoot()));
        assertEquals(Set.of(tree.getRoot(), v1, v2, v3, v4), tree.findReachableVertices(v1));
        assertEquals(Set.of(tree.getRoot(), v1, v2, v3, v4), tree.findReachableVertices(v2));
        assertEquals(Set.of(tree.getRoot(), v1, v2, v3, v4), tree.findReachableVertices(v3));
        assertEquals(Set.of(tree.getRoot(), v1, v2, v3, v4), tree.findReachableVertices(v4));
    }
    // Test for Tree
    @Test
    void testGetHeight() {
        assertEquals(0, tree.getHeight());
        tree.addEdge(tree.getRoot(), v1);
        assertEquals(1, tree.getHeight());
        tree.addEdge(v1, v2);
        assertEquals(2, tree.getHeight());
        tree.addEdge(v1, v3);
        assertEquals(2, tree.getHeight());
        tree.addEdge(v2, v4);
        assertEquals(3, tree.getHeight());
    }
    @Test
    void testGetChildren() {
        tree.addEdge(tree.getRoot(), v1);
        tree.addEdge(v1, v2);
        tree.addEdge(v1, v3);
        tree.addEdge(v2, v4);
        assertEquals(Set.of(v1), tree.getChildren(tree.getRoot()));
        assertEquals(Set.of(v2, v3), tree.getChildren(v1));
        assertEquals(Set.of(v4), tree.getChildren(v2));
        assertEquals(Set.of(), tree.getChildren(v3));
        assertEquals(Set.of(), tree.getChildren(v4));
    }
    @Test
    void testGetParent() {
        tree.addEdge(tree.getRoot(), v1);
        tree.addEdge(v1, v2);
        tree.addEdge(v1, v3);
        tree.addEdge(v2, v4);
        assertEquals(Optional.empty(), tree.getParent(tree.getRoot()));
        assertEquals(Optional.of(tree.getRoot()), tree.getParent(v1));
        assertEquals(Optional.of(v1), tree.getParent(v2));
        assertEquals(Optional.of(v1), tree.getParent(v3));
        assertEquals(Optional.of(v2), tree.getParent(v4));
    }
    // Test for mutableTree
    @Test
    void testAddVertex() {
        assertFalse(tree.addVertex(v1));
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
    void removeRoot() {
        assertThrows(IllegalArgumentException.class, () -> tree.removeVertex(tree.getRoot()));
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
        assertTrue(tree.containsEdge(tree.getRoot(), v1));
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
