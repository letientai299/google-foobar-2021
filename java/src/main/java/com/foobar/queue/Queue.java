package com.foobar.queue;

public class Queue {
    public static int solution(int start, int length) {
        int v = 0;
        int n = length;
        int x = start;
        while (n > 0) {
            for (int i = 0; i < n; i++) {
                v ^= x;
                x++;
            }
            x += length - n;
            n--;
        }
        return v;
    }
}
