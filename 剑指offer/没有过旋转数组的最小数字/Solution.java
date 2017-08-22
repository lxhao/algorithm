package 没有过旋转数组的最小数字;

/**
 * 题目:
 * <p>
 * 把一个数组最开始的若干个元素搬到数组的末尾，我们称之为数组的旋转。 输入一个非递减排序的数组的一个旋转，
 * 输出旋转数组的最小元素。 例如数组{3,4,5,1,2}为{1,2,3,4,5}的一个旋转，该数组的最小值为1。
 * NOTE：给出的所有元素都大于0，若数组大小为0，请返回0。
 * <p>
 * 这个题目暴力的方式是o(n)的复杂度,lg(n)的复杂度可以实现
 * 初始化left=0, right = len - 1, mid = (left + right) / 2
 * 如果arr[mid]大于arr[right] 证明最小值在mid右边,让left = mid + 1
 * 如果arr[mid]小于arr[right] 证明最小值在mid左边,让right = mid - 1
 * 如果arr[mid]等于arr[right] 不能判断最小值是在mid左边还是右边
 * 考虑这样的情况,[1, 0, 1, 1, 1]和[1, 1, 1, 0, 1], 0有可能在mid左边,也有可能在mid右边
 * 我们让right往左移动一位
 */

public class Solution {
    public static void main(String[] args) {
        int[] numbers = {1, 1, 1, 0, 1};
        System.out.println(new Solution().minNumberInRotateArray(numbers));
    }

    public int minNumberInRotateArray(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        int left = 0;
        int right = array.length - 1;
        int mid = 0;
        while (left < right) {
            mid = (left + right) >>> 1;
            if (array[mid] > array[right]) {
                left = mid + 1;
            } else if (array[mid] <= array[right]) {
                right = mid - 1;
            } else {
                right = mid;
            }
        }
        return array[left];
    }
}