package at.klimo.aoc.y2023;

import at.klimo.aoc.AOCInputSource;
import org.junit.jupiter.params.ParameterizedTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Solution12Test {

    Solution12 solution = new Solution12();

    @ParameterizedTest
    @AOCInputSource(
        values = {
            "???.### 1,1,3",
            ".??..??...?##. 1,1,3",
            "?#?#?#?#?#?#?#? 1,3,1,6",
            "????.#...#... 4,1,1",
            "????.######..#####. 1,6,5",
            "?###???????? 3,2,1",
            "?.###??.??#?????? 4,8",
            "????.?#???# 2,1,2",
            "???????????#??#???# 5,1,2,1,2",
            "?????#???#?. 2,2",
            "??????? 2,2",
            "????? 1,1",
            "????????? 3,3"
        },
        expectationsAsLong = {1L, 4L, 1L, 1L, 4L, 10L, 2L, 3L, 16L, 4L, 6L, 6L, 6L}
    )
    void testP1(String[] input, long expected) {
        var result = solution.solveP1(solution.mapInput(input));
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @AOCInputSource
    void testP2(String[] input, long expected) {
        var result = solution.solveP2(solution.mapInput(input));
        assertEquals(expected, result);
    }
}
