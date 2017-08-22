package 字符串的排列;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 题目描述
 * 输入一个字符串,按字典序打印出该字符串中字符的所有排列。例如输入字符串abc,
 * 则打印出由字符a,b,c所能排列出来的所有字符串abc,acb,bac,bca,cab和cba。
 * <p>
 * 第一个位置依次和后面的每个位置交换元素,递归处理
 */
public class Solution {
    public static void main(String[] args) {
        System.out.println(new Solution().Permutation("abc"));
    }

    public ArrayList<String> Permutation(String str) {
        ArrayList<String> res = new ArrayList<>();
        if (str == null || str.length() == 0) {
            return res;
        }
        Permutation(str.toCharArray(), res, 0);
        Collections.sort(res);
        return res;
    }

    private void Permutation(char[] str, ArrayList<String> res, int startPos) {
        if (startPos == str.length) {
            res.add(new String(str));
            return;
        }

        int[] flag = new int[128];
        for (int i = startPos; i < str.length; i++) {
            if (flag[str[i]] != 0) {
                continue;
            }
            flag[str[i]] = 1;
            swap(str, startPos, i);
            Permutation(str, res, startPos + 1);
            swap(str, startPos, i);
        }
    }

    private void swap(char[] str, int p1, int p2) {
        char tmp = str[p1];
        str[p1] = str[p2];
        str[p2] = tmp;
    }

}