package com.foobar.station;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;

class SolutionTest {
    @ParameterizedTest
    @MethodSource("getTests")
    public void testStation(int[] l, int t, int[] expected) {
        int[] actual = Solution.solution(l, t);
        assertArrayEquals(actual, expected, () ->
                format("expect=%s, actual=%s",
                        Arrays.toString(expected),
                        Arrays.toString(actual)
                )
        );
    }

    static Stream<Arguments> getTests() {
        return Stream.of(
                Arguments.of(new int[]{1, 2, 3, 4}, 10, new int[]{0, 3}),
                Arguments.of(new int[]{1, 2, 3, 4}, 15, new int[]{-1, -1}),
                Arguments.of(new int[]{4, 3, 5, 7, 8}, 12, new int[]{0, 2}),
                Arguments.of(new int[]{4, 3, 10, 2, 8}, 12, new int[]{2, 3}),
                Arguments.of(new int[]{4, 3, 10, 2, 8}, 10, new int[]{2, 2})
        );
    }
}