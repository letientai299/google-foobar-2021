package com.foobar.coded;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class SolutionTest {

    @ParameterizedTest(name = "{index} => array   = {0} ),  expected = {1} ")
    @MethodSource("getTests")
    public void testCoded(int[] digits, int expected) {
        int actual = Solution.solution(digits);
        assertEquals(expected, actual);
    }

    static Stream<Arguments> getTests() {
        return Stream.of(
                Arguments.of(new int[]{0, 0, 0, 0, 1, 1, 1, 1, 1, 3, 3, 3, 3}, 3),
                Arguments.of(new int[]{1, 1, 3}, 3),
                Arguments.of(new int[]{6, 3}, 63),
                Arguments.of(new int[]{3, 1, 4, 1}, 4311),
                Arguments.of(new int[]{3, 1, 4, 1, 5, 9}, 94311),
                Arguments.of(new int[]{3, 2, 4, 1, 5, 9}, 954321),
                Arguments.of(new int[]{3, 0, 4, 1, 5, 9}, 95430),
                Arguments.of(new int[]{3, 0, 1, 1, 5, 9}, 95310),
                Arguments.of(new int[]{1, 0, 0, 4}, 0)
        );
    }
}
