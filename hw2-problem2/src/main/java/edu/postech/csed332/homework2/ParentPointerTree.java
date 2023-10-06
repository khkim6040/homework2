package edu.postech.csed332.homework2;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

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
        if(!containsVertex(vertex)) {
            throw new IllegalArgumentException();
        }
        return nodeMap.get(vertex).depth;
    }

    @Override
    public int getHeight() {
        // TODO: implement this
        int height = 0;
        for(N vertex: getVertices()) {
            height = Math.max(height, getDepth(vertex));
        }
        return height;
    }

    @Override
    public @NotNull Set<N> getChildren(@NotNull N vertex) {
        // TODO: implement this
        Set<N> children = new HashSet<>();
        for(N child: getVertices()) {
            if(getParent(child).isPresent() && getParent(child).get().equals(vertex)) {
                children.add(child);
            }
        }
        return children;
    }

    @Override
    public @NotNull Optional<N> getParent(@NotNull N vertex) {
        // TODO: implement this
        if(nodeMap.get(vertex).parent == null) {
            return Optional.empty();
        }
        return Optional.of(nodeMap.get(vertex).parent);
    }

    @Override
    public boolean containsVertex(@NotNull N vertex) {
        // TODO: implement this
        return nodeMap.containsKey(vertex);
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
        nodeMap.remove(vertex);
        for(N child: children) {
            removeVertex(child);
        }
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
        if(!containsVertex(source) || containsVertex(target)) {
            return false;
        }
        nodeMap.put(target, new Node<>(source, nodeMap.get(source).depth + 1));
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
        if(nodeMap.get(target).parent != source) {
            return false;
        }
        Set<N> children = getChildren(target);
        nodeMap.remove(target);
        for(N child: children) {
            removeVertex(child);
        }
        return true;
    }

    @Override
    public @NotNull Set<N> getNeighborhood(@NotNull N vertex) {
        // TODO: implement this
        Set<N> neighbors = new HashSet<>();
        // Include parent
        if (getParent(vertex).isPresent()) {
            neighbors.add(getParent(vertex).get());
        }
        // Include children
        Set<N> children = getChildren(vertex);
        if(!children.isEmpty()) {
            neighbors.addAll(children);
        }
        return neighbors;
    }

    @Override
    public @NotNull Set<N> getVertices() {
        // TODO: implement this
        return nodeMap.keySet();
    }

    @Override
    public @NotNull Set<Edge<N>> getEdges() {
        // TODO: implement this
        Set<Edge<N>> edges = new HashSet<>();
        for(N vertex: getVertices()) {
            if(getParent(vertex).isPresent()) {
                N parent = getParent(vertex).get();
                edges.add(new Edge<>(parent, vertex));
                edges.add(new Edge<>(vertex, parent));
            }
        }
        return edges;
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
