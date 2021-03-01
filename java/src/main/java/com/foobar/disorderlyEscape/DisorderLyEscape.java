package com.foobar.disorderlyEscape;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;

// Since this problem requires another level of math to solve it, I think its
// code is best written with Literate programming, a skill I rarely use for
// day-to-day work.
//
// https://en.wikipedia.org/wiki/Literate_programming
//
// The result of Literate Programming is the comments on this file are not
// typical Javadoc (so don't blame me). Instead, it's my reasoning behind the
// computation.
//
// Kindly note that I cannot explain everything. Curious readers should learn a
// bit about Group theory to fully understand the terms used in the solution.
//
// "An introduction to group theory" by Tony Gaglione is a good resource (beside
// many other resources I use to grasp the basic of Group Theory).
//
// https://wdjoyner.files.wordpress.com/2016/08/gaglione-gp-thry.pdf
//
// Since I'm not even an amateur mathematician, pardon me if some terms used
// below is incorrect or mismatches with their rigorous mathematics definition.
public class DisorderLyEscape {

    // We'll use Burnside's lemma to solve this problem.
    //
    // https://en.wikipedia.org/wiki/Burnside%27s_lemma
    //
    // Our set X is all the possible states of the matrix. Size of X can be
    // easily computed:
    //
    // |X| = W*H
    //
    // The group G that acts on it are the products of the row and column
    // permutations. In math terms, the permutations groups are called Symmetric
    // Group and denote as S_n, where n is the size of the set to be permuted.
    //
    // https://en.wikipedia.org/wiki/Symmetric_group
    //
    // For our matrix of WxH cells, the column permutations is S_w and row
    // permutations is S_h. Thus:
    //
    // G = S_w.S_h
    // |G| = W!*H!
    //
    // Note that the dot `.` notation means the "multiplication" in Group
    // Theory, while the asterisk `*` is our typical numeric multiplication.
    //
    // Unlike the cube's faces coloring example in Burnside's lemma article,
    // there's too many action in G to list them all and derive number of fixed
    // elements in X for each action. We need another way.
    //
    // From now on, we will use Cycle Notation to denote the permutation.
    //
    // https://en.wikipedia.org/wiki/Permutation#Cycle_notation
    //
    // Let's use following example with specific permutations to get an
    // intuitive idea how to solve it.
    //
    // - W=8, H=7.
    // - Column permutation c=(0,1,2)(3,4). This fixes _3 columns_: 5, 6, 7.
    // - Row permutation r=(0,1)(2,3,4). This fixes _2 rows_: 5, 6.
    //
    //                         fixed
    //                         -----
    //           0 1 2   3 4   5 6 7
    //         +--------------------
    //        0| a a a   b b   m m m
    //        1| a a a   b b   m m m
    //         |
    //        2| d d d   e e   g g g
    //        3| d d d   e e   g g g
    //        4| d d d   e e   g g g
    //         |
    // fixed |5| i i i   k k   F F F
    //       |6| i i i   k k   F F F
    //
    // We can see that the 2 permutations r and c split the matrix into multiple
    // smaller one.
    //
    // Let's call M(a) to dente the sub-matrix consists of only a cells.
    // Similar, M(F) is the one consists of only F cells.
    //
    // Let's also use |M(a)| to denote number of possible configuration of M(a).
    // Thus, the number of fixed elements in X for this particular r.c
    // permutation is:
    //
    // |X^(r.c)| = |M(a)| .|M(b)|...|M(F)|
    //
    // There's some properties that can help us to calculate some of those
    // |M(x)|.
    //
    // - F cells are fixed by both r and c. So they can use any of S states.
    // Thus: |M(F)| = S^(3*2) = S^6. It's example of sub-matrix that is entirely
    // (2-side) fixed.
    //
    // - All columns of M(m) are fixed by r, so cells in same rows don't need
    // have same state. However, c will swap row 0 and 1, in order to fix M(m),
    // we need all cells in each of columns 5, 6, 7 to have same state. This
    // leads to: |M(m)| = S^3
    //
    // - With same logic, we can calculate number of fixed elements of M(g),
    // M(i), M(k). Including M(m), they are example for how to calculate number
    // of sub-matrix that has 1 side fixed.
    //
    // Now, how do we calculate number of those sub-matrix that has 0-side
    // fixed. Let's look closer to how cells in M(d) move after the
    // permutations.
    //
    //           0 1 2
    //         +------
    //        2| d d d
    //        3| d d d
    //        4| d d d
    //
    // The permutations affects M(d) is: c1=(0,1,2) and r1=(2,3,4). Let's use
    // [x,y] notation to call the cell at column x and row y.  We can see that:
    //
    // - [0,2] move to [1,3]
    // - [1,3] more to [2,4]
    // - [2,4] more to [0,2]
    //
    // In order for M(d) to be fixed, it requires 3 cells [0,2], [1,3] and [2,4]
    // to have same state. Similarly, following group of 3 cells must have same
    // states:
    //
    // - [1,2], [2,3], [0,4]
    // - [2,2], [0,3], [1,4]
    //
    // To visualize the finding, here's how M(d) should look like:
    //
    //            0   1   2
    //         +-----------
    //        2| d1  d2  d3
    //        3| d3  d1  d2
    //        4| d2  d3  d1
    //
    // It's now clear that |M(d)| = S^3.
    //
    // Please try it yourself to see:
    //
    // - |M(a)| = S^1 = S
    // - |M(b)| = S^2
    // - |M(e)| = S
    //
    // We can see that our permutations split the sub-matrix into several
    // disjoint circles, and:
    //
    // - Each cell belong to exactly and 1 circle, and move within it if we
    // apply the permutations repetitively.
    // - In order to fix the sub-matrix, it requires all cells in same circle to
    // have same state.
    //
    // Let use len(r1) to denote the length of the row permutation r1. Similar,
    // len(c1) means length of column permutation c1.
    //
    // It's easy to see that the size of the sub-matrix is len(c1).len(r1).
    //
    // You might also guess that the number of circles within each sub-matrix is
    // also determined by the length of permutations. Indeed, it is.
    //
    // Think about how the top-left in sub-matrix travel in its circle if we
    // apply the permutations repetitively. Let's say its original location is
    // [0, 0] (relative to the sub-matrix location within the parent matrix).
    //
    // It needs len(r1) steps to go back to top row. For each steps, beside
    // moving between rows, it also move between columns. Hence, by the time it
    // go back to top row, its location should be:
    //
    // [len(r1) % len(c1), 0]
    //
    // If we repetitively apply k times len(r1) steps, then the cell location
    // after that would be:
    //
    // [k * len(r1) % len(c1), 0]
    //
    // Our cycle length is determined by the smallest k such that:
    //
    // k*len(r1) % len(c1) == 0
    //
    // Thus
    //
    // k*len(r1) == lcm(len(r1), len(r2))
    //
    // where lcm(a, b) is the Least Common Multiple of a and b.
    //
    // Now, we can see that:
    //
    // |M(d)| = S^( len(r1)*len(c1) / lcm(len(r1),len(c1) )
    //
    // We can verify our examples again:
    //
    // - |M(a)| = S^(2*3/lcm(2,3)) = S^(6/6) = S
    // - |M(b)| = S^(2*2/lcm(2,2)) = S^(4/2) = S^2
    // - |M(d)| = S^(3*3/lcm(3,3)) = S^(9/3) = S^3
    // - |M(e)| = S
    //
    // Now, with all the logic reasoned, we know what do implements:
    //
    // - Generate all the pairs (fixed_num, [l_0, l_1, ..., l_k]) from W Here,
    // fixed_num is number of columns that is fixed by the permutation on W, l_i
    // (where 0 <= i <= k) is length of the disjoint circles withing that
    // permutation. Note that sum of fixed_num and all l_i must be equal to W.
    // Let's call it per(W).
    //
    // - Similarly, generate per(H).
    //
    // - For each item in per(W), we calculate number of fixed matrices against
    // it and all items in per(H) according to our logic.
    //
    // - The sum of all result from above steps, after divide for |G| is our
    // final result.
    public static String solution(int width, int height, int states) {
        final List<Permutation> colPermutations = genPermutations(width);
        final List<Permutation> rowPermutations = genPermutations(height);

        final BigInteger S = BigInteger.valueOf(states);

        BigInteger total = colPermutations
                .stream()
                .parallel()
                .map(cp -> {
                    BigInteger sum = ZERO;
                    for (Permutation rp : rowPermutations) {
                        BigInteger fixedMatrices = countFixedMatrices(rp, cp, S);
                        sum = sum.add(fixedMatrices);
                    }
                    return sum;
                })
                .reduce(BigInteger::add)
                .orElse(ZERO);

        BigInteger G = factorial(width).multiply(factorial(height));
        BigInteger result = total.divide(G);
        return result.toString();
    }

