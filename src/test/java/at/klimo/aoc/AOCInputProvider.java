package at.klimo.aoc;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;

public class AOCInputProvider implements ArgumentsProvider, AnnotationConsumer<AOCInputSource> {

    private List<Pair<?, String[]>> inputList = emptyList();

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return inputList
            .stream()
            .map(i -> Arguments.of(i.getRight(), i.getLeft()));
    }

    @Override
    public void accept(AOCInputSource aocInputSource) {
        var inputs = aocInputSource.values();
        var expectations = expectations(aocInputSource);
        inputList = new ArrayList<>(inputs.length);
        for (int i = 0; i < inputs.length; i++) {
            inputList.add(Pair.of(expectations.get(i), inputs[i].lines().toArray(String[]::new)));
        }
    }

    private List<?> expectations(AOCInputSource source) {
        if (source.expectedType().equals(Long.class)) {
            return Arrays.stream(source.expectationsAsLong()).boxed().toList();
        }
        if (source.expectedType().equals(Integer.class)) {
            return Arrays.stream(source.expectationsAsInt()).boxed().toList();
        }
        throw new IllegalArgumentException("unknown expectation type " + source.expectedType().getSimpleName());
    }
}
