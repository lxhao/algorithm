import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Problem2 {

    public static void main(String args[]) {
        int sumMoney = 1;
        int[] proCosts = {1, 2, 3, 4, 3};
        int[] proProfits = {1, 2, 3, 4, 5};
        System.out.println(computeMaxProfit(sumMoney, proProfits, proCosts));
    }

    private static int computeMaxProfit(int sumMoney, int[] proProfits, int[] proCosts) {
        if(sumMoney < 0 || proProfits == null || proCosts == null) {
            throw new IllegalArgumentException();
        }
        if(proProfits.length !=  proCosts.length) {
            throw new IllegalArgumentException();
        }
        int[] dp = new int[sumMoney + 1];
        for (int j = 0; j < proProfits.length; j++) {
            for (int i = sumMoney; i >= 1; i--) {
                //选一个
                if (i - proCosts[j] >= 0) {
                    dp[i] = Math.max(dp[i], dp[i - proCosts[j]] + proProfits[j]);
                }
                //选两个
                if (i - 2 * proCosts[j] >= 0) {
                    dp[i] = Math.max(dp[i], dp[i - 2 * proCosts[j]] + proProfits[j] * 2);
                }
            }
        }
        return dp[sumMoney];
    }
}
