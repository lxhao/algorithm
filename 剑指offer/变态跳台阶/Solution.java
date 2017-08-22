package 变态跳台阶;

/**
 * 题目:
 * <p>
 * 一只青蛙一次可以跳上1级台阶，也可以跳上2级……它也可以跳上n级。
 * 求该青蛙跳上一个n级的台阶总共有多少种跳法。
 * <p>
 * 第n阶可以从n-1阶到第1阶跳上来,也可以直接从第0阶跳到第n阶
 * f(n) = f(n - 1) + f(n - 2) + ... + f(2) + f(1) + 1
 * f(n - 1) = f(n - 2) + ... + f(2) + f(1) + 1
 * f(n) = 2 * f(n - 1)
 * f(n) = pow(2, n - 1)
 */
public class Solution {
    public int JumpFloorII(int target) {
        if (target <= 0) {
            return 0;
        }
        return (int) Math.pow(2, target - 1);
    }
}