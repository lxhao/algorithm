package 挑战赛11场.白兔的式子;

import java.io.*;

public class Main {
    private static final int MAX = 998244353;

    public static void main(String[] args) throws IOException {
        try {
            System.setIn(new FileInputStream("input.txt"));
        } catch (FileNotFoundException ignored) {
        }
        StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (in.nextToken() != StreamTokenizer.TT_EOF) {
            int T = (int) in.nval;
            while (T-- > 0) {
                in.nextToken();
                int a = (int) in.nval;
                in.nextToken();
                int b = (int) in.nval;
                in.nextToken();
                int n = (int) in.nval;
                in.nextToken();
                int m = (int) in.nval;
                solve(a, b, n, m);
            }
        }
    }

    private static void solve(int a, int b, int n, int m) {
        long[] dp = new long[m + 1];
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            for (int j = m; j >= 1; j--) {
                dp[j] = (a * dp[j]) % MAX;
                dp[j] += (b * dp[j - 1]) % MAX;
                dp[j] %= MAX;
            }
        }
        System.out.println(dp[m]);
    }
}

