package edu.postech.csed332.homework2;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

/**
 * An implementation of Tree that delegates to a given instance of Graph. This class
 * is a wrapper of a MutableGraph instance that enforces the Tree invariant.
 * NOTE: you should NOT add more member variables to this class.
 *
 * @param <N> type of vertices, which must be immutable and comparable
 */
public class DelegateTree<N extends Comparable<N>> implements MutableTree<N> {

    /**
     * A root vertex of this tree.
     */
    private final @NotNull N root;

    /**
     * The underlying graph of this tree
     */
    private final @NotNull MutableGraph<N> delegate;

    /**
     * A map assigning a depth to each vertex in this tree
     */
    private final @NotNull Map<N, Integer> depthMap;

    /**
     * Creates a tree that delegates to a given graph.
     *
     * @param emptyGraph an empty graph
     * @param vertex     the root of the tree
     * @throws IllegalArgumentException if {@code emptyGraph} is not empty
     */
    public DelegateTree(@NotNull MutableGraph<N> emptyGraph, @NotNull N vertex) {
        if (!emptyGraph.getVertices().isEmpty())
            throw new IllegalArgumentException();

        delegate = emptyGraph;
        depthMap = new HashMap<>();
        root = vertex;

        delegate.addVertex(root);
        depthMap.put(root, 0);
    }

    @Override
    public @NotNull N getRoot() {
        return root;
    }

    @Override
    public int getDepth(@NotNull N vertex) {
        // TODO: implement this
        if(!containsVertex(vertex)) {
            throw new IllegalArgumentException();
        }
        return depthMap.get(vertex);
    }

    @Override
    public int getHeight() {
        // TODO: implement this
        return Collections.max(depthMap.values());
    }

    @Override
    public @NotNull Set<N> getChildren(@NotNull N vertex) {
        // TODO: implement this
        Set<N> neighbors = getNeighborhood(vertex);
        Set<N> children = new HashSet<>();
        for(N neighbor : neighbors) {
            if(depthMap.get(neighbor) == depthMap.get(vertex) + 1) {
                children.add(neighbor);
            }
        }
        return children;
    }

    @Override
    public @NotNull Optional<N> getParent(@NotNull N vertex) {
        // TODO: implement this
        Set<N> neighbors = getNeighborhood(vertex);
        N parent = null;
        for(N neighbor : neighbors) {
            if(depthMap.get(neighbor) == depthMap.get(vertex) - 1) {
                parent = neighbor;
            }
        }
        if(parent == null) {
            return Optional.empty();
        }
        return Optional.of(parent);
    }

    @Override
    public boolean containsVertex(@NotNull N vertex) {
        // TODO: implement this
        return delegate.containsVertex(vertex);
    }

    @Override
    public boolean removeVertex(@NotNull N vertex) {
        // TODO: implement this
        if(!containsVertex(vertex)) {
            return false;
        }
        if(vertex.equals(root)) {
            throw new IllegalArgumentException();
        }
        Set<N> children = getChildren(vertex);
        delegate.removeVertex(vertex);
        for(N child : children) {
            removeVertex(child);
        }
        return true;
    }

    @Override
    public boolean containsEdge(@NotNull N source, @NotNull N target) {
        // TODO: implement this
        return delegate.containsEdge(source, target);
    }

    @Override
    public boolean addEdge(@NotNull N source, @NotNull N target) {
        // TODO: implement this
        if(!containsVertex(source) || containsVertex(target)) {
            return false;
        }
        delegate.addEdge(source, target);
        depthMap.put(target, depthMap.get(source) + 1);
        return true;
    }

    @Override
    public boolean removeEdge(@NotNull N source, @NotNull N target) {
        // TODO: implement this
        if(!containsVertex(source) || !containsVertex(target)) {
            return false;
        }
        if(!containsEdge(source, target)) {
            return false;
        }
        if(!getChildren(source).contains(target)) {
            return false;
        }
        Set<N> children = getChildren(target);
        removeVertex(target);
        for(N child : children) {
            removeVertex(child);
        }
        return true;
    }

    @Override
    public @NotNull Set<N> getNeighborhood(@NotNull N vertex) {
        // TODO: implement this
        return delegate.getNeighborhood(vertex);
    }

    @Override
    public @NotNull Set<N> getVertices() {
        // TODO: implement this
        return delegate.getVertices();
    }

    @Override
    public @NotNull Set<Edge<N>> getEdges() {
        // TODO: implement this
        return delegate.getEdges();
    }

    /**
     * Checks if all class invariants hold for this object
     *
     * @return true if the representation of this tree is valid
     */
    boolean checkInv() {
        // TODO: implement this
        // mutableGraph Invariant
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
        // mutabletree Invariant
        // Single root
        for(N vertex : vertices) {
            if(getParent(vertex).isEmpty() && !vertex.equals(root)) {
                return false;
            }
        }
        // Only one path from the root to any other vertex
        Set<N> reachableVertices = findReachableVertices(root);
        for(N vertex : vertices) {
            if(!reachableVertices.contains(vertex)) {
                return false;
            }
        }
        return true;
    }
}
