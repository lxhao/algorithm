package 数组中出现次数超过一半的数字;

/**
 * 题目描述
 * 数组中有一个数字出现的次数超过数组长度的一半，
 * 请找出这个数字。例如输入一个长度为9的数组{1,2,3,2,2,2,5,4,2}。
 * 由于数字2在数组中出现了5次，超过数组长度的一半，因此输出2。如果不存在则输出0。
 * <p>
 */
public class Solution {
    public static void main(String[] args) {
        int[] array = {1, 2, 3, 2, 2, 2, 5, 4, 2};
        Solution solution = new Solution();
        System.out.println(solution.MoreThanHalfNum_Solution(array));
        int res = solution.partion(array, 0, array.length - 1, (array.length - 1) / 2);
    }

    public int MoreThanHalfNum_Solution(int[] array) {
        int res = partion(array, 0, array.length - 1, (array.length - 1) / 2);
        return checkMoreHalf(array, res) ? res : 0;

    }

    /**
     * 方法二:
     * 利用快排的思想找出第k大的数字
     */
    private int partion(int[] array, int start, int end, int target) {
        int tmp = array[start];
        int left = start;
        int right = end;

        while (left < right) {
            while (left < right && array[right] > tmp) {
                right--;
            }
            array[left] = array[right];

            while (left < right && array[left] <= tmp) {
                left++;
            }
            array[right] = array[left];
        }
        array[left] = tmp;
        if (left == target) {
            return array[left];
        } else if (left > target) {
            return partion(array, start, left - 1, target);
        } else {
            return partion(array, left + 1, end, target);
        }
    }


    //检查是否超过一半
    private boolean checkMoreHalf(int[] array, int n) {
        int counter = 0;
        for (int e : array) {
            if (n == e) {
                counter++;
            }
        }
        return (counter > (array.length) / 2);
    }

    /**
     * 方法一:
     * 保存两个值,一个是数组中的数字,一个是次数,当遍历下一个数组的时候,如果和我们之前保存的数字
     * 相同,次数加一;如果和我们之前保存的数字不同,次数减一;如果相同,次数置为0,更新保存的数字为当前位置的值
     * 由于我们要找的数字出现的次数比其它所有的数字次数还要多,那么要找的数字肯定是最后一次把次数设为1的数字.
     */
    public int MoreThanHalfNum_Solution1(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        int res = array[0];
        int counter = 0;
        for (int i = 0; i < array.length; i++) {
            if (counter == 0) {
                res = array[i];
                counter = 1;
            } else if (array[i] == res) {
                counter++;
            } else {
                counter--;
            }
        }
        return checkMoreHalf(array, res) ? res : 0;
    }
}