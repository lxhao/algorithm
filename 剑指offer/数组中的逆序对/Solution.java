package 数组中的逆序对;

/**
 * 题目描述
 * 在数组中的两个数字，如果前面一个数字大于后面的数字，则这两个数字组成一个逆序对。
 * 输入一个数组,求出这个数组中的逆序对的总数P。并将P对1000000007取模的结果输出。 即输出P%1000000007
 * <p>
 * 输入描述:
 * 题目保证输入的数组中没有的相同的数字
 * 数据范围：
 * 对于%50的数据,size<=10^4
 * 对于%75的数据,size<=10^5
 * 对于%100的数据,size<=2*10^5
 * 示例1
 * 输入
 * <p>
 * 1,2,3,4,5,6,7,0
 * 输出
 * <p>
 * 7
 * <p>
 * 用归并排序的思想,放入左边的元素时,逆序对就是右边已经保存的元素数量
 */

public class Solution {
    private static final int MAX = (int) (Math.pow(10, 9) + 7);

    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 0};
        Solution solution = new Solution();
        System.out.println(solution.InversePairs(array));
    }

    public int InversePairs(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        return InversePairs(array, 0, array.length - 1, new int[array.length]);
    }

    public int InversePairs(int[] array, int start, int end, int[] tmp) {
        int res = 0;
        if (start >= end) {
            return res;
        }
        int mid = (start + end) >> 1;
        res += InversePairs(array, start, mid, tmp) + InversePairs(array, mid + 1, end, tmp);
        res %= MAX;
        int left = start;
        int right = mid + 1;
        int index = left;
        while (left <= mid && right <= end) {
            if (array[left] <= array[right]) {
                tmp[index++] = array[left++];
                res += right - mid - 1;
                res %= MAX;
            } else {
                tmp[index++] = array[right++];
            }
        }
        while (left <= mid) {
            tmp[index++] = array[left++];
            res += right - mid - 1;
            res %= MAX;
        }

        while (right <= end) {
            tmp[index++] = array[right++];
        }

        System.arraycopy(tmp, start, array, start, end + 1 - start);
        return res % MAX;
    }
}