package com.foobar.escape;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Escape {
    public static int solution(int[] enters, int[] exits, int[][] path) {
        int m = path.length;
        int n = path[0].length;

        Set<Integer> in = new HashSet<>();
        for (int e : enters) {
            in.add(e);
        }

        Set<Integer> out = new HashSet<>();
        for (int e : exits) {
            out.add(e);
        }

        int[][] cap = new int[m][n];
        for (int r = 0; r < m; r++) {
            cap[r] = Arrays.copyOf(path[r], n);
            if (in.contains(r)) {
                continue;
            }
            if (out.contains(r)) {
                for (int c = 0; c < n; c++) {
                    cap[r][c] = Integer.MAX_VALUE;
                    path[r][c] = 0;
                }
                continue;
            }

            for (int c = 0; c < n; c++) {
                path[r][c] = 0;
            }
        }

        int total = 0;

        Set<Integer> queue = in;
        while (queue.size() != 0) {
            Set<Integer> next = new HashSet<>();
            for (Integer r : queue) {
                boolean atExit = out.contains(r);

                for (int i = 0; i < n; i++) { // possible next rooms
                    int bun = path[r][i];
                    if (in.contains(i) || bun == 0) {
                        // never go back to entrances,
                        // or skip if there's no bunny group
                        continue;
                    }

                    if (atExit) {
                        total += bun;
                        path[r][i] = 0;
                        continue;
                    }

                    // check cap of possible next rooms
                    for (int j = 0; j < n && bun > 0; j++) {
                        if (!out.contains(j) && (j == i || in.contains(j))) {
                            continue;
                        }

                        int cur = path[i][j];
                        int max = cap[i][j];
                        if (max == cur) {
                            continue;
                        }

                        if (bun <= max - cur) {
                            path[i][j] += bun;
                            bun = 0;
                        } else {
                            path[i][j] = max;
                            bun -= max - cur;
                        }

                        if (path[i][j] > 0) {
                            next.add(i);
                        }
                    }
                }
            }
            queue = next;
        }

        return total;
    }
}

