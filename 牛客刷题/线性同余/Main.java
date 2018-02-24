package 线性同余;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

/**
 * 链接：https://www.nowcoder.net/acm/contest/75/B
 * 来源：牛客网
 * <p>
 * 题目描述
 * uu遇到了一个小问题，可是他不想答。你能替他解决这个问题吗？
 * 问题：给你k对a和r是否存在一个正整数x使每队a和r都满足：x mod a=r，求最小正解x或无解。
 * <p>
 * <p>
 * 输入描述:
 * 第一行是正整数k(k<=100000)
 * 接下来k行，每行有俩个正整数a，r(100000>a>r>=0)
 * 输出描述:
 * 在每个测试用例输出非负整数m，占一行。
 * 如果有多个可能的值，输出最小的值。
 * 如果没有可能的值，则输出-1。
 * <p>
 * 示例1
 * <p>
 * 输入
 * 2
 * 8 7
 * 11 9
 * <p>
 * 输出
 * 31
 */
public class Main {
    static class Point {
        int x;
        int y;
    }

    public static void main(String[] args) throws FileNotFoundException {
        System.setIn(new FileInputStream("input.txt"));
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m[] = new int[n];
        int rest[] = new int[n];
        for (int i = 0; i < n; i++) {
            m[i] = in.nextInt();
            rest[i] = in.nextInt();
        }
        System.out.println(solve(m, rest));
        in.close();
    }

    private static void extGcd(long a, int b, Point res) {
        if (b == 0) {
            res.x = 1;
            res.y = 0;
            return;
        }
        extGcd(b, (int) (a % b), res);
        res.x = res.y;
        res.y = (int) (res.x - a / b * res.y);
    }

    private static long solve(int m[], int rest[]) {
        long sum = 0;
        for (int e : m) {
            sum += e;
        }
        int t;
        long result = 0;
        for (int i = 0; i < m.length; i++) {
            long M = sum - m[i];
            Point point = new Point();
            extGcd(M, m[i], point);
            result += (M * point.x * rest[i]) % sum;
        }
        return result;
    }
}

