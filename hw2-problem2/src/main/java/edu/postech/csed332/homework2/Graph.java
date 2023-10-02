package edu.postech.csed332.homework2;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * An undirected graph consisting of a set of vertices and edges. Vertices are given by
 * any immutable type N. Edges have source and target vertices (that can be identical).
 * Edges are undirected, i.e., if (v1,v2) is in E, then (v2,v1) is also in E. Self-loops
 * are not allowed, i.e., if v1 = v2, then (v1,v2) is not in E.
 *
 * @param <N> type of vertices, which must be immutable and comparable
 */
public interface Graph<N extends Comparable<N>> {

    /**
     * Return true if this graph contains a given vertex. For example, consider a graph
     * with vertices {v1, v2, v3, v4} and edges {(v1,v2), (v1,v3), (v2,v1), (v3,v1)}.
     * Then, containsVertex(v1) = true and containsVertex(v5) = false.
     *
     * @param vertex a vertex
     * @return {@code true} if the graph contains vertex
     */
    boolean containsVertex(@NotNull N vertex);

    /**
     * Return true if this graph contains an edge from source to target. Consider a graph
     * with vertices {v1, v2, v3, v4} and edges {(v1,v2), (v1,v3), (v2,v1), (v3,v1)}.
     * Then, containsEdge(v1,v2) = true and containsEdge(v1,v4) = false.
     *
     * @param source a source vertex
     * @param target a target vertex
     * @return true if source and target is connected by an edge
     */
    boolean containsEdge(@NotNull N source, @NotNull N target);

    /**
     * Return the set of adjacent vertices of a given vertex. If the vertex is not in
     * the graph, returns the empty set. For example, consider a graph with vertices
     * {v1, v2, v3, v4} and edges {(v1,v2), (v1,v3), (v2,v1), (v3,v1)}. Then,
     * getAdjacentVertices(v1) = {v2, v3}, getAdjacentVertices(v2) = {v1},
     * getAdjacentVertices(v3) = {v1}, and getAdjacentVertices(v4) = {}.
     *
     * @param vertex a vertex
     * @return the set of adjacent vertices of vertex, immutable
     */
    @NotNull Set<N> getNeighborhood(@NotNull N vertex);

    /**
     * Returns all the vertices in this graph. The set of vertices is immutable.
     *
     * @return the set of vertices in the graph, immutable
     */
    @NotNull Set<N> getVertices();

    /**
     * Returns all the edges in this graph. The set of edges is immutable.
     *
     * @return the set of edges in the graph, immutable
     */
    @NotNull Set<Edge<N>> getEdges();

    /**
     * Returns all the vertices that are reachable from a given vertex in this graph,
     * based on a breadth-first search strategy. For example, consider a graph with
     * vertices {v1, v2, v3, v4} and edges {(v1,v2), (v1,v3), (v2,v1), (v3,v1)}. Then,
     * findReachableVertices(v1) = {v1, v2, v3}, findReachableVertices(v4) = {v4},
     * and findReachableVertices(v5) = {}.
     *
     * @param vertex a vertex
     * @return the set of reachable vertices from {@code vertex}, immutable
     */
    default @NotNull Set<N> findReachableVertices(@NotNull N vertex) {
        Set<N> seen = new HashSet<>();
        Set<N> frontier = new HashSet<>();

        if (containsVertex(vertex))
            frontier.add(vertex);

        while (!seen.containsAll(frontier)) {
            seen.addAll(frontier);
            frontier = frontier.stream()
                    .flatMap(n -> getNeighborhood(n).stream())
                    .filter(n -> !seen.contains(n))
                    .collect(Collectors.toSet());
        }
        return Collections.unmodifiableSet(seen);
    }

    /**
     * Provides a human-readable string representation for the abstract value of the graph
     *
     * @return a string representation
     */
    default String toRepr() {
        return String.format("[vertex: {%s}, edge: {%s}]",
                getVertices().stream().map(N::toString).collect(Collectors.joining(", ")),
                getEdges().stream().map(Edge::toString).collect(Collectors.joining(", ")));
    }
}
