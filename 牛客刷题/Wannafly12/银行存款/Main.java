package Wannafly12.银行存款;

import java.util.*;

public class Main {
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int year = in.nextInt();
        double r1 = in.nextDouble();
        double r2 = in.nextDouble();
        double r3 = in.nextDouble();
        double r5 = in.nextDouble();
        double[] dp = new double[year + 1];
        dp[0] = 1;
        for (int i = 1; i <= year; i++) {
            dp[i] = dp[i - 1] * Math.pow((1 + r1), 1);
            if (i - 2 >= 0) {
                dp[i] = Math.max(dp[i], dp[i - 2] * Math.pow((1 + r2), 2));
            }
            if (i - 3 >= 0) {
                dp[i] = Math.max(dp[i], dp[i - 3] * Math.pow((1 + r3), 3));
            }
            if (i - 5 >= 0) {
                dp[i] = Math.max(dp[i], dp[i - 5] * Math.pow((1 + r5), 5));
            }
        }
        System.out.printf("%.5f\n", dp[year]);
    }
}