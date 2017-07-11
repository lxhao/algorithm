import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
/**
*   动态规划 填表dp[h][n+1],h代表有h种硬币coins[]={1,5,10,20,50,100}  n+1代表要拼目标为0-n
*
*   递推公式 :dp[i][j]=dp[i][j]+dp[i-1][j-k*coins[i]],其中k[0,n/coins[i]].
*
*   解释：使用前i种钱币拼凑面值为j的方法数dp[i][j]= 
*      使用前i-1种钱币，使用k个第i种钱币，拼凑面值为j的方法数，
*      即使用前i-1种钱币拼凑面值为j的方法数dp[i-1][j-k*coins[i]]
*
*   初始化：
*      上表的第一行dp[0]均为1，表示任意目标，只由面值为1的硬币拼凑，拼凑方法为1；
 *
 */

public class Main {
    public static void main(String[] args) {
        Scanner in = getScanner(System.in);
//        Scanner in = getScanner("input.txt");
        int[] currencyValue = {1, 5, 10, 20, 50, 100};
        while (in.hasNext()) {
            int sum = in.nextInt();
            long res = solution(currencyValue, sum);
            System.out.println(res);
        }
    }

    private static long solution(int[] currencyValue, int sum) {
        long[][] res = new long[currencyValue.length][sum + 1];
        Arrays.fill(res[0], 1);
        for (int curIdx = 1; curIdx < currencyValue.length; curIdx++) {
            for (int sumIdx = 0; sumIdx <= sum; sumIdx++) {
                int stop = sumIdx / currencyValue[curIdx];
                for (int k = 0; k <= stop; k++) {
                    res[curIdx][sumIdx] += res[curIdx - 1][sumIdx - k * currencyValue[curIdx]];
                }
            }
        }
        return res[currencyValue.length - 1][sum];
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
