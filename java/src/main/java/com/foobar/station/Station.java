package com.foobar.station;

import java.util.HashMap;
import java.util.Map;

public class Station {
    public static int[] solution(int[] l, int t) {
        Map<Integer, Integer> tmp = new HashMap<>();
        int s = 0;
        tmp.put(0, -1);
        for (int i = 0; i < l.length; i++) {
            int v = l[i];
            s += v;
            if (s < t) {
                tmp.put(s, i);
                continue;
            }

            if (s == t) {
                return new int[]{0, i};
            }

            Integer x = tmp.getOrDefault(s - t, -1);
            if (x != -1) {
                return new int[]{x+1, i};
            }

            tmp.put(s, i);
        }
        return new int[]{-1, -1};
    }
}
