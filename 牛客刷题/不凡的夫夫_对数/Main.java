package 不凡的夫夫_对数;

import java.io.*;
import java.util.Scanner;

/**
 * 链接：https://www.nowcoder.net/acm/contest/75/A
 * 来源：牛客网
 * 题目描述 夫夫有一天对一个数有多少位数感兴趣，但是他又不想跟凡夫俗子一样，所以他想知道给一个整数n，
 * 求n！的在8进制下的位数是多少位。
 * 输入描述:第一行是一个整数t(0<t<=1000000)(表示t组数据)接下来t行，每一行有一个整数n(0<=n<=10000000)
 * 输出描述:输出n！在8进制下的位数。
 * 示例1输入
 * 3
 * 4
 * 2
 * 5
 * 输出
 * 2
 * 1
 * 3
 */

public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("input.txt"));
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(in.readLine());
        int[] result = preCompute();
        for (int i = 0; i < t; i++) {
            int n = Integer.parseInt(in.readLine());
            System.out.println(result[n]);
        }
    }

    private static int[] preCompute() {
        int[] result = new int[10000000 + 1];
        result[0] = 1;
        result[1] = 1;
        double tmp = 0.0;
        for (int i = 2; i <= 10000000; i++) {
            tmp += Math.log(i) / Math.log(8);
            result[i] = (int) Math.ceil(tmp);
        }
        return result;
    }

    private static int solve(int n) {
        if (n == 0 || n == 1) {
            return 1;
        }
        double res = 0.0;
        for (int i = 1; i <= n; i++) {
            res += Math.log(i) / Math.log(8);
        }
        return (int) Math.ceil(res);
    }
}

