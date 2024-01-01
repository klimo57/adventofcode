package at.klimo.aoc.util.graph;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public class BaseNode<T> implements INode<T> {

    private final T value;
    private final List<INode<T>> adjacentNodes;

    protected BaseNode(T value) {
        this.value = value;
        this.adjacentNodes = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseNode<?> baseNode = (BaseNode<?>) o;
        return Objects.equals(value, baseNode.value);
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
