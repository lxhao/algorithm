package 分糖果;

import java.util.Arrays;

public class Solution {
    public int candy(int[] ratings) {
        int[] candyArr = new int[ratings.length];
        candyArr[0] = 1;
        for (int i = 1; i < ratings.length; i++) {
            candyArr[i] = 1;
            if (ratings[i] > ratings[i - 1]) {
                candyArr[i] = candyArr[i - 1] + 1;
            }
        }

        int ans = candyArr[candyArr.length - 1];
        for (int i = ratings.length - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1] && candyArr[i] <= candyArr[i + 1]) {
                candyArr[i] = candyArr[i + 1] + 1;
            }
            ans = Math.min(ans, candyArr[i]);
        }
        return Arrays.stream(candyArr).sum();
    }
}