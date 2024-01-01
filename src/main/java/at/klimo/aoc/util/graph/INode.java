package at.klimo.aoc.util.graph;

import java.util.List;

public interface INode<T> {

    T getValue();

    List<INode<T>> getAdjacentNodes();

    default void addDestination(INode<T> destination) {
        getAdjacentNodes().add(destination);
    }
}
