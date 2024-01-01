package at.klimo.aoc.y2023;

import at.klimo.aoc.ImplementationException;
import at.klimo.aoc.Solution;
import at.klimo.aoc.util.CharacterMatrix;
import at.klimo.aoc.util.PointXY;
import at.klimo.aoc.util.graph.BaseWeightedNode;

import java.util.List;
import java.util.Map;

public class Solution17 implements Solution<CharacterMatrix, Integer> {

    @Override
    public Integer solveP1(CharacterMatrix input) throws ImplementationException {
//        var src = createGraph(input, new PointXY(0, 0), new HashMap<>());
//        var dst = new BaseWeightedNode<>(new PointXY(input.width - 1, input.length - 1));
//        var path = dijkstra(
//            src,
//            dst,
//            (adjacent, cur) -> !isFourthBlockInSameDirection(
//                adjacent.getValue(),
//                cur.getShortestPath()
//                    .stream()
//                    .map(BaseWeightedNode::getValue)
//                    .toList()
//            )
//        );
//        return path.getLast().getDistance();
        return null;
    }

    @Override
    public Integer solveP2(CharacterMatrix input) throws ImplementationException {
        return null;
    }

    static BaseWeightedNode<PointXY> createGraph(CharacterMatrix matrix, PointXY pos, Map<PointXY, BaseWeightedNode<PointXY>> nodes) {
//        if (nodes.containsKey(pos)) {
//            return nodes.get(pos);
//        }
//        var node = new BaseWeightedNode<>(pos);
//        nodes.put(pos, node);
//        pos.neighbours(matrix.width, matrix.length)
//            .straight()
//            .forEach(adjacent -> node.addDestination(createGraph(matrix, adjacent, nodes), matrix.at(adjacent) - 48));
//        return node;
        return null;
    }

    static boolean isFourthBlockInSameDirection(PointXY next, List<PointXY> previous) {
        if (previous.size() < 3) {
            return false;
        }
        var thirdToLast = previous.get(previous.size() - 3);
        return next.x() - thirdToLast.x() > 3 && next.y() == thirdToLast.y() ||
            next.y() - thirdToLast.y() > 3 && next.x() == thirdToLast.x();
    }

    @Override
    public CharacterMatrix mapInput(String[] input) {
        return new CharacterMatrix(input);
    }
}
