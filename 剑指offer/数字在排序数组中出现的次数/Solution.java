package 数字在排序数组中出现的次数;

/**
 * 题目描述
 * 统计一个数字在排序数组中出现的次数。
 * 用二分查找找到数字出现的第一个位置和最后一个位置
 */
public class Solution {
    public static void main(String[] args) {
        int[] numbers = {1, 2, 2, 2, 3, 3, 3};
        Solution solution = new Solution();
//        System.out.println(solution.findFirstK(numbers, 3));
        System.out.println(solution.findLastK(numbers, 3));
//        System.out.println(solution.GetNumberOfK(numbers, 3));
    }

    public int GetNumberOfK(int[] array, int k) {
        int lastPos = findLastK(array, k);
        if (lastPos < 0) {
            return 0;
        }
        return lastPos - findFirstK(array, k) + 1;
    }

    private int findFirstK(int[] array, int k) {
        int left = 0;
        int right = array.length - 1;
        int mid;
        while (left <= right) {
            mid = (left + right) >>> 1;
            if (array[mid] < k) {
                left = mid + 1;
            } else if (array[mid] > k) {
                right = mid - 1;
            } else if (mid - 1 >= left && array[mid - 1] == k) {
                right = mid - 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    private int findLastK(int[] array, int k) {
        int left = 0;
        int right = array.length - 1;
        int mid;
        while (left <= right) {
            mid = (left + right) >>> 1;
            if (array[mid] > k) {
                right = mid - 1;
            } else if (array[mid] < k) {
                left = mid + 1;
            } else if (mid + 1 <= right && array[mid + 1] == k) {
                left = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }
}