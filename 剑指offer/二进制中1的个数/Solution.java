package 二进制中1的个数;

/**
 * 题目:
 * 输入一个整数，输出该数二进制表示中1的个数。其中负数用补码表示
 * <p>
 * 每次减一必定改变右边第一个1为0,再与上原来的值,正好去掉一个1
 * 比如10的二进制表示1010,
 * 10 - 1 = 1001, 1001 & 1010 = 1000
 * 1000 - 1 = 0111, 0111 & 1000 = 0
 */
public class Solution {
    public static void main(String[] args) {
        System.out.println(new Solution().NumberOf1(-1));
    }

    public int NumberOf1(int n) {
        int counter = 0;
        while (n != 0) {
            n &= n - 1;
            counter++;
        }
        return counter;
    }
}