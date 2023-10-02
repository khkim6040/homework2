package edu.postech.csed332.homework2;

import org.jetbrains.annotations.NotNull;

/**
 * An edge of a graph, given by a pair of two vertices of type V. Notice that
 * an instance of Edge is directed, i.e., Edge[source=v1, target=v2] is different
 * from Edge[source=v2, target=v1]. To represent an undirected edge in a graph,
 * one needs to create two instances of Edge.
 *
 * @param <N> type of vertices, which must be immutable and comparable
 */
public record Edge<N extends Comparable<N>>(@NotNull N source, @NotNull N target) implements Comparable<Edge<N>> {

    @Override
    public int compareTo(@NotNull Edge<N> o) {
        int c1 = this.source.compareTo(o.source);
        return c1 != 0 ? c1 : this.target.compareTo(o.target);
    }

    @Override
    public String toString() {
        return "(" + source + "," + target + ")";
    }
}