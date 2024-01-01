package at.klimo.aoc.util.graph;

import at.klimo.aoc.ImplementationException;
import lombok.AllArgsConstructor;

import java.util.*;
import java.util.function.BiPredicate;

import static at.klimo.aoc.util.ImmutableCollections.append;
import static java.util.Collections.emptyList;
import static java.util.Comparator.comparing;

@AllArgsConstructor
public class Dijkstra<T> {

    private final IWeightedNode<T> src;
    private final IWeightedNode<T> dst;

    public List<IWeightedNode<T>> shortestPath() {
        return shortestPath((ignored, ignored2) -> true);
    }

    public List<IWeightedNode<T>> shortestPath(BiPredicate<IWeightedNode<T>, IWeightedNode<T>> adjacentNodeFilter) {
        src.setDistance(0);
        var settledNodes = new HashSet<IWeightedNode<T>>();
        var unsettledNodes = new HashSet<IWeightedNode<T>>();
        var shortestPathsMap = new HashMap<IWeightedNode<T>, List<IWeightedNode<T>>>();

        unsettledNodes.add(src);

        while (!unsettledNodes.isEmpty()) {
            IWeightedNode<T> cur = lowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(cur);
//            cur.getAdjacentNodes().entrySet()
//                .stream()
//                .filter(adjacent -> {
//                    var res = adjacentNodeFilter.predicate(adjacent.getKey(), cur);
//                    return res;
//                })
//                .filter(adjacent -> !settledNodes.contains(adjacent.getKey()))
//                .forEach(adjacent -> {
//                    minimumDistance(adjacent.getKey(), adjacent.getValue(), cur);
//                    unsettledNodes.add(adjacent.getKey());
//                });
            for (var adjacent : cur.getAdjacentNodesWithDistance().entrySet()) {
                if (!adjacentNodeFilter.test(adjacent.getKey(), cur)) {
                    continue;
                }
                if (!settledNodes.contains(adjacent.getKey())) {
                    minimumDistance(cur, adjacent.getKey(), adjacent.getValue(), shortestPathsMap);
                    unsettledNodes.add(adjacent.getKey());
                }
            }
            settledNodes.add(cur);
            if (Objects.equals(cur, dst)) {
                return append(LinkedList::new, shortestPathsMap.getOrDefault(cur, emptyList()), cur);
            }
        }
        throw new ImplementationException("no path found");
    }

    private static <T> IWeightedNode<T> lowestDistanceNode(Set<IWeightedNode<T>> unsettledNodes) {
        return unsettledNodes.stream()
            .min(comparing(IWeightedNode::getDistance))
            .orElseThrow(() -> new IllegalStateException("cannot calculate lowest distance node in empty set"));
    }

    private static <T> void minimumDistance(IWeightedNode<T> src,
                                            IWeightedNode<T> dst,
                                            int edgeWeight,
                                            Map<IWeightedNode<T>, List<IWeightedNode<T>>> shortestPathsMap) {
        if (src.getDistance() + edgeWeight < dst.getDistance()) {
            dst.setDistance(src.getDistance() + edgeWeight);
            shortestPathsMap.compute(dst, (key, val) -> append(LinkedList::new, shortestPathsMap.getOrDefault(src, emptyList()), src));
        }
    }
}
