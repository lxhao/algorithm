package 股票收益;

import java.util.*;

public class Main {
    public static void main(String args[]) {
        int[] prices = {1, 2, 5};
        System.out.println(getMostProfit(prices, 3));
    }

    /**
     * @Param 股票价格
     * @Param 最大交易次数
     *
     * @Return 最大收益
     */
    public static int getMostProfit(int[] prices, int times) {
        int[][] mostProfit = new int[times + 1][prices.length];
        int max;
        for(int i = 1; i <= times; i++) {
            max = mostProfit[i - 1][0] - prices[0];
            for(int j = 1; j < prices.length; j++) {
                mostProfit[i][j] = Math.max(mostProfit[i][j - 1], max + prices[j]);
                max = mostProfit[i - 1][j] - prices[j];
            }
        }
        return mostProfit[times][prices.length - 1];
    }
}
