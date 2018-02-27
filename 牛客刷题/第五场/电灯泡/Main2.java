package 第五场.电灯泡;

import java.io.*;

class TreeArray2 {
    int n;
    int[] numbers;

    TreeArray2(int n) {
        this.n = n;
        this.numbers = new int[n + 1];
    }

    long getSum(int pos) {
        long res = 0;
        while (pos > 0) {
            res += numbers[pos];
            pos -= pos & (pos ^ (pos - 1));
        }
        return res;
    }

    long getSum(int p1, int p2) {
        return getSum(p1) - getSum(p2 - 1);
    }

    void update(int pos, int val) {
        while (pos <= n) {
            numbers[pos] += val;
            pos += pos & (pos ^ (pos - 1));
        }
    }

    int getVal(int pos) {
        return numbers[pos];
    }
}

public class Main2 {
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

        TreeArray2 treeArray = new TreeArray2(n * n);
        int[] numbers = new int[n * n + 1];
        for (int i = 1; i <= n * n; i++) {
            in.nextToken();
            int val = (int) in.nval;
            numbers[i] = val;
            if (val != 0) {
                treeArray.update(i, 1);
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
                int pos = (x - 1) * n + y;
                treeArray.update(pos, numbers[pos] == 1 ? -1 : 1);
                numbers[pos] ^= 1;
            } else {
                in.nextToken();
                int x1 = (int) in.nval;
                in.nextToken();
                int y1 = (int) in.nval;
                in.nextToken();
                int x2 = (int) in.nval;
                in.nextToken();
                int y2 = (int) in.nval;
                long ans = 0;
                for (int linePos = x1 - 1; linePos < x2; linePos++) {
                    ans += treeArray.getSum(linePos * n + y2, linePos * n + y1);
                }
                System.out.println(ans);
            }
        }
    }
}
