package 藏宝图子序列;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * https://www.nowcoder.com/practice/74475ee28edb497c8aa4f8c370f08c30?tpId=85&tqId=29836&rp=1&ru=/ta/2017test&qru=/ta/2017test/question-ranking
 * <p>
 * 第一想法是用最长公共子序列做的，看了下讨论区发现其实只要用两个索引遍历一遍就可以了
 */

public class Main {

    public static void main(String[] args) throws IOException {
        try {
            System.setIn(new FileInputStream("input.txt"));
        } catch (FileNotFoundException ignored) {
        }
        Scanner in = new Scanner(System.in);
        String s = in.next();
        String p = in.next();
        int max = maxComSubseq(s, p);
        System.out.println(max == p.length() ? "Yes" : "No");
    }

    private static int maxComSubseq(String s, String p) {
        int[][] dp = new int[s.length() + 1][p.length() + 1];
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 1; j <= p.length(); j++) {
                if (s.charAt(i - 1) == p.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Integer.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[s.length()][p.length()];
    }
}
