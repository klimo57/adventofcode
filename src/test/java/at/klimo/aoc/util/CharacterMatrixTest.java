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

    @Test
    void testTranspose() {
        var m = new CharacterMatrix(new String[]{
                "123",
                "456",
                "789"
        });
        var result = m.transpose();
        assertEquals(new CharacterMatrix(new String[]{
                "147",
                "258",
                "369"
        }), result);
    }

    @Test
    void testSpin90degClockwise() {
        var m = new CharacterMatrix(new String[]{
                "123",
                "456",
                "789"
        });
        var result = m.spin90degClockwise();
        assertEquals(new CharacterMatrix(new String[]{
                "741",
                "852",
                "963"
        }), result);
    }

    @Test
    void testReplace() {
        var m = new CharacterMatrix(new String[]{
                "123",
                "456",
                "789"
        });
        var result = m.replaceCharAt(1, 1, 'X');
        assertEquals(new CharacterMatrix(new String[]{
                "123",
                "4X6",
                "789"
        }), result);
    }
}
