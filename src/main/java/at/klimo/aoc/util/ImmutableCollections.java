package at.klimo.aoc.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public class ImmutableCollections {

    public static <T> List<T> append(Supplier<List<T>> listSupplier, List<T> list, T... appendages) {
        var newList = listSupplier.get();
        newList.addAll(list);
        Arrays.stream(appendages).forEach(newList::add);
        return newList;
    }

    public static <T, C extends Collection<T>> C addAll(Supplier<C> collectionSupplier,
                                                        Collection<T> base,
                                                        Collection<T> toAdd) {
        var c = collectionSupplier.get();
        c.addAll(base);
        c.addAll(toAdd);
        return c;
    }

    private ImmutableCollections() {
    }
}
