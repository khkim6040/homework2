package edu.postech.csed332.homework2;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

/**
 * An implementation of Graph with an adjacency list representation.
 * NOTE: you should NOT add more member variables to this class.
 *
 * @param <N> type of vertices, which must be immutable and comparable
 */
public class AdjacencyListGraph<N extends Comparable<N>> implements MutableGraph<N> {

    /**
     * A map from vertices to the sets of their adjacent vertices. For example, a graph
     * with vertices {v1, v2, v3, v4} and edges {(v1,v2), (v1,v3), (v2,v1), (v3,v1)} is
     * represented by the map {v1 |-> {v2, v3}, v2 |-> {v1}, v3 |-> {v1}, v4 |-> {}}.
     */
    private final @NotNull SortedMap<N, SortedSet<N>> adjMap;

    /**
     * Creates an empty graph
     */
    public AdjacencyListGraph() {
        adjMap = new TreeMap<>();
    }

    @Override
    public boolean containsVertex(@NotNull N vertex) {
        // TODO: implement this
        return getVertices().contains(vertex);
    }

    @Override
    public boolean addVertex(@NotNull N vertex) {
        // TODO: implement this
        if (containsVertex(vertex)) {
            return false;
        }
        adjMap.put(vertex, new TreeSet<>());
        return true;
    }

    @Override
    public boolean removeVertex(@NotNull N vertex) {
        // TODO: implement this
        if (!containsVertex(vertex)) {
            return false;
        }
        adjMap.remove(vertex);
        adjMap.values().forEach(set -> set.remove(vertex));
        return true;
    }

    @Override
    public boolean containsEdge(@NotNull N source, @NotNull N target) {
        // TODO: implement this
        return getEdges().contains(new Edge<>(source, target));
    }

    @Override
    public boolean addEdge(@NotNull N source, @NotNull N target) {
        // TODO: implement this
        if(getEdges().contains(new Edge<>(source, target))) {
            return false;
        }
        // Add source and target if they are not in the graph
        if (!containsVertex(source)) {
            addVertex(source);
        }
        if (!containsVertex(target)) {
            addVertex(target);
        }
        adjMap.get(source).add(target);
        adjMap.get(target).add(source);
        return true;
    }

    @Override
    public boolean removeEdge(@NotNull N source, @NotNull N target) {
        // TODO: implement this
        if (!containsEdge(source, target)) {
            return false;
        }
        adjMap.get(source).remove(target);
        adjMap.get(target).remove(source);
        return true;
    }

    @Override
    public @NotNull Set<N> getNeighborhood(@NotNull N vertex) {
        // TODO: implement this
        if (!containsVertex(vertex)) {
            return Collections.emptySet();
        }
        return Collections.unmodifiableSet(adjMap.get(vertex));
    }

    @Override
    public @NotNull Set<N> getVertices() {
        return Collections.unmodifiableSet(adjMap.keySet());
    }

    @Override
    public @NotNull Set<Edge<N>> getEdges() {
        return adjMap.entrySet().stream()
                .flatMap(entry -> entry.getValue().stream().map(n -> new Edge<>(entry.getKey(), n)))
                .collect(Collectors.toUnmodifiableSet());
    }

    /**
     * Checks if all class invariants hold for this object.
     *
     * @return true if the representation of this graph is valid
     */
    boolean checkInv() {
        // TODO: implement this
        Set<N> vertices = getVertices();
        for(Edge<N> edge : getEdges()) {
            N source = edge.source();
            N target = edge.target();
            // for each edge, source and target should be in both V_this, E_this
            if(!vertices.contains(source) || !vertices.contains(target)) {
                return false;
            }
            if (!getNeighborhood(source).contains(target)) {
                return false;
            }
            if (!getNeighborhood(target).contains(source)) {
                return false;
            }
        }
        // self-loop check
        for(N vertex : vertices) {
            if(getNeighborhood(vertex).contains(vertex)) {
                return false;
            }
        }
        return true;
    }
}
