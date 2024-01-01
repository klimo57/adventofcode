package at.klimo.aoc.util.graph;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Getter
@Accessors(chain = true)
public abstract class BaseWeightedNode<T> implements IWeightedNode<T> {

    private final Map<IWeightedNode<T>, Integer> adjacentNodesWithDistance;

    private final T value;

    @Setter
    private int distance;

    protected BaseWeightedNode(T value) {
        this.value = value;
        this.distance = Integer.MAX_VALUE;
        this.adjacentNodesWithDistance = new HashMap<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseWeightedNode<?> node = (BaseWeightedNode<?>) o;
        return Objects.equals(value, node.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Node[value=" + value.toString() + "]";
    }
}
