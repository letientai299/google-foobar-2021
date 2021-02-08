package com.foobar.queue;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;

class QueueTest {
    @ParameterizedTest
    @MethodSource("getTests")
    public void testQueue(int start, int length, int expected) {
        int actual = Queue.solution(start, length);
        assertEquals(actual, expected, () ->
                format("expect=%s, actual=%s", expected, actual)
        );
    }

    static Stream<Arguments> getTests() {
        return Stream.of(
                Arguments.of(0, 3, 2),
                Arguments.of(17, 4, 14)
        );
    }
}