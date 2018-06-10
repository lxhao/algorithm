package 背包问题;

import java.util.*;

public class Main {
    public static void main(String args[]) {
        int volume[] = {12, 2, 1, 4, 1 };
        int value[] = {4, 2, 2, 10, 1};
        println("物品体积:");
        println(volume);
        println("物品价值:");
        println(value);

        int size = value.length;
        int packageVol = 8;
        int dp[] = new int[packageVol + 1];

        for(int i = 1; i <= packageVol; i++) {
            int maxValue = dp[i - 1];
            for(int j = 0; j < size; j++) {
                //在背包装上j号物品
                if(i - volume[j] >= 0) {
                    maxValue = Math.max(dp[i - volume[j]] + value[j], maxValue);
                }
            }
            dp[i] = maxValue;
        }
        println(dp);
    }

    public static void println(Object o) {
            System.out.println(o);
    }

    public static void print(Object o) {
            System.out.print(o);
    }

    public static void println(int[] arr) {
        for(int i = 0; i < arr.length; i++) {
            System.out.printf("%-2d ", arr[i]);
        }
        println("");
    }
}

