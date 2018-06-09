package 美团CodeM资格赛;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * 题目描述
 * 美团在吃喝玩乐等很多方面都给大家提供了便利。最近又增加了一项新业务：小象生鲜。这是新零售超市，你既可以在线下超市门店选购生鲜食品，也可以在手机App上下单，最快30分钟就配送到家。
 * 新店开张免不了大优惠。我们要在小象生鲜超市里采购n个物品，每个物品价格为ai，有一些物品可以选择八折优惠（称为特价优惠）。
 * 有m种满减优惠方式，满减优惠方式只有在所有物品都不选择特价优惠时才能使用，且最多只可以选择最多一款。
 * 每种满减优惠描述为(bi,ci)，即满bi减ci（当消费>=bi时优惠ci）。
 * 求要买齐这n个物品（必须一单买齐），至少需要多少钱（保留两位小数）。
 * 输入描述:
 * 第一行，两个整数n,m。
 * 接下来n行，每行一个正整数ai，以及一个0/1表示是否可以选择特价优惠（1表示可以）。
 * 接下来m行，每行两个正整数bi,ci，描述一款满减优惠。
 * <p>
 * 1 <= n,m <=10
 * 1 <= ai <= 100
 * 1 <= ci < bi <= 1000
 * 输出描述:
 * 一行一个实数，表示至少需要消耗的钱数（保留恰好两位小数）。
 * 示例1
 * 输入
 * 2 1
 * 6 1
 * 10 1
 * 12 2
 * 输出
 * 12.80
 * 示例2
 * 输入
 * 2 2
 * 6 1
 * 10 1
 * 5 1
 * 16 6
 * 输出
 * 10.00
 */

public class 下单 {
    public static void main(String[] args) {
        try {
            System.setIn(new FileInputStream("input.txt"));
        } catch (FileNotFoundException ignored) {
        }
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        double costHasCoupon = 0;
        int totalPrice = 0;
        for (int i = 0; i < n; i++) {
            int price = in.nextInt();
            totalPrice += price;
            if (in.nextInt() == 1) {
                costHasCoupon += price * 0.8;
            } else {
                costHasCoupon += price;
            }
        }

        double bestStrategy = costHasCoupon;
        for (int i = 0; i < m; i++) {
            int total = in.nextInt();
            int reduce = in.nextInt();
            if (totalPrice >= total && totalPrice - reduce < bestStrategy) {
                bestStrategy = totalPrice - reduce;
            }
        }
        System.out.printf("%.2f\n", bestStrategy);

    }

}
