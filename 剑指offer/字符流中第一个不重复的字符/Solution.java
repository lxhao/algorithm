package 字符流中第一个不重复的字符;

/**
 * 题目描述
 * 在一个字符串(1<=字符串长度<=10000，全部由字母组成)中找到第一个只出现一次的字符,并返回它的位置
 */
public class Solution {
    public static void main(String[] args) {
        System.out.println(new Solution().FirstNotRepeatingChar("helloh"));
    }
    public int FirstNotRepeatingChar(String str) {
        int[] counter = new int[128];
        int len = str.length();
        for (int i = 0; i < len; i++) {
            counter[str.charAt(i)]++;
        }
        for (int i = 0; i < len; i++) {
            if (counter[str.charAt(i)] == 1) {
                return i;
            }
        }
        return -1;
    }
}