package com.foobar.gun;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GunTest {
    @ParameterizedTest
    @MethodSource("getTests")
    public void test(int[] dimensions, int[] mine, int[] trainer, int distance, int expect) {
        int actual = Gun.solution(dimensions, mine, trainer, distance);
        assertEquals(expect, actual);
    }

    static Stream<Arguments> getTests() {
        return Stream.of(
                Arguments.of(
                        new int[]{2, 5},
                        new int[]{1, 2},
                        new int[]{1, 4},
                        11, // distance
                        27 // directions
                ),

                Arguments.of(
                        new int[]{3, 2},
                        new int[]{1, 1},
                        new int[]{2, 1},
                        4, // distance
                        7 // directions
                ),

                Arguments.of(
                        new int[]{23, 10},
                        new int[]{6, 4},
                        new int[]{3, 2},
                        23, // distance
                        8 // directions
                ),


                Arguments.of(
                        new int[]{4, 4},
                        new int[]{1, 1},
                        new int[]{2, 2},
                        6, // distance
                        7 // directions
                ),

                Arguments.of(
                        new int[]{3, 3},
                        new int[]{1, 1},
                        new int[]{1, 2},
                        4, // distance
                        4 // directions
                ),

                Arguments.of(
                        new int[]{3, 2},
                        new int[]{1, 1},
                        new int[]{3, 2},
                        4, // distance
                        2 // directions
                ),

                Arguments.of(
                        new int[]{300, 275},
                        new int[]{150, 150},
                        new int[]{185, 100},
                        500, 9
                ),

                Arguments.of(
                        new int[]{10, 10},
                        new int[]{4, 4},
                        new int[]{3, 3},
                        5000,
                        739323
                )

        );
    }
}