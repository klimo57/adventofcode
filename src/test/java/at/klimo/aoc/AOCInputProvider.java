package at.klimo.aoc;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Collections.emptyMap;

public class AOCInputProvider implements ArgumentsProvider, AnnotationConsumer<AOCInputSource> {

    private Map<Long, String[]> inputMap = emptyMap();

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return inputMap.entrySet()
            .stream()
            .map(e -> Arguments.of(e.getValue(), e.getKey()));
    }

    @Override
    public void accept(AOCInputSource aocInputSource) {
        var inputs = aocInputSource.values();
        var expectations = aocInputSource.expectationsAsLong();
        inputMap = new HashMap<>(inputs.length);
        for (int i = 0; i < inputs.length; i++) {
            inputMap.put(expectations[i], inputs[i].lines().toArray(String[]::new));
        }
    }
}
