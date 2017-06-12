import java.util.*;

/**
 *
 *
 */
public class Main {
    public static void main(String args[]) {
        int N = 20161006;
        //保存计算过的结果
        int[] countedNum = new int[N + 1];
        //保留第n个数的前一个数
        int[] res = new int[N + 1];
        System.out.println(minOperationTimes(N, countedNum, res));
        for(int i = N; i > 1; i = res[i]) {
            System.out.printf("%d\t", res[i]);
        }
    }

    public static int minOperationTimes(int n, int[] countedNum, int[] res) {
        if (countedNum[n] != 0) {
            return countedNum[n];
        }
        if (n == 1) {
            return 0;
        }
        int t;
        //偶数
        if (n % 2 == 0) {
            res[n] = n / 2;
            t = minOperationTimes(n / 2, countedNum, res) + 1;
        //奇数
        } else {
            res[n] = n - 1;
            t = minOperationTimes(n - 1, countedNum, res) + 1;
        }
        countedNum[n] = t;
        return countedNum[n];
    }
}
