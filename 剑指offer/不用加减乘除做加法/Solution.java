package 不用加减乘除做加法;

/**
 * 题目描述
 * 写一个函数，求两个整数之和，要求在函数体内不得使用+、-、*、/四则运算符号。
 */
public class Solution {
    public int Add(int num1, int num2) {
        while (num2 != 0) {
            int tmp = num1;
            //不需要进位的二进制位
            num1 = num1 ^ num2;
            //需要进位的二进制位
            num2 = tmp & num2;
            //进位
            num2 = num2 << 1;
        }
        return num1;
    }

    public static void main(String[] args) {
        System.out.println(new Solution().Add(1, 2));
    }
}