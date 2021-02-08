package com.foobar.escape;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EscapeTest {
    @ParameterizedTest
    @MethodSource("getTests")
    public void test(int[] entrances, int[] exits, int[][] path, int want) {
        int actual = Escape.solution(entrances, exits, path);
        assertEquals(want, actual);
    }

    static Stream<Arguments> getTests() {
        return Stream.of(
                Arguments.of(
                        new int[]{0}, new int[]{1, 2},
                        new int[][]{
                                {0, 7, 1},
                                {0, 0, 9},
                                {0, 0, 0}
                        },
                        8
                ),

                Arguments.of(
                        new int[]{0}, new int[]{2},
                        new int[][]{
                                {0, 7, 1},
                                {0, 0, 9},
                                {0, 0, 0}
                        },
                        8
                ),

                Arguments.of(
                        new int[]{0}, new int[]{1},
                        new int[][]{
                                {0, 7},
                                {0, 0}
                        },
                        7
                ),

                Arguments.of(
                        new int[]{0}, new int[]{3},
                        new int[][]{
                                {0, 7, 0, 0},
                                {0, 0, 6, 0},
                                {0, 0, 0, 8},
                                {9, 0, 0, 0}
                        },
                        6
                ),

                Arguments.of(
                        new int[]{0, 1}, new int[]{4, 5},
                        new int[][]{
                                {0, 0, 4, 6, 0, 0},
                                {0, 0, 5, 2, 0, 0},
                                {0, 0, 0, 0, 4, 4},
                                {0, 0, 0, 0, 6, 6},
                                {0, 0, 0, 0, 0, 0},
                                {0, 0, 0, 0, 0, 0}
                        },
                        16
                )
        );
    }
}