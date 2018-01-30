import java.util.ArrayList;

public class Solution {
    public static void main(String[] args) {
        int[][] matrix = {
                {1, 2},
                {3, 4},
                {5, 6},
                {7, 8},
                {9, 10}
        };
        System.out.println(new Solution().printMatrix(matrix));
    }

    public ArrayList<Integer> printMatrix(int[][] matrix) {
        ArrayList<Integer> ans = new ArrayList<>();

        if (matrix == null) {
            return ans;
        }

        int top = 0;
        int left = 0;

        int rowLen = matrix.length;
        int colLen = matrix[0].length;

        int maxCircle = Math.min((matrix[0].length + 1) / 2, (matrix.length + 1) / 2);
        for (int m = 0; m <= maxCircle; m++, top++, left++) {
            // 从左至右打印上面一行
            if (top <= (rowLen - 1) / 2) {
                for (int i = left; i <= (colLen - left - 1); i++) {
                    ans.add(matrix[top][i]);
                }
            }

            //从上往下打印右边一列
            if (left <= (colLen - 1) / 2) {
                for (int i = top + 1; i <= (rowLen - top - 1) - 1; i++) {
                    ans.add(matrix[i][colLen - left - 1]);
                }
            }

            //从右至左打印底部一行
            if (top <= (rowLen - 1) / 2 && top != rowLen - top - 1) {
                for (int i = (colLen - left - 1); i >= left; i--) {
                    ans.add(matrix[rowLen - top - 1][i]);
                }
            }

            //从下往上打印左边一列
            if (left <= (colLen - 1) / 2 && left != colLen - left - 1) {
                for (int i = (rowLen - top - 1) - 1; i >= top + 1; i--) {
                    ans.add(matrix[i][left]);
                }
            }
        }
        return ans;
    }
}