package com.foobar.gun;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Gun {
    // This is a goddamn hard problem! Even though I an think of the reflection
    // idea, I can't prove and reduce from infinite to finite.
    //
    // From this article, we know that there's maximum 16 points to shoot.
    // https://amininima.wordpress.com/2013/05/27/the-laser-gun/
    //
    // Now the problems ares remove duplicated target, calculate the distance
    // that the beam should travel and remove out of range targets.
    //
    // I guess (but can't prove yet) that we can just use calculate the travel
    // distance to those 1+15 reflections in following picture:
    // https://amininima.files.wordpress.com/2013/05/lasergunguards1.png
    // But these 16 points are not asymetric. So we need to make it 25 to be
    // sure!
    public static int solution(int[] dim, int[] me, int[] train, int d) {
        Map<Point, Integer> hitTrain = solve(dim, me, train, d);
        Map<Point, Integer> hitMe = solve(dim, me, me, d);
        int res = 0;
        for (Map.Entry<Point, Integer> e : hitTrain.entrySet()) {
            Point p = e.getKey();
            int dis = e.getValue();
            if (hitMe.containsKey(p) && hitMe.get(p) <= dis) {
                continue;
            }
            res++;
        }
        return res;
    }

    private static Map<Point, Integer> solve(int[] dim, int[] me, int[] train, int d) {
        // { point -> distance square}
        Point m = new Point(me[0], me[1]);
        Point t = new Point(train[0], train[1]);

        int w = dim[0];
        int h = dim[1];

        Map<Point, Integer> trainTargets = buildTargets(m, t, w, h);
        d *= d;

        Map<Point, Integer> vectors = new HashMap<>();
        int dis = t.disSquare(m);
        if (dis != 0 && dis <= d) {
            Point v = new Point(t.x - m.x, t.y - m.y).vector();
            vectors.put(v, dis);
        }

        for (Map.Entry<Point, Integer> entry : trainTargets.entrySet()) {
            Point p = entry.getKey();
            dis = entry.getValue();
            if (dis == 0 || dis > d) {
                continue;
            }

            Point v = new Point(p.x - m.x, p.y - m.y).vector();
            vectors.put(v, dis);
        }

        return vectors;
    }

    private static Map<Point, Integer> buildTargets(Point m, Point t, int w, int h) {
        // first target is always unique
        Map<Point, Integer> targets = new HashMap<>();
        targets.put(t, t.disSquare(m));

        // 4 reflections against the edges
        Point r = new Point(t.x, h + h - t.y); // top edge
        targets.putIfAbsent(r, r.disSquare(m));
        r = new Point(t.x, -t.y); // bottom edge
        targets.putIfAbsent(r, r.disSquare(m));
        r = new Point(-t.x, t.y); // left edge
        targets.putIfAbsent(r, r.disSquare(m));
        r = new Point(w + w - t.x, t.y); // right edge
        targets.putIfAbsent(r, r.disSquare(m));

        // 4 reflections against the corners
        r = new Point(-t.x, -t.y); // bottom-left
        targets.putIfAbsent(r, r.disSquare(m));
        r = new Point(-t.x, h + h - t.y); // top-left
        targets.putIfAbsent(r, r.disSquare(m));
        r = new Point(w + w - t.x, -t.y); // bottom-right
        targets.putIfAbsent(r, r.disSquare(m));
        r = new Point(w + w - t.x, h + h - t.y); // top-right
        targets.putIfAbsent(r, r.disSquare(m));

        // 5 reflections on top
        r = new Point(-2 * w + t.x, 2 * h + t.y);
        targets.putIfAbsent(r, r.disSquare(m));
        r = new Point(-t.x, 2 * h + t.y);
        targets.putIfAbsent(r, r.disSquare(m));
        r = new Point(t.x, 2 * h + t.y);
        targets.putIfAbsent(r, r.disSquare(m));
        r = new Point(2 * w - t.x, 2 * h + t.y);
        targets.putIfAbsent(r, r.disSquare(m));
        r = new Point(2 * w + t.x, 2 * h + t.y);
        targets.putIfAbsent(r, r.disSquare(m));

        // 5 reflections at bottom
        r = new Point(t.x, -2 * h + t.y);
        targets.putIfAbsent(r, r.disSquare(m));
        r = new Point(-t.x, -2 * h + t.y);
        targets.putIfAbsent(r, r.disSquare(m));
        r = new Point(-2 * w + t.x, -2 * h + t.y);
        targets.putIfAbsent(r, r.disSquare(m));
        r = new Point(2 * w + t.x, -2 * h + t.y);
        targets.putIfAbsent(r, r.disSquare(m));
        r = new Point(2 * w - t.x, -2 * h + t.y);
        targets.putIfAbsent(r, r.disSquare(m));

        // 3 additional reflections to the right
        r = new Point(2 * w + t.x, 2 * h - t.y);
        targets.putIfAbsent(r, r.disSquare(m));
        r = new Point(2 * w + t.x, t.y);
        targets.putIfAbsent(r, r.disSquare(m));
        r = new Point(2 * w + t.x, -t.y);
        targets.putIfAbsent(r, r.disSquare(m));

        // 3 additional reflections to the left
        r = new Point(-2 * w + t.x, 2 * h - t.y);
        targets.putIfAbsent(r, r.disSquare(m));
        r = new Point(-2 * w + t.x, t.y);
        targets.putIfAbsent(r, r.disSquare(m));
        r = new Point(-2 * w + t.x, -t.y);
        targets.putIfAbsent(r, r.disSquare(m));
        return targets;
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
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
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
                System.out.println("ops");
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

