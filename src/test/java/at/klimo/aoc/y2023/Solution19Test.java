package at.klimo.aoc.y2023;

import at.klimo.aoc.AOCInputSource;
import org.junit.jupiter.params.ParameterizedTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Solution19Test {

    Solution19 solution = new Solution19();

    @ParameterizedTest
    @AOCInputSource(
        values = {
            """
                px{a<2006:qkq,m>2090:A,rfg}
                pv{a>1716:R,A}
                lnx{m>1548:A,A}
                rfg{s<537:gd,x>2440:R,A}
                qs{s>3448:A,lnx}
                qkq{x<1416:A,crn}
                crn{x>2662:A,R}
                in{s<1351:px,qqz}
                qqz{s>2770:qs,m<1801:hdj,R}
                gd{a>3333:R,R}
                hdj{m>838:A,pv}
                                
                {x=787,m=2655,a=1222,s=2876}
                {x=1679,m=44,a=2067,s=496}
                {x=2036,m=264,a=79,s=2244}
                {x=2461,m=1339,a=466,s=291}
                {x=2127,m=1623,a=2188,s=1013}
                """
        },
        expectationsAsLong = {19114L}
    )
    void testP1(String[] input, long expected) {
        var result = solution.solveP1(solution.mapInput(input));
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @AOCInputSource(
        values = {},
        expectationsAsLong = {}
    )
    void testP2(String[] input, long expected) {
        var result = solution.solveP2(solution.mapInput(input));
        assertEquals(expected, result);
    }
}
