package 二维数组的查找;

/**
 * 题目描述:
 * 在一个二维数组中，每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。
 * 请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
 */
public class Solution {

    public static void main(String[] args) {
        int[][] array = {
                {1, 2, 8, 9},
                {4, 7, 10, 13},
        };
        int target = 7;
        System.out.println(new Solution().Find(target, array));
    }

    /**
     * 从右往左搜索,如果大于target,继续往左移动,如果小于target,往下移动
     *
     * @param target
     * @param array
     * @return
     */
    public boolean Find(int target, int[][] array) {
        if (array == null || array.length == 0) {
            return false;
        }
        int row = 0;
        int col = array.length - 1;
        int nowVal;
        while (row < array[0].length && col >= 0) {
            nowVal = array[row][col];
            if (nowVal > target) {
                col--;
            } else if (nowVal < target) {
                row++;
            } else {
                return true;
            }
        }
        return false;
    }
}
