package 第四场.石油采集;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * 链接：https://www.nowcoder.com/acm/contest/76/A
 * 来源：牛客网
 * <p>
 * 题目描述
 * 随着海上运输石油泄漏的问题，一个新的有利可图的行业正在诞生，那就是撇油行业。如今，在墨西哥湾漂浮的大量石油，吸引了许多商人的目光。这些商人们有一种特殊的飞机，可以一瓢略过整个海面20米乘10米这么大的长方形。（上下相邻或者左右相邻的格子，不能斜着来）当然，这要求一瓢撇过去的全部是油，如果一瓢里面有油有水的话，那就毫无意义了，资源完全无法利用。现在，商人想要知道，在这片区域中，他可以最多得到多少瓢油。
 * <p>
 * 地图是一个N×N的网络，每个格子表示10m×10m的正方形区域，每个区域都被标示上了是油还是水
 * 输入描述:
 * 测试输入包含多条测试数据
 * 测试数据的第一行给出了测试数据的数目T（T<75）
 * 每个测试样例都用数字N（N<50）来表示地图区域的大小，接下来N行，每行都有N个字符，其中符号’.’表示海面、符号’#’表示油面。
 * 输出描述:
 * 输出格式如下“Case X: M”（X从1开始），M是商人可以最多得到的油量。
 * 示例1
 * 输入
 * 1
 * 6
 * ......
 * .##...
 * ......
 * .#..#.
 * .#..##
 * ......
 * 输出
 * Case 1: 3
 */

/**
 * 用一个二维数组记录每个位置往下和往右能取得的石油数,二维数组初始值-1
 */

public class Main {

    static class MaxValue {
        int down;
        int right;

        MaxValue() {

        }

        MaxValue(int down, int right) {
            this.down = down;
            this.right = right;
        }
    }

    public static void main(String[] args) {
        try {
            System.setIn(new FileInputStream("input.txt"));
        } catch (FileNotFoundException ignored) {
        }
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        for (int caseNo = 1; caseNo <= n; caseNo++) {
            int graghSize = in.nextInt();
            char[][] gragh = new char[graghSize][graghSize];
            for (int i = 0; i < graghSize; i++) {
                gragh[i] = in.next().toCharArray();
            }
            boolean[][] flag = new boolean[graghSize][graghSize];
            MaxValue[][] values = new MaxValue[graghSize][graghSize];
            int res = search(gragh, 0, 0, flag, values);
            System.out.printf("Case %d: %d\n", caseNo, res);
        }
        in.close();
    }

    private static int search(char[][] gragh, int x, int y, boolean[][] flag, MaxValue[][] values) {
        if (x == gragh.length) {
            x = 0;
            y += 1;
        }
        if (y == gragh.length) {
            return 0;
        }
        // 已经被选中或者当前区域是水面
        if (flag[x][y] || gragh[x][y] == '.') {
            return search(gragh, x + 1, y, flag, values);
        }
        // 已经计算出结果
        if (values[x][y] != null) {
            return Math.max(values[x][y].down, values[x][y].right);
        }

        values[x][y] = new MaxValue(0, 0);
        // 往右取
        if (x + 1 < gragh.length && gragh[x + 1][y] == '#') {
            flag[x + 1][y] = true;
            values[x][y].right = 1 + search(gragh, x + 1, y, flag, values);
            flag[x + 1][y] = false;
        }
        // 往下取
        if (y + 1 < gragh.length && gragh[x][y + 1] == '#') {
            flag[x][y + 1] = true;
            values[x][y].down = 1 + search(gragh, x + 1, y, flag, values);
            flag[x][y + 1] = false;
        }
        return Math.max(values[x][y].down, values[x][y].right);
    }
}

