import java.util.HashMap;
import java.util.Map;

/**
 * Definition for a point.
 * class Point {
 * int x;
 * int y;
 * Point() { x = 0; y = 0; }
 * Point(int a, int b) { x = a; y = b; }
 * }
 */
class Point {
    int x;
    int y;

    Point() {
        x = 0;
        y = 0;
    }

    Point(int a, int b) {
        x = a;
        y = b;
    }
}

public class Solution {
    public static void main(String args[]) {
        Point[] points = new Point[3];
        points[0] = new Point(2, 2);
        points[1] = new Point(2, 2);
        points[2] = new Point(2, 2);
        System.out.println(new Solution().maxPoints(points));
    }

    public int maxPoints(Point[] points) {
        if (points == null) {
            return 0;
        }
        if (points.length <= 2) {
            return points.length;
        }
        int maxLen = 2;
        Map<Double, Integer> pointsNumber = new HashMap<>();
        for (int i = 0; i < points.length; i++) {
            pointsNumber.clear();
            int maxTmp = 1;
            //重复点
            int dump = 0;
            int ver = 1;
            for (int j = i + 1; j < points.length; j++) {
                if (i == j) {
                    continue;
                }
                if (isSamePoint(points[i], points[j])) {
                    //重合
                    dump++;
                } else if (points[i].x == points[j].x) {
                    //垂直
                    ver++;
                    maxTmp = Math.max(maxTmp, ver);
                } else {
                    double a = 1.0 * (points[i].y - points[j].y) / (points[i].x - points[j].x);
                    if (a == -0.0) {
                        a = 0.0;
                    }
                    Integer t = pointsNumber.get(a);
                    pointsNumber.put(a, t == null ? 2 : t + 1);
                    maxTmp = Math.max(maxTmp, pointsNumber.get(a));
                }
            }
            maxLen = Math.max(maxLen, maxTmp + dump);
        }
        return maxLen;
    }

    private boolean isSamePoint(Point p1, Point p2) {
        return p1.x == p2.x && p1.y == p2.y;
    }
}
