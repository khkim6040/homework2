package edu.postech.csed332.homework2;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * An implementation of Tree, where each vertex has a reference to its parent node but
 * no references to its children.
 *
 * @param <N> type of vertices, which must be immutable and comparable
 */
public class ParentPointerTree<N extends Comparable<N>> implements MutableTree<N> {

    private record Node<V>(@Nullable V parent, int depth) {
    }

    /**
     * A root vertex of this tree.
     */
    private final @NotNull N root;

    /**
     * A map assigning to each vertex a pair of a parent reference and a depth. The parent
     * of the root is {@code null}. For example, a tree with  vertices {v1, v2, v3, v4} edges
     * {(v1,v2), (v1,v3), (v2,v1), (v2,v4), (v3,v1), (v4,v2)}, where v1 is the root, is
     * represented by the map {v1 |-> (null,0), v2 |-> (v1,1), v3 |-> (v1,1), v4 |-> (v2,2)}.
     */
    private final @NotNull SortedMap<N, Node<N>> nodeMap;

    /**
     * Create a parent pointer tree with a given root vertex.
     *
     * @param vertex the root of the tree
     */
    public ParentPointerTree(@NotNull N vertex) {
        root = vertex;
        nodeMap = new TreeMap<>();
        nodeMap.put(root, new Node<>(null, 0));
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
