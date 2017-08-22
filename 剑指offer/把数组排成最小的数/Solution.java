package 把数组排成最小的数;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 题目描述
 * 输入一个正整数数组，把数组里所有数字拼接起来排成一个数，打印能拼接出的所有数字中最小的一个。
 * 例如输入数组{3，32，321}，则打印出这三个数字能排成的最小数字为321323。
 * <p>
 * <p>
 * 先将整型数组转换成String数组，然后将String数组排序，最后将排好序的字符串数组拼接出来。
 * 关键就是制定排序规则。
 */
public class Solution {
    public static void main(String[] args) {
        int[] numbers = {3334, 3, 3333332};
        Solution solution = new Solution();
        System.out.println(solution.PrintMinNumber(numbers));
    }

    public String PrintMinNumber(int[] numbers) {
        StringBuilder sb = new StringBuilder();
        if (numbers == null || numbers.length == 0) {
            return sb.toString();
        }
        List<String> list = new ArrayList<>();
        for (int e : numbers) {
            list.add(String.valueOf(e));
        }
        Collections.sort(list, getComparator());
        for (String e : list) {
            sb.append(e);
        }
        return sb.toString();
    }

    private Comparator<String> getComparator() {
        return new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                String t1 = s1 + s2;
                String t2 = s2 + s1;
                return t1.compareTo(t2);
            }
        };
    }
}