package at.klimo.aoc.util.graph;

import java.util.Map;

public interface IWeightedNode<T> extends INode<T>, Comparable<IWeightedNode<T>> {
    int getDistance();

    IWeightedNode<T> setDistance(int distance);

    Map<IWeightedNode<T>, Integer> getAdjacentNodesWithDistance();

    default void addDestination(IWeightedNode<T> destination, int distance) {
        getAdjacentNodesWithDistance().put(destination, distance);
    }

    @Override
    default int compareTo(IWeightedNode<T> o) {
        return Integer.compare(getDistance(), o.getDistance());
    }
}
