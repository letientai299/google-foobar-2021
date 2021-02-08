package com.foobar.bomb;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class BombTest {
    @ParameterizedTest
    @MethodSource("getTests")
    public void test(String m, String f, String expect) {
        String actual = Bomb.solution(m, f);
        assertEquals(actual, expect);
    }

    static Stream<Arguments> getTests() {
        return Stream.of(
                // m, f, expect
                Arguments.of("4", "7", "4"),
                // (1, 1), (1, 2), (1, 3), (4, 3), (4, 7): 0 to 4
                Arguments.of("3", "2", "2"),
                // (1, 1), (1, 2), (3, 2)
                Arguments.of("2", "1", "1"),
                Arguments.of("2", "4", "impossible")
        );
    }
}
