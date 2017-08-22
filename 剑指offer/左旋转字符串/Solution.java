package 左旋转字符串;

/**
 * 题目描述
 * 汇编语言中有一种移位指令叫做循环左移（ROL），现在有个简单的任务，就是用字符串模拟这个指令的运算结果。
 * 对于一个给定的字符序列S，请你把其循环左移K位后的序列输出。例如，字符序列S=”abcXYZdef”,
 * 要求输出循环左移3位后的结果，即“XYZdefabc”。是不是很简单？OK，搞定它！
 */
public class Solution {
    public static void main(String[] args) {
        System.out.println(new Solution().LeftRotateString("abcXYZdef", 3));
    }

    public String LeftRotateString(String str, int n) {
        if (str == null || str.length() == 0 || n == 0) {
            return str;
        }
        n %= str.length();
        char[] charArr = str.toCharArray();
        reverse(charArr, 0, n - 1);
        reverse(charArr, n, str.length() - 1);
        reverse(charArr, 0, str.length() - 1);
        return new String(charArr);
    }

    private void reverse(char[] charArr, int start, int end) {
        if (end < start) {
            return;
        }
        for (int i = start; i <= (start + end) / 2; i++) {
            swap(charArr, i, end + start - i);
        }
    }

    private void swap(char[] charArr, int p1, int p2) {
        char tmp = charArr[p1];
        charArr[p1] = charArr[p2];
        charArr[p2] = tmp;
    }
}