    private static BigInteger countFixedMatrices(Permutation rp, Permutation cp, BigInteger S) {
        // calculate for M(F) sub-matrix
        BigInteger fixed = S.pow(rp.fixed * cp.fixed);
        fixed = fixed.multiply(rp.count);
        fixed = fixed.multiply(cp.count);

        // calculate for those sub-matrices that only permute on rows
        BigInteger m = S.pow(cp.fixed * rp.cycles.length);
        fixed = fixed.multiply(m);

        // calculate for those sub-matrices that only permute on columns
        m = S.pow(rp.fixed * cp.cycles.length);
        fixed = fixed.multiply(m);

        // calculate for those sub-matrices that permute both sides.
        int t = 0;
        for (int lr : rp.cycles) {
            for (int lc : cp.cycles) {
                int v = lr * lc / lcm(lr, lc);
                t += v;
            }
        }

        m = S.pow(t);
        fixed = fixed.multiply(m);
        return fixed;
    }

    static List<Permutation> genPermutations(int n) {
        // fixed should only run from 0 to n-2, because if we fix n-1 elements,
        // the last one has no where to go.
        List<Permutation> result = IntStream.rangeClosed(0, n - 2)
                .parallel()
                .mapToObj(fixed -> {
                    List<Permutation> ls = new ArrayList<>();

                    // Recall our logic about permutation configurations:
                    //
                    // N = fixed + l_1 + l_2 + ... + l_k
                    //
                    // Since we know the `fixed`, we need to generate the list
                    // of l_i, we just need to generate all the list of numbers
                    // that sum to N-fixed.
                    List<int[]> cyclesList = numbersSumToN(n - fixed);

                    for (int[] cycles : cyclesList) {
                        Permutation p = makePermutation(n, fixed, cycles);
                        ls.add(p);
                    }
                    return ls;
                })
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        // Our result would be incomplete without the identity permutation.
        result.add(new Permutation(n, new int[0], ONE));
        return result;
    }

