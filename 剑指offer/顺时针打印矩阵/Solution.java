package 顺时针打印矩阵;

import java.util.ArrayList;

/**
 * 题目描述
 * 输入一个矩阵，按照从外向里以顺时针的顺序依次打印出每一个数字，例如，
 * 如果输入如下矩阵： 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16
 * 则依次打印出数字1,2,3,4,8,12,16,15,14,13,9,5,6,7,11,10.
 */
public class Solution {
    public static void main(String[] args) {
        int[][] matrix = {
                {1},
                {2},
                {3},
                {4},
                {5}
        };
        System.out.println(new Solution().printMatrix(matrix));
    }

    public ArrayList<Integer> printMatrix(int[][] matrix) {
        ArrayList<Integer> res = new ArrayList<>();
        int rowLen = matrix.length;
        int colLen = matrix[0].length;
        //得到打印的层数
        int layers = Math.min((rowLen + 1) / 2, (colLen + 1) / 2);
        for (int i = 0; i < layers; i++) {
            //打印上面一行
            for (int j = i; j <= colLen - i - 1; j++) {
                res.add(matrix[i][j]);
            }

            //打印右边一列
            for (int j = i + 1; j <= rowLen - i - 2; j++) {
                res.add(matrix[j][colLen - i - 1]);
            }

            //打印下面一行, 需要判断是否和上面一行重复
            for (int j = colLen - i - 1; j >= i && rowLen - i - 1 != i; j--) {
                res.add(matrix[rowLen - i - 1][j]);
            }

            //打印左边一列,需要判断是否和右边一列重复
            for (int j = rowLen - i - 2; j >= i + 1 && i != colLen - i - 1; j--) {
                res.add(matrix[j][i]);
            }
        }
        return res;
    }
}

