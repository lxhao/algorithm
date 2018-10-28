package 平面内同一直线上的最大点数;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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
    private float getSlope(Point p1, Point p2) {
        if (p1.y == p2.y) {
            return Float.MAX_VALUE;
        }
        if (p1.x == p2.x) {
            return 0.0f;
        }
        return (p1.x - p2.x) * 1.0f / (p1.y - p2.y);
    }

    public int maxPoints(Point[] points) {
        if (points == null) {
            return 0;
        }
        if (points.length <= 2) {
            return points.length;
        }
        int ans = 2;
        for (int i = 0; i < points.length - 1; i++) {
            Map<Float, Integer> pointsMap = new HashMap<>();
            int samePoint = 0;
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].x == points[j].x && points[i].y == points[j].y) {
                    samePoint += 1;
                    continue;
                }
                float slope = getSlope(points[i], points[j]);
                pointsMap.put(slope, pointsMap.getOrDefault(slope, 1) + 1);
            }
            ans = Math.max((pointsMap.size() > 0 ? Collections.max(pointsMap.values()) : 1) + samePoint, ans);
        }
        return ans;
    }

    public static void main(String[] args) {
        Point p1 = new Point(4, 0);
        Point p2 = new Point(4, -1);
        Point p3 = new Point(4, 5);
        Point[] points = new Point[]{p1, p2, p3};
        System.out.println(new Solution().maxPoints(points));
    }
}