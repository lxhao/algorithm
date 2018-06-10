package 带约束k个数的最大乘积;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            System.setIn(new FileInputStream("input.txt"));
        } catch (FileNotFoundException ignored) {
        }
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] numbers = new int[n];
        for (int i = 0; i < n; i++) {
            numbers[i] = in.nextInt();
        }
        int k = in.nextInt();
        int d = in.nextInt();
        System.out.println(solve(numbers, k, d));
    }

    private static long solve(int[] numbers, int totalNum, int dis) {
        long[][] dp = new long[numbers.length + 1][totalNum + 1];
        long[][] minValue = new long[numbers.length + 1][totalNum + 1];
        for (int i = 1; i <= numbers.length; i++) {
            dp[i][1] = numbers[i - 1];
            minValue[i][1] = numbers[i - 1];
        }

        for (int t = 2; t <= totalNum; t++) {
            for (int n = t; n <= numbers.length; n++) {
                long max = Long.MIN_VALUE;
                long min = Long.MAX_VALUE;
                for (int left = Math.max(t - 1, n - dis); left <= n - 1; left++) {
                    long tmpMax = dp[left][t - 1] * numbers[n - 1];
                    long tmpMin = minValue[left][t - 1] * numbers[n - 1];
                    if (tmpMax < tmpMin) {
                        long tmp = tmpMax;
                        tmpMax = tmpMin;
                        tmpMin = tmp;
                    }
                    max = Long.max(max, tmpMax);
                    min = Long.min(min, tmpMin);
                }
                dp[n][t] = max;
                minValue[n][t] = min;
            }
        }

        long ans = Long.MIN_VALUE;
        for (int i = totalNum; i <= numbers.length; i++) {
            ans = Long.max(ans, dp[i][totalNum]);
        }
        return ans;
    }
}
