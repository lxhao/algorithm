import java.util.Arrays;
import java.util.Scanner;
/**
 *  小易非常喜欢拥有以下性质的数列:
 *  1、数列的长度为n
 *  2、数列中的每个数都在1到k之间(包括1和k)
 *  3、对于位置相邻的两个数A和B(A在B前),都满足(A <= B)或(A mod B != 0)(满足其一即可)
 *  例如,当n = 4, k = 7
 *  那么{1,7,7,2},它的长度是4,所有数字也在1到7范围内,并且满足第三条性质,所以小易是喜欢这个数列的
 *  但是小易不喜欢{4,4,4,2}这个数列。小易给出n和k,希望你能帮他求出有多少个是他会喜欢的数列。*
 */

public class SeriesOfNetEase {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            int n = in.nextInt();
            int k = in.nextInt();
            System.out.println(solve(n, k));
        }
    }

    private static int solve(int n, int k) {
        int MAX_VALUE = 1000000007;
        //表示第n个数字以k结尾的组合数
        int[][] dp = new int[n][k + 1];
        //数字长度为i-1的总组合数
        int lastSum = k;
        //数字长度为i的的总组合数
        int nowSum = 0;
        //长度为1时可以以任意数字结尾,组合数为1
        Arrays.fill(dp[0], 1);
        for (int i = 1; i < n; i++) {
            nowSum = 0;
            for (int j = 1; j <= k; j++) {
                dp[i][j] = (dp[i][j] + lastSum) % MAX_VALUE;
                /**
                 * 减去上一个数子除以当前数字为0的情况
                 * j == 1时需要计算n次
                 * j == 2时需要计算n / 2次
                 * j == 3时需要计算n / 3次
                 * ....
                 * 求大神指点这个复杂度怎么算
                 */
                for (int z = j + j; z <= k; z += j) {
                    dp[i][j] = (dp[i][j] - dp[i - 1][z] + MAX_VALUE) % MAX_VALUE;
                }
                nowSum = (dp[i][j] + nowSum) % MAX_VALUE;
            }
            lastSum = nowSum;
        }
        return nowSum;
    }
}
