package edu.postech.csed332.homework2;

import org.jetbrains.annotations.NotNull;

/**
 * An undirected graph with operations for adding and removing vertices and edges. Note
 * that vertices of MutableGraph are still immutable, but the graph itself is mutable
 * by adding or removing vertices and edges.
 *
 * @param <N> type of vertices, which must be immutable and comparable
 */
public interface MutableGraph<N extends Comparable<N>> extends Graph<N> {

    /**
     * Add a given vertex to this graph, and returns {@code true} if the graph is
     * changed. If the graph already contains the vertex, the graph is not changed and
     * this method returns {@code false}.
     *
     * @param vertex a vertex to add
     * @return {@code true} if the graph is modified, {@code false} otherwise
     */
    boolean addVertex(@NotNull N vertex);

    /**
     * Remove a vertex from this graph, together with all edges that involve the vertex,
     * and returns {@code true} if the graph is changed. If the graph does not contain the
     * vertex, the graph is not changed and this method returns {@code false}.
     *
     * @param vertex a vertex to remove
     * @return {@code true} if the graph is modified, {@code false} otherwise
     */
    boolean removeVertex(@NotNull N vertex);

    /**
     * Add an (undirected) edge to this graph, and returns {@code true} if the graph is
     * changed. If source and/or target vertices are not in the graph, missing vertices
     * are added to the graph. Because an edge is undirected, if an edge (v1,v2) is added,
     * then the graph should contain both (v1,v2) and (v2,v1). If the graph already contains
     * the edge, the graph is not changed and this method returns {@code false}.
     *
     * @param source a source vertex
     * @param target a target vertex
     * @return {@code true} if the graph is modified, {@code false} otherwise
     */
    boolean addEdge(@NotNull N source, @NotNull N target);

    /**
     * Remove an (undirected) edge from this graph, and returns {@code true} if the graph is
     * changed. Because an edge is undirected, if an edge (v1,v2) is removed, then the graph
     * should not contain both (v1,v2) and (v2,v1) after the removal. If the graph does not
     * contain the edge, the graph is not changed and this method returns {@code false}.
     *
     * @param source a source vertex
     * @param target a target vertex
     * @return {@code true} if the graph is modified, {@code false} otherwise
     */
    boolean removeEdge(@NotNull N source, @NotNull N target);
}
