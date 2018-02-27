package 第五场.电灯泡;

import java.io.*;

class TreeArray {
    private int n;
    private int[][] treeArr;

    TreeArray(int n) {
        this.n = n;
        this.treeArr = new int[n + 1][n + 1];
    }

    void update(int x, int y, int val) {
        for (int i = x; i <= n; i += lowbit(i)) {
            for (int j = y; j <= n; j += lowbit(j)) {
                treeArr[i][j] += val;
            }
        }
    }

    private int getSum(int x, int y) {
        int res = 0;
        for (int i = x; i > 0; i -= lowbit(i)) {
            for (int j = y; j > 0; j -= lowbit(j)) {
                res += treeArr[i][j];
            }
        }
        return res;
    }

    int lowbit(int x) {
        return x & -x;
    }

    int getSum(int x1, int y1, int x2, int y2) {
        return getSum(x1 - 1, y1 - 1) + getSum(x2, y2) - getSum(x1 - 1, y2) - getSum(x2, y1 - 1);
    }

}

public class Main {
    public static void main(String args[]) throws IOException {
        try {
            System.setIn(new FileInputStream("input.txt"));
        } catch (FileNotFoundException ignored) {
        }
        StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
        in.nextToken();
        int n = (int) in.nval;
        in.nextToken();
        int m = (int) in.nval;

        TreeArray treeArray = new TreeArray(n);
        int[][] numbers = new int[n + 1][n + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                in.nextToken();
                numbers[i][j] = (int) in.nval;
                if (numbers[i][j] != 0) {
                    treeArray.update(i, j, 1);
                }
            }
        }

        for (int i = 0; i < m; i++) {
            in.nextToken();
            int type = (int) in.nval;
            if (type == 1) {
                in.nextToken();
                int x = (int) in.nval;
                in.nextToken();
                int y = (int) in.nval;
                treeArray.update(x, y, numbers[x][y] == 1 ? -1 : 1);
                numbers[x][y] ^= 1;
            } else {
                in.nextToken();
                int x1 = (int) in.nval;
                in.nextToken();
                int y1 = (int) in.nval;
                in.nextToken();
                int x2 = (int) in.nval;
                in.nextToken();
                int y2 = (int) in.nval;
                int ans = treeArray.getSum(x1, y1, x2, y2);
                System.out.println(ans);
            }
        }
    }
}