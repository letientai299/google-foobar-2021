package com.foobar.bomb;

import java.math.BigInteger;

public class Bomb {
    public static String solution(String x, String y) {
        BigInteger m = new BigInteger(x);
        BigInteger f = new BigInteger(y);
        BigInteger zero = BigInteger.ZERO;
        BigInteger one = BigInteger.ONE;

        if (m.compareTo(f) > 0) {
            BigInteger t = f;
            f = m;
            m = t;
        }

        if (m.compareTo(one) == 0) {
            return f.subtract(one).toString();
        }


        BigInteger gen = zero;
        while (!m.equals(one) && !m.equals(zero) && !m.equals(f)) {
            if (m.compareTo(f) == 0) {
                break;
            }

            BigInteger[] bis = f.divideAndRemainder(m);
            gen = gen.add(bis[0]);
            f = m;
            m = bis[1];
        }

        if (m.compareTo(one) != 0) {
            return "impossible";
        }

        return gen.add(f.subtract(m)).toString();
    }
}

