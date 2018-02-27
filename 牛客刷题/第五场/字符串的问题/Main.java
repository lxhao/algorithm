package 第五场.字符串的问题;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String args[]) {
        try {
            System.setIn(new FileInputStream("input.txt"));
        } catch (FileNotFoundException ignored) {
        }
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            String line = in.next();
            int[] next = getNext(line);
            int lastPrefixLen = next[line.length() - 1];

            if (lastPrefixLen == 0) {
                System.out.println("Just a legend");
                continue;
            }
            boolean flag = false;
            String ans = "";
            for (int i = 1; i < next.length - 1; i++) {
                int prefixLen = matchNext(next, next[i], lastPrefixLen);
                if (prefixLen > ans.length()) {
                    flag = true;
                    ans = line.substring(i - prefixLen + 1, i + 1);
                }
            }
            if (flag) {
                System.out.println(ans);
            } else {
                System.out.println("Just a legend");
            }
        }
    }

    private static int matchNext(int[] next, int prefixLen, int lastPrefixLen) {
        if (prefixLen == 0 || lastPrefixLen == 0) {
            return 0;
        }
        if (prefixLen > lastPrefixLen) {
            return matchNext(next, next[prefixLen - 1], lastPrefixLen);
        }
        if (prefixLen == lastPrefixLen) {
            return prefixLen;
        }
        return matchNext(next, prefixLen, next[lastPrefixLen - 1]);
    }

    private static int[] getNext(String str) {
        int[] next = new int[str.length()];
        int nextPos = 1;
        int prefixPos = 0;
        while (nextPos < str.length()) {
            if (str.charAt(prefixPos) == str.charAt(nextPos)) {
                prefixPos++;
                next[nextPos++] = prefixPos;
            } else if (prefixPos == 0) {
                nextPos++;
            } else {
                prefixPos = next[prefixPos - 1];
            }
        }
        return next;
    }
}