package DecodeWaysII;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * https://leetcode.com/problems/decode-ways-ii/description/
 */
public class Solution {
    private static final int M = 1000000007;

    public static void main(String[] args) throws IOException {
        try {
            System.setIn(new FileInputStream("input.txt"));
        } catch (FileNotFoundException ignored) {
        }
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            String s = in.next();
            System.out.println(new Solution().numDecodings(s));
        }
    }

    private int add(long n1, long n2) {
        return (int) ((n1 % M + n2 % M) % M);
    }

    private long oneChar(char c) {
        if (c == '*') {
            return 9;
        } else if (c == '0') {
            return 0;
        } else {
            return 1;
        }
    }

    private long twoChars(char c1, char c2) {
        int res = 0;
        if (c1 == '1' || c1 == '*') {
            if (c2 == '*') {
                res += 9;
            } else {
                res += 1;
            }
        }
        if (c1 == '2' || c1 == '*') {
            if (c2 == '*') {
                res += 6;
            } else if (c2 <= '6') {
                res += 1;
            }
        }
        return res;
    }

    public int numDecodings(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int[] dp = new int[s.length() + 1];
        dp[0] = 1;
        dp[1] = (int) oneChar(s.charAt(0));
        for (int i = 1; i < s.length(); i++) {
            long oneCharWays = oneChar(s.charAt(i)) * dp[i];
            long towCharsWays = twoChars(s.charAt(i - 1), s.charAt(i)) * dp[i - 1];
            dp[i + 1] = add(oneCharWays, towCharsWays);
        }
        return dp[s.length()];
    }
}
