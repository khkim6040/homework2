package edu.postech.csed332.homework2;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A tree, specified as a subclass of Graph. A tree has a single root vertex, and there
 * exists exactly one path from the root to any other vertex in the tree. This invariant
 * is maintained by restricting the operations for adding and removing vertices and edges.
 *
 * @param <N> type of vertices, which must be immutable and comparable
 */
public interface Tree<N extends Comparable<N>> extends Graph<N> {

    /**
     * Returns the root of this tree. The root is the unique vertex with no parent.
     *
     * @return the root
     */
    @NotNull N getRoot();

    /**
     * Return the distance of vertex from the root of this tree. For example, the root
     * has depth 0, and a child of the root has depth 1.
     *
     * @param vertex a vertex
     * @return the depth of vertex
     * @throws IllegalArgumentException if vertex is not in the graph
     */
    int getDepth(@NotNull N vertex);

    /**
     * Returns the maximum depth in this tree. The maximum depth is the maximum of the
     * depths of all vertices in this tree.
     *
     * @return the maximum depth in this tree
     */
    int getHeight();

    /**
     * Returns the children of a vertex in this tree. For example, consider a tree with
     * vertices {v1, v2, v3, v4} and edges {(v1,v2), (v1,v3), (v2,v1), (v2,v4), (v3,v1),
     * (v4,v2)}, where v1 is the root. Then, the children of v1 are {v2, v3}, the children
     * of v2 are {v4}, and the children of v3 and v4 are empty sets.
     *
     * @param vertex a vertex
     * @return the set of children of vertex in the tree
     */
    @NotNull Set<N> getChildren(@NotNull N vertex);

    /**
     * Returns the parent of a vertex in this tree. For example, consider a tree with
     * vertices {v1, v2, v3, v4} and edges {(v1,v2), (v1,v3), (v2,v1), (v2,v4), (v3,v1),
     * (v4,v2)}, where v1 is the root. Then, the parent of v1 is {@code Optional.empty()},
     * the parent of v2 is v1, and the parents of v3 and v4 are v1 and v2, respectively.
     *
     * @param vertex a vertex
     * @return the parent of vertex, or {@code Optional.empty()} if vertex is the root
     */
    @NotNull Optional<N> getParent(@NotNull N vertex);

    /**
     * Provides a human-readable string representation for the abstract value of the tree
     *
     * @return a string representation
     */
    @Override
    default String toRepr() {
        return String.format("[root: %s, vertex: {%s}, edge: {%s}]",
                getRoot(),
                getVertices().stream().map(N::toString).collect(Collectors.joining(", ")),
                getEdges().stream().map(Edge::toString).collect(Collectors.joining(", ")));
    }
}
