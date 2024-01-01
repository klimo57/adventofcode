package at.klimo.aoc.y2023;

import at.klimo.aoc.ImplementationException;
import at.klimo.aoc.Solution;
import at.klimo.aoc.util.CharacterMatrix;
import at.klimo.aoc.util.graph.INode;
import at.klimo.aoc.util.graph.Node;

import java.util.Set;
import java.util.stream.Collectors;

public class Solution21 implements Solution<CharacterMatrix, Integer> {

    @Override
    public Integer solveP1(CharacterMatrix input) throws ImplementationException {
        var start = input.stream()
            .filter(val -> val.value() == 'S')
            .findAny()
            .orElseThrow();
        var nodes = Set.of(new Node<>(start.coordinates()));
        int steps = 64;
        int recordedSteps = 0;
        while (recordedSteps < steps) {
            nodes = nodes.stream()
                .map(INode::getValue)
                .flatMap(p -> p.neighbours(input).straight().stream())
                .filter(p -> input.at(p) != '#')
                .map(Node::new)
                .collect(Collectors.toSet());
            recordedSteps++;
        }
        return nodes.size();
    }

    @Override
    public Integer solveP2(CharacterMatrix input) throws ImplementationException {
        return null;
    }

    @Override
    public CharacterMatrix mapInput(String[] input) {
        return new CharacterMatrix(input);
    }
}
