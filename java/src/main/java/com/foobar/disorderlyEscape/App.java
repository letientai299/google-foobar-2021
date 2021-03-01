package com.foobar.disorderlyEscape;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciitable.CWC_LongestWord;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static com.foobar.disorderlyEscape.DisorderLyEscape.factorial;
import static com.foobar.disorderlyEscape.DisorderLyEscape.solution;
import static java.lang.Math.E;
import static java.math.BigInteger.valueOf;

public class App {
    private final static Logger log = LoggerFactory.getLogger(App.class.getName());

    public static void main(String[] args) {
        // Some attempts to find the magic fomula.
        // printResultTable(10, 10, 2);
        // printResultTable(10, 10, 3);
        // printResultTable(10, 10, 4);
        // followS(2, 20);

        bench();
    }

    private static void bench() {
        int W = 15;
        int H = 15;
        int S = 5;
        for (int w = 0; w < W; w++) {
            for (int h = 0; h < H; h++) {
                for (int s = 0; s < S; s++) {
                    solution(w, h, s);
                }
            }
        }
    }

    private static void followS(int start, int end) {
        AsciiTable at = new AsciiTable();
        int w = 4;
        int h = 3;

        at.addRule();
        at.addRow("s", "res", "other", "exp");
        at.addRule();


        for (int s = start; s <= end; s++) {
            String r = solution(w, h, s);
            BigInteger res = new BigInteger(r);
            double lnr = Math.log(res.doubleValue()) / Math.log(s);
            double exp = Math.pow(s, lnr);
            at.addRow(s, r, lnr, near(exp));
            at.addRule();
        }

        log.info("Result table for (w,h,s)=({},{},[{},{}])", w, h, start, end);
        log.info("\n{}", at.render());
    }

    private static void printResultTable(int w, int h, int s) {
        AsciiTable at = new AsciiTable();
        at.addRule();

        List<String> row = new ArrayList<>();
        row.add("w=0/h=0");
        for (int i = 1; i <= w; i++) {
            row.add(String.format("w=%d", i));
        }
        at.addRow(row).setTextAlignment(TextAlignment.RIGHT);
        at.addRule();

        for (int i = 1; i <= h; i++) {
            row.clear();
            row.add(String.format("h=%d", i));
            for (int j = 1; j <= w; j++) {
                String res = solution(j, i, s);
                BigInteger b = new BigInteger(res);
                BigInteger f = factorial(valueOf(i)).multiply(factorial(valueOf(j)));
                b = b.multiply(f);
                double d = Math.log(b.doubleValue()) / Math.log(s);
                long guess = near(Math.pow(E, s));
                row.add(String.format("%s, near=%s, ceil=%s, floor=%s",
                        res, near(d), ceil(d), floor(d)));
            }
            at.addRow(row).setTextAlignment(TextAlignment.RIGHT);
            at.addRule();
        }

        at.getRenderer().setCWC(new CWC_LongestWord());

        log.info("Result table for (w,h,s)=({},{},{})", w, h, s);
        log.info("\n{}", at.render());
    }

    static long floor(double d) {
        return (long) Math.floor(d);
    }

    static long ceil(double d) {
        return (long) Math.ceil(d);
    }


    static long near(double d) {
        d += 0.5;
        return (long) Math.floor(d);
    }
}

