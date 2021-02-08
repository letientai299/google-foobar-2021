package com.foobar.minion;

import java.util.HashMap;
import java.util.Map;

public class Solution {
    public static int[] solution(int[] data, int n) {
        int removed = 0;
        Map<Integer, Integer> freq = new HashMap<>();
        for (int v : data) {
            Integer cur = freq.getOrDefault(v, 0);
            cur++;
            freq.put(v, cur);
            if (cur > n + 1) {
                removed ++;
            } else if (cur == n + 1) {
                removed += cur;
            }
        }

        int[] res = new int[data.length - removed];
        int j = 0;
        for (int i = 0; i < data.length; i++) {
            int v = data[i];
            int f = freq.get(v);
            if (f > n) {
                continue;
            }
            res[j] = data[i];
            j++;
        }

        return res;
    }
}
