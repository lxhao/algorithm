package 分田地;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * https://www.nowcoder.com/practice/fe30a13b5fb84b339cb6cb3f70dca699?tpId=85&tqId=29833&rp=1&ru=/ta/2017test&qru=/ta/2017test/question-ranking
 * <p>
 * <p>
 * 思路：
 * 记sum为所有田地的价值，划分后最小值的可能值一定在0-sum之间，用二分的做法猜出这个最小值。
 * 给定一个值，如果判断是否存在一种划分方式能得到这个最小值呢？
 * 枚举横向切三刀的所有情况，对横向切三刀的每种情况，判断是否能纵向切三刀，使得区域最小值满足条件。
 * 复杂度n^3*m*log(sum) 或者m^3*n*log(sum)
 */

public class Main {

    public static void main(String[] args) throws IOException {
        try {
            System.setIn(new FileInputStream("input.txt"));
        } catch (FileNotFoundException ignored) {
        }
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        int[][] matrix = new int[n + 1][m + 1];
        for (int i = 1; i <= n; i++) {
            String line = in.next();
            char[] charArr = line.toCharArray();
            for (int j = 1; j <= m; j++) {
                matrix[i][j] = charArr[j - 1] - '0';
            }
        }
        int[][] sum = calSum(matrix);
        System.out.println(split(sum));
    }

    private static int split(int[][] sum) {
        int rowLen = sum.length;
        int colLen = sum[0].length;
        int totalVal = sum[rowLen - 1][colLen - 1];
        int left = 0;
        int right = totalVal;
        int mid;
        int ans = 0;
        while (left <= right) {
            mid = (left + right) >>> 1;
            if (judge(mid, sum)) {
                left = mid + 1;
                ans = mid;
            } else {
                right = mid - 1;
            }
        }
        return ans;
    }

    private static boolean judge(int x, int[][] sum) {
        int rowLen = sum.length;
        int colLen = sum[0].length;

        // 记录划分了几刀
        for (int rowFir = 1; rowFir < rowLen; rowFir++) {
            for (int rowSec = rowFir + 1; rowSec < rowLen; rowSec++) {
                for (int rowThi = rowSec + 1; rowThi < rowLen; rowThi++) {
                    int preCol = 1;
                    int cnt = 0;
                    for (int colPos = 1; colPos < colLen; colPos++) {
                        int s1 = getArea(1, preCol, rowFir, colPos, sum);
                        int s2 = getArea(rowFir + 1, preCol, rowSec, colPos, sum);
                        int s3 = getArea(rowSec + 1, preCol, rowThi, colPos, sum);
                        int s4 = getArea(rowThi + 1, preCol, rowLen - 1, colPos, sum);
                        if (s1 >= x && s2 >= x && s3 >= x && s4 >= x) {
                            cnt++;
                            preCol = colPos + 1;
                        }
                    }
                    if (cnt >= 4) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static int getArea(int x1, int y1, int x2, int y2, int[][] sum) {
        return Math.abs(sum[x2][y2] - sum[x1 - 1][y2] - sum[x2][y1 - 1] + sum[x1 - 1][y1 - 1]);
    }

    private static int[][] calSum(int[][] matrix) {
        int[][] sum = new int[matrix.length][matrix[0].length];
        for (int i = 1; i < matrix.length; i++) {
            for (int j = 1; j < matrix[0].length; j++) {
                sum[i][j] = matrix[i][j] + sum[i - 1][j] + sum[i][j - 1] - sum[i - 1][j - 1];
            }
        }
        return sum;
    }
}
