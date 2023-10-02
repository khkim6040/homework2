package edu.postech.csed332.homework2;

import org.jetbrains.annotations.NotNull;

/**
 * A tree with operations for adding and removing vertices and edges. Note that this
 * interface is exactly the same as MutableGraph, but has different contracts.
 *
 * @param <N> type of vertices, which must be immutable and comparable
 */
public interface MutableTree<N extends Comparable<N>> extends Tree<N>, MutableGraph<N> {

    /**
     * Add a vertex to the tree, and returns {@code true} if the tree is changed. This
     * method always returns {@code false}; otherwise, if a new vertex is added, the tree
     * will contain an isolated vertex not reachable from the root.
     *
     * @param vertex a vertex to add
     * @return {@code true} if the tree is modified, {@code false} otherwise
     */
    @Override
    default boolean addVertex(@NotNull N vertex) {
        return false;
    }

    /**
     * Remove a given vertex, and all vertices that are descendants of the given
     * vertex from this tree. This method returns {@code true} if the tree is changed.
     * The root of the tree cannot be removed. For example, consider a tree with vertices
     * {v1, v2, v3, v4} and edges {(v1,v2), (v1,v3), (v2,v1), (v2,v4), (v3,v1), (v4,v2)},
     * where v1 is the root. Then, if the vertex v2 is removed, then v2, v4, and all edges
     * involving v2 or v4 are removed.
     *
     * @param vertex a vertex to remove
     * @return {@code true} if the tree is modified, {@code false} otherwise
     * @throws IllegalArgumentException if the vertex is the root of the tree
     */
    @Override
    boolean removeVertex(@NotNull N vertex);

    /**
     * Add an edge from source to target to the tree, and returns {@code true} if the
     * tree is changed, provided source is already in the tree and target is not in the
     * tree. Otherwise, the tree is not changed. Because a tree is an undirected graph,
     * if an edge (v1,v2) is added, then the tree should contain both (v1,v2) and (v2,v1).
     *
     * @param source a parent vertex
     * @param target a child vertex
     * @return {@code true} if the graph is modified, {@code false} otherwise
     */
    @Override
    boolean addEdge(@NotNull N source, @NotNull N target);

    /**
     * Remove an (undirected) edge from this graph, causing the child vertex and all its
     * descendants to be removed. This method returns {@code true} if the tree is changed.
     * For example, consider a tree with vertices {v1, v2, v3, v4} and edges {(v1,v2),
     * (v1,v3), (v2,v1), (v2,v4), (v3,v1), (v4,v2)}, where v1 is the root. Then, if the
     * edge (v1,v2) is removed, v2, v4, and all edges involving v2 or v4 are removed.
     *
     * @param source a parent vertex
     * @param target a child vertex
     * @return {@code true} if the graph is modified, {@code false} otherwise
     */
    @Override
    boolean removeEdge(@NotNull N source, @NotNull N target);
}
