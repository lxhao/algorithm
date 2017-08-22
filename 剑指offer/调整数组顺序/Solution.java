package 调整数组顺序;

/**
 * 题目：
 * <p>
 * 输入一个整数数组，实现一个函数来调整该数组中数字的顺序，使得所有的奇数位于数组的前半部分，
 * 所有的偶数位于位于数组的后半部分，并保证奇数和奇数，偶数和偶数之间的相对位置不变。
 * <p>
 * 用冒泡的思想，把奇数往前面置换
 */

public class Solution {
    public static void main(String[] args) {
        int[] arr = {2, 4, 6, 1, 3, 5};
        Solution solution = new Solution();
        solution.reOrderArray(arr);
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
    }

    public void reOrderArray(int[] array) {
        if (array == null) {
            return;
        }
        for (int i = 0; i < array.length; i++) {
            for (int j = array.length - 1; j > i; j--) {
                //一个偶数后面跟一个奇数, 交换位置
                if (array[j - 1] % 2 == 0 && array[j] % 2 != 0) {
                    swap(array, j, j - 1);
                }
            }
        }
    }

    private void swap(int[] arr, int pos1, int pos2) {
        int temp = arr[pos1];
        arr[pos1] = arr[pos2];
        arr[pos2] = temp;
    }
}