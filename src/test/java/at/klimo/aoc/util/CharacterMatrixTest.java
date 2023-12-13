package at.klimo.aoc.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CharacterMatrixTest {

    @Test
    void testRowAsString() {
        var lines = new String[]{
            "123",
            "456",
            "789"
        };
        var result = new CharacterMatrix(lines).rowAsString(1);
        assertEquals("456", result);
    }

    @Test
    void testColumnAsString() {
        var lines = new String[]{
            "123",
            "456",
            "789"
        };
        var result = new CharacterMatrix(lines).columnAsString(1);
        assertEquals("258", result);
    }
}
