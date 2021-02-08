package com.foobar.minion;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class SolutionTest {
    @Test
    public void testImmutableCollections() {
        int[] actual = Solution.solution(new int[]{1, 2, 2, 3, 3, 3, 4, 5, 5}, 1);
        System.out.printf("actual: %s", Arrays.toString(actual));
    }
}
