package at.klimo.aoc;

import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ArgumentsSource(AOCInputProvider.class)
public @interface AOCInputSource {
    String[] values() default {};

    long[] expectationsAsLong() default {};
}
