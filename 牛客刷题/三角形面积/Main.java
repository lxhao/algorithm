package 三角形面积;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    static class Point {
        int x;
        int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private double pointDistance(Point p1, Point p2) {
        double distance = 0;
        distance = Math.sqrt((p1.y - p2.y) * (p1.y - p2.y) + (p1.x - p2.x) * (p1.x - p2.x));
        return distance;
    }

    private Double getArea(Point p1, Point p2, Point p3) {
        double area;
        double a, b, c, s;
        a = pointDistance(p1, p2);
        b = pointDistance(p2, p3);
        c = pointDistance(p1, p3);
        s = 0.5 * (a + b + c);
        area = Math.sqrt(s * (s - a) * (s - b) * (s - c));
        return area;
    }

    private int getPoint(Point p1, Point p2) {
        int x1 = p1.x;
        int y1 = p1.y;
        int x2 = p2.x;
        int y2 = p2.y;
        int startX = p1.x;
        int endX = p2.x;
        if (endX < startX) {
            int t = startX;
            startX = endX;
            endX = startX;
        }
        int res = 0;
        if (x1 == x2) {
            return Math.abs(y1 - y2) - 2;
        }
        for (int x = startX + 1; x < endX; x++) {
            double y = (x - x2) / (x1 - x2) * (y1 - y2) + y2;
            if (y % 1 == 0) {
                res++;
            }
        }
        return res;
    }

    public static void main(String[] args) throws FileNotFoundException {
        System.setIn(new FileInputStream("input.txt"));
        Scanner in = new Scanner(System.in);
        Main main = new Main();
        while (in.hasNext()) {
            int x = in.nextInt();
            if (x == -1) {
                break;
            }
            int y = in.nextInt();
            Point p1 = new Point(x, y);
            Point p2 = new Point(in.nextInt(), in.nextInt());
            Point p3 = new Point(in.nextInt(), in.nextInt());

            Double area = main.getArea(p1, p2, p3);
            System.out.printf("%.1f\n", area);
            System.out.printf("%d\n", main.getPoint(p1, p2));
            System.out.printf("%d\n", main.getPoint(p1, p3));
            System.out.printf("%d\n", main.getPoint(p2, p3));
        }
        in.close();
    }
}

