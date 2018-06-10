package 地牢逃脱;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {
    /**
     * https://www.nowcoder.com/practice/0385945b7d834a99bc0010e67f892e38?tpId=85&tqId=29831&tPage=1&rp=1&ru=%2Fta%2F2017test&qru=%2Fta%2F2017test%2Fquestion-ranking
     */

    private static boolean isOk(int x, int y, char[][] board) {
        return x >= 0 && y >= 0 && x < board.length && y < board[0].length && board[x][y] == '.';
    }

    public static void main(String[] args) {
        try {
            System.setIn(new FileInputStream("input.txt"));
        } catch (FileNotFoundException ignored) {
        }
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        char[][] board = new char[n][m];
        for (int i = 0; i < n; i++) {
            board[i] = in.next().toCharArray();
        }
        int startX = in.nextInt();
        int startY = in.nextInt();

        int k = in.nextInt();
        int[][] stepTypes = new int[k][2];
        for (int i = 0; i < k; i++) {
            stepTypes[i][0] = in.nextInt();
            stepTypes[i][1] = in.nextInt();
        }

        int[][] ansArr = new int[n][m];
        for (int i = 0; i < n; i++) {
            Arrays.fill(ansArr[i], Integer.MAX_VALUE);
        }
        ansArr[startX][startY] = 0;
        Queue<Integer> queueX = new LinkedList<>();
        Queue<Integer> queueY = new LinkedList<>();
        queueX.add(startX);
        queueY.add(startY);
        int minStepLen = 1;
        //广度优先搜索
        while (!queueX.isEmpty()) {
            int len = queueX.size();
            for (int j = 0; j < len; j++) {
                int x = queueX.poll();
                int y = queueY.poll();
                for (int[] stepType : stepTypes) {
                    int nextX = x + stepType[0];
                    int nextY = y + stepType[1];
                    // 位置合理不是障碍点，且没有到达过
                    if (isOk(nextX, nextY, board) && ansArr[nextX][nextY] == Integer.MAX_VALUE) {
                        ansArr[nextX][nextY] = minStepLen;
                        queueX.add(nextX);
                        queueY.add(nextY);
                    }
                }
            }
            minStepLen++;
        }

        int ans = Integer.MIN_VALUE;
        for (int i = 0; i < ansArr.length; i++) {
            for (int j = 0; j < ansArr[0].length; j++) {
                if (board[i][j] == 'X') {
                    continue;
                }
                ans = Integer.max(ans, ansArr[i][j]);
            }
        }
        System.out.println(ans == Integer.MAX_VALUE ? -1 : ans);
    }

}
