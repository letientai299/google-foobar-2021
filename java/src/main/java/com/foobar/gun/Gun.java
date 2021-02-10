package com.foobar.gun;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Gun {
    // This is a goddamn hard problem!
    public static int solution(int[] dim, int[] me, int[] train, int d) {
        Set<Point> possibleVectors = shootVectors(dim, me, train, d);
        return possibleVectors.size();
    }

    static Set<Point> shootVectors(int[] dim, int[] me, int[] train, int d) {
        Point m = new Point(me[0], me[1]);
        Point t = new Point(train[0], train[1]);
        Point size = new Point(dim[0], dim[1]);

        // all points to aim for can be one of following formulas:
        // (a*w + t.x, b*h + t.y)
        // (a*w - t.x, b*h - t.y)
        // so, we will find the min and max of a and b
        int maxA = (d + t.x + m.x) / size.x;
        int maxB = (d + t.y + m.y) / size.y;

        Set<Point> vectors = new HashSet<>();
        Set<Point> dangerous = new HashSet<>(); // directions that can hit me

        for (int a = 0; a <= maxA / 2; a++) {
            for (int b = 0; b <= maxB / 2; b++) {
                Set<Point> potentials = makePotentialVectors(m, d, size, a, b, t, dangerous);
                vectors.addAll(potentials);
            }
        }
        return vectors;
    }

    private static Set<Point> makePotentialVectors(
            Point m, int d, Point size, int a, int b, Point t, final Set<Point> dangerous
    ) {
        Set<Point> ts = possibleTargets(size, a, b, t);
        return ts.stream()
                .map(x -> makeVector(d, m, x, size, dangerous))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    private static Point makeVector(
            int d, Point m, Point p, Point size, Set<Point> dangerous
    ) {
        int dis = p.disSquare(m);
        if (dis == 0 || dis > d * d) {
            return null;
        }

        Point v = new Point(p.x - m.x, p.y - m.y).vector();

        if (dangerous.contains(v)) {
            return null;
        }

        int w = size.x;
        int maxA = ((int) Math.ceil(Math.sqrt(dis)) + 2 * m.x) / w;
        int h = size.y;
        int maxB = ((int) Math.ceil(Math.sqrt(dis)) + 2 * m.y) / h;

        outer:
        for (int a = 0; a <= maxA / 2; a++) {
            for (int b = 0; b <= maxB / 2; b++) {
                Set<Point> ms = possibleTargets(size, a, b, m);
                for (Point c : ms) {
                    if (c.x >= 0 && c.x <= w && c.y >= 0 && c.y <= h) {
                        continue;
                    }

                    if (c.equals(p)) {
                        continue;
                    }

                    Point dv = new Point(c.x - m.x, c.y - m.y).vector();
                    if (dv.equals(v) && c.disSquare(m) <= dis) {
                        dangerous.add(v);
                        break outer;
                    }
                }
            }
        }

        if (dangerous.contains(v)) {
            return null;
        }

        return v;
    }

    private static Set<Point> possibleTargets(Point size, int a, int b, Point t) {
        Set<Point> ts = new HashSet<>();
        int w = size.x;
        int h = size.y;
        a *= 2;
        b *= 2;
        ts.add(new Point(a * w + t.x, b * h + t.y));
        ts.add(new Point(a * w - t.x, b * h + t.y));
        ts.add(new Point(a * w + t.x, b * h - t.y));
        ts.add(new Point(a * w - t.x, b * h - t.y));

        ts.add(new Point(-a * w + t.x, b * h + t.y));
        ts.add(new Point(-a * w - t.x, b * h + t.y));
        ts.add(new Point(-a * w + t.x, b * h - t.y));
        ts.add(new Point(-a * w - t.x, b * h - t.y));

        ts.add(new Point(-a * w + t.x, -b * h + t.y));
        ts.add(new Point(-a * w - t.x, -b * h + t.y));
        ts.add(new Point(-a * w + t.x, -b * h - t.y));
        ts.add(new Point(-a * w - t.x, -b * h - t.y));


        ts.add(new Point(a * w + t.x, -b * h + t.y));
        ts.add(new Point(a * w - t.x, -b * h + t.y));
        ts.add(new Point(a * w + t.x, -b * h - t.y));
        ts.add(new Point(a * w - t.x, -b * h - t.y));
        return ts;
    }

    static class Point {
        int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        int disSquare(Point other) {
            return (other.x - this.x) * (other.x - this.x) +
                    (other.y - this.y) * (other.y - this.y);
        }

        @Override
        public String toString() {
            return "(" + x + "," + y + ')';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        public Point vector() {
            int x = (this.x >= 0) ? this.x : -this.x;
            int y = (this.y >= 0) ? this.y : -this.y;
            int g = gcd(x, y);
            if (g == 0) {
                return this;
            }
            return new Point(this.x / g, this.y / g);
        }
    }

    static int gcd(int x, int y) {
        if (x == 0) {
            return y;
        }

        if (y == 0) {
            return x;
        }

        return gcd(y % x, x);
    }
}

