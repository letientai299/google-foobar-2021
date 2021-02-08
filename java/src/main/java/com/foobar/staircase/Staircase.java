package com.foobar.staircase;


public class Staircase {
    public static int solution(int n) {
        int v = 0;
        for (int i = 1; i < (n + 1) / 2; i++) {
            v += recursive(i, n - i);
        }
        return v;
    }

    static int recursive(int low, int remain) {
        if (remain == 0) {
            return 1;
        }

        if (remain < 0) {
            return 0;
        }

        int v = 1; // for [low, remain]
        for (int i = low + 1; i < (remain + 1) / 2; i++) {
            v += recursive(i, remain - i);
        }
        return v;
    }
}
