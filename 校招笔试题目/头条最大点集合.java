import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

public class 头条最大点集合 {
    static class Point implements Comparable<Point> {
        int x;
        int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int compareTo(Point o) {
            return this.y - o.y;
        }
    }

    public static void main(String[] args) {
//        Scanner in = getScanner(System.in);
        Scanner in = getScanner("input.txt");
        while (in.hasNext()) {
            int n = in.nextInt();
            Point[] points = new Point[n];
            for (int i = 0; i < n; i++) {
                points[i] = new Point(in.nextInt(), in.nextInt());
            }
            List<Point> res = solve(points);
            for (Point e : res) {
                System.out.println(e.x + " " + e.y);
            }
        }
        in.close();
    }

    private static List<Point> solve(Point[] points) {
        Arrays.sort(points, new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                return o1.x - o2.x;
            }
        });
        Point[] pointsSortByY = Arrays.copyOf(points, points.length);
        Arrays.sort(pointsSortByY);

        List<Point> res = new ArrayList<>();
        for (int i = 0; i < points.length - 1; i++) {
            Point e = points[i];
            int pos = Arrays.binarySearch(pointsSortByY, e);
            for (pos++; pos < pointsSortByY.length; pos++) {
                if (pointsSortByY[pos].x >= e.x) {
                    break;
                }
            }
            if (pos == pointsSortByY.length) {
                res.add(e);
            }
        }
        res.add(points[points.length - 1]);
        return res;
    }

    //从输入流读取输入数据
    public static Scanner getScanner(InputStream is) {
        return new Scanner(is);
    }

    //从文件读取输入数据
    public static Scanner getScanner(String fileName) {
        try {
            return getScanner(new FileInputStream(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}

