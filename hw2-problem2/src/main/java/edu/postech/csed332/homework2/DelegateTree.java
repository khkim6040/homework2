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
        return 0;
    }

    @Override
    public int getHeight() {
        // TODO: implement this
        return 0;
    }

    @Override
    public @NotNull Set<N> getChildren(@NotNull N vertex) {
        // TODO: implement this
        return Set.of();
    }

    @Override
    public @NotNull Optional<N> getParent(@NotNull N vertex) {
        // TODO: implement this
        return Optional.empty();
    }

    @Override
    public boolean containsVertex(@NotNull N vertex) {
        // TODO: implement this
        return false;
    }

    @Override
    public boolean removeVertex(@NotNull N vertex) {
        // TODO: implement this
        return false;
    }

    @Override
    public boolean containsEdge(@NotNull N source, @NotNull N target) {
        // TODO: implement this
        return false;
    }

    @Override
    public boolean addEdge(@NotNull N source, @NotNull N target) {
        // TODO: implement this
        return false;
    }

    @Override
    public boolean removeEdge(@NotNull N source, @NotNull N target) {
        // TODO: implement this
        return false;
    }

    @Override
    public @NotNull Set<N> getNeighborhood(@NotNull N vertex) {
        // TODO: implement this
        return Set.of();
    }

    @Override
    public @NotNull Set<N> getVertices() {
        // TODO: implement this
        return Set.of();
    }

    @Override
    public @NotNull Set<Edge<N>> getEdges() {
        // TODO: implement this
        return Set.of();
    }

    /**
     * Checks if all class invariants hold for this object
     *
     * @return true if the representation of this tree is valid
     */
    boolean checkInv() {
        // TODO: implement this
        return false;
    }
}