    static List<int[]> numbersSumToN(int n) {
        List<int[]> res = new ArrayList<>();
        // our cycles should have length greater than 1 (otherwise, it's a fixed
        // permutation).
        int[] ls = new int[n / 2 + 1];
        int len = 0;
        for (int i = 2; i <= n; i++) {
            ls[len] = i;
            numbersSumToNRecursive(res, n - i, ls, len + 1);
            ls[len] = 0;
        }
        return res;
    }

    static void numbersSumToNRecursive(
            List<int[]> all, int rem, int[] ls, int len
    ) {
        if (rem == 0) {
            int[] copy = new int[len];
            System.arraycopy(ls, 0, copy, 0, len);
            all.add(copy);
            return;
        }

        if (rem < 0) {
            return;
        }

        int last = ls[len - 1];
        for (int i = last; i <= rem; i++) {
            ls[len] = i;
            numbersSumToNRecursive(all, rem - i, ls, len + 1);
            ls[len] = 0;
        }
    }

    static Permutation makePermutation(int n, int fixed, int[] cycles) {
        BigInteger N = BigInteger.valueOf(n);
        BigInteger f = BigInteger.valueOf(fixed);

        // We need to count how many permutation of n elements that satisfy
        // given configurations. We start with the number of ways to select
        // `fixed` elements our of n.
        BigInteger count = combination(N, f);

        BigInteger remains = BigInteger.valueOf(n - fixed);
        int repeatedLen = 1;
        int previousLen = 0;

        // Then, for each cycle-length l_i, we count how many ways to construct
        // all derangement of l_i elements out of remaining elements.
        //
        // That requires 2 steps:
        //
        // - Count how many ways to select l_i elements.
        // - Count number of disjoint cycles for l_i elements that has length
        // l_i, which is (l_i-1)! (see https://en.wikipedia.org/wiki/Cyclic_permutation)
        //
        // Note that our counting might count 1 permutation several times. For
        // examples, if the cycles configurations are [2, 2, 3], fixed=0, then
        // the following
        //
        // - (0,1)(2,3)(4,5,6)
        // - (2,3)(0,1)(4,5,6)
        //
        // are valid element selections, but they're same permutation and should
        // not be counted twice.
        //
        // Since our numbersSumToN function should generate a sorted list, we
        // can keep track of previous l_i during counting and remove duplication
        // by divide current result with `repeatedLen!`.
        for (int ln : cycles) {
            BigInteger len = BigInteger.valueOf(ln);
            BigInteger selections = combination(remains, len);
            remains = remains.subtract(len);
            BigInteger disjointCycles = factorial(ln - 1);
            count = count.multiply(selections).multiply(disjointCycles);
            if (previousLen == ln) {
                repeatedLen++;
            } else {
                count = count.divide(factorial(repeatedLen));
                repeatedLen = 1;
                previousLen = ln;
            }
        }

        count = count.divide(factorial(repeatedLen));
        return new Permutation(fixed, cycles, count);
    }

