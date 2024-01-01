package at.klimo.aoc.util.graph;

import java.util.*;
import java.util.function.BiFunction;

import static at.klimo.aoc.util.ImmutableCollections.addAll;

public class DepthFirstSearch<T> {

    private final INode<T> src;
    private final Stack<INode<T>> stack;

    public DepthFirstSearch(INode<T> src) {
        this.src = src;
        this.stack = new Stack<>();
        stack.push(src);
    }

    public INode<T> find(INode<T> goal) {
        return find(goal, Objects::equals);
    }

    public INode<T> find(INode<T> goal, BiFunction<T, T, Boolean> comparator) {
        var node = stack.pop();
        if (comparator.apply(node.getValue(), goal.getValue())) {
            return node;
        }
        node.getAdjacentNodes().forEach(stack::push);
        return find(goal, comparator);
    }

    private Set<INode<T>> possibleVerticesAfterSteps(Collection<INode<T>> startNodes, int steps) {
        if (steps == 0) {
            return Set.of(src);
        }
        return startNodes.stream()
            .map(INode::getAdjacentNodes)
            .map(adjacent -> possibleVerticesAfterSteps(adjacent, steps - 1))
            .reduce(
                new HashSet<>(),
                (acc, next) -> addAll(HashSet::new, acc, next),
                (s1, s2) -> addAll(HashSet::new, s1, s2)
            );
    }
}
