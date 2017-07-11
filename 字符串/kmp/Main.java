import java.util.*;
import java.io.*;

public class Main {
    public static void main(String args[]) {
        Scanner in = getScanner("input.txt");
        while (in.hasNext()) {
            String s = in.next();
            String target = in.next();
            System.out.println(s);
            System.out.println(target);

            long startTime = System.currentTimeMillis();
            System.out.println(search(s, target));
            long endTime = System.currentTimeMillis();
            System.out.printf("暴力搜索耗时:%d\n", endTime - startTime);

            startTime = System.currentTimeMillis();
            System.out.println(search(s, target, getNext(target)));
            endTime = System.currentTimeMillis();
            System.out.printf("暴力搜索耗时:%d\n", endTime - startTime);

        }
    }

    private static int[] getNext(String target) {
        int targetLen = target.length();
        int[] next = new int[targetLen];
        next[0] = -1;
        int k = -1;
        int j = 0;
        while( j < targetLen - 1 ) {
            if(k == -1 || target.charAt(j) == target.charAt(k)) {
                k++;
                j++;
                next[j] = k;
            } else {
               k = next[k];
            }
        }
        return next;
    }

    private static boolean search(String s, String target, int[] next) {
    }

    private static boolean search(String s, String target) {
        int j = 0;
        int i = 0;
        for (; i < s.length() && j < target.length();) {
            if (s.charAt(i) == s.charAt(j)) {
                i++;
                j++;
            } else {
                j = 0;
                i = i - j + 1;
            }
        }
        if (j == target.length()) {
            return true;
        }
        return false;
    }

    private static Scanner getScanner(String file) {
        try {
            return new Scanner(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            return null;
        }
    }
}
