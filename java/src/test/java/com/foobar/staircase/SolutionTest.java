package com.foobar.staircase;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SolutionTest {
    @ParameterizedTest
    @MethodSource("getTests")
    public void testStation(int n, int expected) {
        int actual = Solution.solution(n);
        assertEquals(actual, expected);
    }

    static Stream<Arguments> getTests() {
        return Stream.of(
                Arguments.of(3, 1), // 21
                Arguments.of(4, 1), // 31
                Arguments.of(5, 2), // 32, 41
                Arguments.of(6, 3), // 321, 42, 51
                Arguments.of(7, 4), // 421, 43, 52, 61
                Arguments.of(200, 487067745)
        );
    }
}