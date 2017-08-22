package 和为S的两个数字;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * 题目描述
 * 输入一个递增排序的数组和一个数字S，在数组中查找两个数，是的他们的和正好是S，如果有多对数字的和等于S，
 * 输出两个数的乘积最小的。
 * <p>
 * 输出描述:
 * 对应每个测试案例，输出两个数，小的先输出。
 * <p>
 * 保存两个索引,分别指向最前面和最后面,因为数组是有序的,如果这个两个索引出的元素相加小于sum
 * 后面的索引前移,否则前面的索引后移,直到相加等于sum
 */
public class Solution {
    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] numbers = {1, 2, 3, 4};
        System.out.println(solution.FindNumbersWithSum(numbers, 5));
    }

    public ArrayList<Integer> FindNumbersWithSum(int[] array, int sum) {
        ArrayList<Integer> res = new ArrayList<>();
        if (array == null) {
            return res;
        }
        int low = 0;
        int high = array.length - 1;
        int n1 = Integer.MAX_VALUE, n2 = 1;
        while (low < high) {
            int sumTmp = array[low] + array[high];
            if (sumTmp > sum) {
                high--;
            } else if (sumTmp < sum) {
                low++;
            } else {
                if (n1 * n2 > array[low] * array[high]) {
                    n1 = array[low];
                    n2 = array[high];
                }
                res.clear();
                res.add(n1);
                res.add(n2);
                low++;
                high--;
            }
        }
        return res;
    }
}