package edu.postech.csed332.homework2;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

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
}
