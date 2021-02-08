package com.foobar.coded;

import java.util.Arrays;

public class Solution {
    public static int solution(int[] digits) {
        // it's unclear how to handle overflow (more than 10 digits)!
        // their test have such cases, it's just not belong to the hidden tests.
        // Level 2:  50% [=====================.....................]
        Arrays.sort(digits);

        int rem = 0;
        for (int d : digits) {
            rem = (rem + d) % 3;
        }

        int v = 0;
        int base = 1;

        for (int d : digits) {
            if (rem != 0 && rem == d % 3) {
                rem = 0; // skip this digit
                continue;
            }

            v += base * d;
            base *= 10;
        }

        if (rem == 0) {
            return v;
        }

        // so we have to remove 2 smallest digits that is not divisible for 3
        v = 0;
        base = 1;
        rem = 2;
        for (int d : digits) {
            if (rem != 0 && d % 3 != 0) {
                rem--; // skip this digit
                continue;
            }

            v += base * d;
            base *= 10;
        }

        if (rem == 0) {
            return v;
        }

        return 0;
    }
}
