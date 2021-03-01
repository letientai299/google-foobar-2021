package com.foobar.disorderlyEscape;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Stream;

import static java.math.BigInteger.ONE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.of;

@SuppressWarnings("unused")
class DisorderLyEscapeTest {

    @ParameterizedTest
    @MethodSource("solutionTests")
    void solution(int w, int h, int s, String want) {
        String actual = DisorderLyEscape.solution(w, h, s);
        assertEquals(want, actual);
    }

    private static Stream<Arguments> solutionTests() {
        return Stream.of(
                of(1, 1, 1, "1"),
                of(1, 1, 2, "2"),
                of(1, 1, 3, "3"),
                of(1, 1, 4, "4"),
                of(1, 2, 1, "1"),
                of(1, 2, 2, "3"),
                of(1, 2, 3, "6"),
                of(1, 2, 4, "10"),
                of(1, 3, 1, "1"),
                of(1, 3, 2, "4"),
                of(1, 3, 3, "10"),
                of(1, 3, 4, "20"),
                of(1, 4, 1, "1"),
                of(1, 4, 2, "5"),
                of(1, 4, 3, "15"),
                of(1, 4, 4, "35"),
                of(2, 1, 1, "1"),
                of(2, 1, 2, "3"),
                of(2, 1, 3, "6"),
                of(2, 1, 4, "10"),
                of(2, 2, 1, "1"),
                of(2, 2, 2, "7"),
                of(2, 2, 3, "27"),
                of(2, 2, 4, "76"),
                of(2, 3, 1, "1"),
                of(2, 3, 2, "13"),
                of(2, 3, 3, "92"),
                of(2, 3, 4, "430"),
                of(2, 4, 1, "1"),
                of(2, 4, 2, "22"),
                of(2, 4, 3, "267"),
                of(2, 4, 4, "1996"),
                of(3, 1, 1, "1"),
                of(3, 1, 2, "4"),
                of(3, 1, 3, "10"),
                of(3, 1, 4, "20"),
                of(3, 2, 1, "1"),
                of(3, 2, 2, "13"),
                of(3, 2, 3, "92"),
                of(3, 2, 4, "430"),
                of(3, 3, 1, "1"),
                of(3, 3, 2, "36"),
                of(3, 3, 3, "738"),
                of(3, 3, 4, "8240"),
                of(3, 4, 1, "1"),
                of(3, 4, 2, "87"),
                of(3, 4, 3, "5053"),
                of(3, 4, 4, "131505"),
                of(4, 1, 1, "1"),
                of(4, 1, 2, "5"),
                of(4, 1, 3, "15"),
                of(4, 1, 4, "35"),
                of(4, 2, 1, "1"),
                of(4, 2, 2, "22"),
                of(4, 2, 3, "267"),
                of(4, 2, 4, "1996"),
                of(4, 3, 1, "1"),
                of(4, 3, 2, "87"),
                of(4, 3, 3, "5053"),
                of(4, 3, 4, "131505"),
                of(4, 4, 1, "1"),
                of(4, 4, 2, "317"),
                of(4, 4, 3, "90492")
        );
    }

    @ParameterizedTest
    @MethodSource("numbersSumToNTests")
    void numbersSumToN(int n, List<int[]> want) {
        List<int[]> lists = DisorderLyEscape.numbersSumToN(n);
        Assertions.assertThat(lists).hasSameElementsAs(want);
    }

    private static Stream<Arguments> numbersSumToNTests() {
        return Stream.of(
                of(2, List.of(new int[]{2})),
                of(3, List.of(new int[]{3})),

                of(4, List.of(
                        new int[]{2, 2},
                        new int[]{4}
                )),

                of(5, List.of(
                        new int[]{2, 3},
                        new int[]{5}
                )),

                of(6, List.of(
                        new int[]{2, 2, 2},
                        new int[]{2, 4},
                        new int[]{3, 3},
                        new int[]{6}
                )),

                of(7, List.of(
                        new int[]{2, 2, 3},
                        new int[]{2, 5},
                        new int[]{3, 4},
                        new int[]{7}
                ))
        );
    }

    @ParameterizedTest
    @MethodSource("combinationTests")
    void combination(int n, int k, int want) {
        BigInteger bn = BigInteger.valueOf(n);
        BigInteger bk = BigInteger.valueOf(k);
        BigInteger bWant = BigInteger.valueOf(want);
        assertEquals(bWant, DisorderLyEscape.combination(bn, bk));
    }

    private static Stream<Arguments> combinationTests() {
        // https://www.calculator.net/permutation-and-combination-calculator.html
        return Stream.of(
                of(120, 5, 190578024),
                of(10, 5, 252),
                of(2, 2, 1),
                of(4, 2, 6),
                of(10, 4, 210),
                of(50, 47, 19600)
        );
    }

    @ParameterizedTest
    @MethodSource("makePermutationTests")
    void makePermutation(int n, int fixed, int[] cycles, BigInteger wantCount) {
        DisorderLyEscape.Permutation p = DisorderLyEscape.makePermutation(n, fixed, cycles);
        assertEquals(wantCount, p.count);
    }

    private static Stream<Arguments> makePermutationTests() {
        return Stream.of(
                // 12 34
                // 13 24
                // 14 23
                of(4, 0, new int[]{2, 2}, BigInteger.valueOf(3)),

                // 12 34 56
                // 12 35 46
                // 12 36 45
                //
                // 13 24 56
                // 13 25 46
                // 13 26 45
                //
                // 14 23 56
                // 14 25 36
                // 14 26 35
                //
                // 15 23 46
                // 15 24 36
                // 15 26 34
                //
                // 16 23 45
                // 16 24 35
                // 16 25 34
                of(6, 0, new int[]{2, 2, 2}, BigInteger.valueOf(15)),
                of(5, 1, new int[]{2, 2}, BigInteger.valueOf(3 * 5)),
                of(7, 0, new int[]{2, 2, 3}, BigInteger.valueOf(210))
        );
    }

    @ParameterizedTest
    @MethodSource("genPermutationsTests")
    void genPermutations(int n, List<DisorderLyEscape.Permutation> want) {
        List<DisorderLyEscape.Permutation> ps = DisorderLyEscape.genPermutations(n);
        assertEquals(want, ps);
    }

    private static Stream<Arguments> genPermutationsTests() {
        return Stream.of(
                of(2, List.of(
                        new DisorderLyEscape.Permutation(0, new int[]{2}, ONE),
                        new DisorderLyEscape.Permutation(2, new int[0], ONE)
                ))
        );
    }

    @ParameterizedTest
    @MethodSource("powTests")
    void pow(BigInteger a, BigInteger b, BigInteger want) {
        BigInteger actual = DisorderLyEscape.pow(a, b);
        assertEquals(want, actual);
    }

    private static Stream<Arguments> powTests() {
        return Stream.of(
                of(BigInteger.valueOf(1000), BigInteger.valueOf(0), ONE),
                of(BigInteger.valueOf(2), BigInteger.valueOf(3), BigInteger.valueOf(8)),
                of(BigInteger.valueOf(3), BigInteger.valueOf(10), BigInteger.valueOf(59049))
        );
    }

}
