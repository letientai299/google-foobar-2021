package com.foobar.minion;

import java.util.HashMap;
import java.util.Map;

public class Solution {
    public static int[] solution(int[] data, int n) {
//        System.out.printf("%s, %d\n", Arrays.toString(data), n);
        Map<Integer, Integer> freq = new HashMap<>();
        for (int v : data) {
            Integer cur = freq.getOrDefault(v, 0);
            cur++;
            freq.put(v, cur);
        }

        int end = 0;
        for (int i = 0; i < data.length; i++) {
            int v = data[i];
            int f = freq.get(v);
            if (f > n) {
                continue;
            }
            data[end] = data[i];
            end++;
        }

        for (int i = end; i < data.length; i++) {
           data[i] = 0;
        }

        return data;
    }
}
