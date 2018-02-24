package 圆圈;

/**
 * 链接：https://www.nowcoder.net/acm/contest/68/A
 * 来源：牛客网
 * <p>
 * 题目描述
 * 我们定义一个圆 C 为以原点 (0, 0) 为中心的单位圆(半径为 1 的圆)。给定在 C 圆周上相异的两点
 * <p>
 * A, B。请问由 A 出发，沿着圆周走到 B，是顺时针走比较近，还是逆时针走比较近呢？
 * <p>
 * C 的圆周上的所有点都可以用 (cos(t), sin(t)) 来表示，其中 t 的物理意义为角度。也就是说，在圆 C 中，给定一角度 t 即可确定在圆周上的一点。在这题中，所有的角度皆以弧度制表示，另外，由于不同的t 值有机会对应到同一个圆周上的点，我们限制t 的范围为[-π,π )。
 * <p>
 * 本题中，我们会用tA 以及tB 来代表点A 及点B，数学上，A = (cos(tA), sin(tA)), B = (cos( tB), sin(tB))。
 * <p>
 * 输入描述:
 * 输入的第一行有一个正整数T，代表接下来共有几组测试数据。
 * <p>
 * 接下来的T行，每行有两个浮点数tA, tB，代表一组数据。
 * <p>
 * 输出描述:
 * 对于每组数据请输出一行，如顺时针比较近请输出“clockwise”，否则请输出“counterclockwise”。
 * <p>
 * 示例1
 * 输入
 * 3
 * 3.14 3.13
 * -3.14 -3.13
 * 1.00 2.00
 * <p>
 * 输出
 * clockwise
 * counterclockwise
 * counterclockwise
 * 备注:
 * 1≤T≤105
 * −π≤tA,tB<π
 * A≠B
 * 输入中的浮点数精确至小数点下两位
 */

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("input.txt"));
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(in.readLine());
        while (t-- > 0) {
            String ab[] = in.readLine().split("\\s");
            double a = Double.parseDouble(ab[0]);
            double b = Double.parseDouble(ab[1]);
            if ((a > b && (a - b) >= Math.PI) || (b > a && (b - a) <= Math.PI)) {
                System.out.println("counterclockwise");
            } else {
                System.out.println("clockwise");
            }
        }
        in.close();
    }
}

