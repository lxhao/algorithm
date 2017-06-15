import java.util.*;

public class Main {
    public static void main(String args[]) {
        String s1 = "BDCABA";
        String s2 = "ABCBDAB";
        Deque<Character> res = new ArrayDeque<>();
        getLCS(s1, s2, res);
        int len = res.size();
        for(int i = 0; i < len; i++) {
            System.out.print(res.poll() + " ");
        }
    }

    public static void getLCS(String s1, String s2, Deque<Character> res) {
        int[][] tmp = new int[s1.length() + 1][s2.length() + 1];

        for (int i = 1; i <= s1.length(); i++) {
            for (int j = 1; j <= s2.length(); j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    tmp[i][j] = tmp[i - 1][j - 1] + 1;
                    continue;
                }
                tmp[i][j] = Math.max(tmp[i - 1][j], tmp[i][j - 1]);
            }
        }
        System.out.println(tmp[s1.length()][s2.length()]);

        int s1Pos = s1.length();
        int s2Pos = s2.length();
        for(; s1Pos >= 1 && s2Pos >= 1;) {
            if(s1.charAt(s1Pos - 1) == s2.charAt(s2Pos - 1)) {
                res.push(s1.charAt(s1Pos - 1));
                s1Pos--;
                s2Pos--;
                continue;
            }

            if(tmp[s1Pos - 1][s2Pos] > tmp[s1Pos][s2Pos - 1]) {
                s1Pos--;
            } else {
                s2Pos--;
            }
        }
    }
}
