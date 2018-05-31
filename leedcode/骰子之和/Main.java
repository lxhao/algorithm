package 骰子之和;

import java.util.*;

public class Main {

    public static void main(String args[]) {
        int sum = 8;
        int amount = 2;
        int[] probability = new int[amount * 6 - amount + 1];
        compute(amount, amount, probability, 0);
        int total = 0;
        for (int i = 0; i < probability.length; i++) {
            total += probability[i];
            System.out.print(probability[i] + " ");
        }
        System.out.println(" ");
        System.out.println(probability[3 - amount] * 1.0 / total);
    }

    /**
     *  计算每个结果出现的次数
     *  @Param restAmount 剩余骰子
     *  @Param origAmount 最初骰子
     *  @Param probability 每一种结果出现的次数
     *  @Param sum 前面骰子的和
     *
     */
    public static void compute(int restAmount, int origAmount, int[] probability, int sum) {
        if (restAmount == 0) {
            probability[sum - origAmount]++;
            return;
        }

        for (int i = 1; i <= 6; i++) {
            compute(restAmount - 1, origAmount, probability, sum + i);
        }
    }
}

