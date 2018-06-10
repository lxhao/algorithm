package 网易内推堆棋子;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
/**
* 小易将n个棋子摆放在一张无限大的棋盘上。第i个棋子放在第x[i]行y[i]列。同一个格子允许放置多个棋子。
* 每一次操作小易可以把一个棋子拿起并将其移动到原格子的上、下、左、右的任意一个* 格子中。小易想知道要让棋盘上出现有一个格子中至少有i(1 ≤ i ≤ n)个棋子所需要的最少操作次数.
*/
public class Chess {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            int n = in.nextInt();
            int[] x = new int[n];
            int[] y = new int[n];

            for (int i = 0; i < n; i++) {
                x[i] = in.nextInt();
            }

            for (int i = 0; i < n; i++) {
                y[i] = in.nextInt();
            }
            List<List<Integer>> distances = new ArrayList<>();
            countDistances(x, y, distances);
            //计算至少有一个格子放i颗棋子的最小移动次数
            for (int i = 1; i <= n; i++) {
                int minMoved = Integer.MAX_VALUE;
                //计算移动i颗棋子到一个格子的最小操作次数
                for (List<Integer> disTmp : distances) {
                    int minMovedTmp = 0;
                    for (int j = 0; j < i; j++) {
                        minMovedTmp += disTmp.get(j);
                    }
                    minMoved = Math.min(minMoved, minMovedTmp);
                }
                System.out.print(minMoved);
                if(i != n) {
                    System.out.print(" ");
                }
            }
        }
    }

    /**
     * 最后的结果只可能出现在棋子横纵坐标的组合上
     * 比如两颗棋子的坐标为(1, 2)和(5, 6) 那最后的结果只可能出现在(1, 2),(1, 6),(5, 2),(5, 6)
     * 按总共候选点的个数就是n * n
     * 计算出所有棋子到每个候选点的距离,并对这个距离排序,根据计算出的距离选择最优解
     * 比如要求有一个格子有3颗棋子最小移动次数,只需要计算每个候选点放三颗棋子的移动次数,然后取最小值
     *
     * @param x
     * @param y
     * @param distances
     */
    private static void countDistances(int[] x, int[] y, List<List<Integer>> distances) {
        for (int i = 0; i < x.length; i++) {
            for (int j = 0; j < y.length; j++) {
                //(i,j)  为候选点
                //计算每颗棋子到候选点的距离,任意一颗棋子到(i,j)的移动次数为坐标相减取绝对值相加
                List<Integer> disTmp = new ArrayList<>();
                for (int k = 0; k < x.length; k++) {
                    disTmp.add(Math.abs(x[k] - x[i]) + Math.abs(y[k] - y[j]));
                }
                //对计算出的距离排序
                Collections.sort(disTmp);
                distances.add(disTmp);
            }
        }
    }
}