    static class Permutation {
        int fixed;
        int[] cycles;
        // To save computation, we shouldn't generate all permutations with the
        // same configurations. Instead, we count them.
        //
        // For examples, when n=3, following permutations have same config:
        // (fixed:1, cycles:[2])
        //
        // - (1)(2,3)
        // - (2)(1,3)
        // - (3)(1,2)
        //
        // The actual permutation isn't matter in our calculation, we only need
        // the count and the config.
        BigInteger count;

        public Permutation(int fixed, int[] cycles, BigInteger count) {
            this.fixed = fixed;
            this.cycles = cycles;
            this.count = count;
        }

        @Override
        public String toString() {
            return "Permutation{" +
                    "fixed=" + fixed +
                    ", cycles=" + Arrays.toString(cycles) +
                    ", count=" + count +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Permutation that = (Permutation) o;
            return fixed == that.fixed && Arrays.equals(cycles, that.cycles) && Objects.equals(count, that.count);
        }

        @Override
        public int hashCode() {
            int result = Objects.hash(fixed, count);
            result = 31 * result + Arrays.hashCode(cycles);
            return result;
        }
    }

    static BigInteger factorial(int n) {
        BigInteger r = ONE;
        for (int i = 1; i <= n; i++) {
            r = r.multiply(BigInteger.valueOf(i));
        }
        return r;
    }

    static BigInteger combination(BigInteger n, BigInteger k) {
        if (n.compareTo(k) < 0) {
            return ZERO;
        }

        BigInteger v = ONE;
        BigInteger i = ONE;
        while (i.compareTo(k) <= 0) {
            v = v.multiply(n).divide(i);
            i = i.add(ONE);
            n = n.subtract(ONE);
        }

        return v;
    }

    private static int lcm(int a, int b) {
        return b * a / gcd(a, b);
    }

    static int gcd(int x, int y) {
        if (x == 0) {
            return y;
        }

        if (y == 0) {
            return x;
        }

        //noinspection SuspiciousNameCombination
        return gcd(y % x, x);
    }
}

