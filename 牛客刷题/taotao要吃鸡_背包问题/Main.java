package taotao要吃鸡_背包问题;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        System.setIn(new FileInputStream("input.txt"));
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            int n = in.nextInt();
            if (n == 0) {
                break;
            }
            int m = in.nextInt();
            int h = in.nextInt();
            int[] costs = new int[n];
            int[] weights = new int[n];
            for (int i = 0; i < n; i++) {
                costs[i] = in.nextInt();
                weights[i] = in.nextInt();
            }
            int ans = 0;
            if (h == 0) {
                ans = getMaxWeight(costs, weights, m, -1);
            } else {
                ans = getMaxWeight(costs, weights, m + h, -1);
                for (int i = 0; i < n; i++) {
                    ans = Math.max(ans, weights[i] + getMaxWeight(costs, weights, m + h - 1, i));
                }
            }
            System.out.println(ans);
        }
        in.close();
    }

    public static int getMaxWeight(int[] costs, int[] weigths, int costSum, int black) {
        int[] dp = new int[costSum + 1];
        for (int i = 0; i < costs.length; i++) {
            if (i == black) {
                continue;
            }
            for (int j = costSum; j >= 0; j--) {
                if (j - costs[i] >= 0) {
                    dp[j] = Math.max(dp[j], dp[j - costs[i]] + weigths[i]);
                }
            }
        }
        return dp[costSum];
    }
}

