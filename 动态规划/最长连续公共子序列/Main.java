package 最长连续公共子序列;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = getScanner(System.in);
//        Scanner in = getScanner("input.txt");
        while (in.hasNext()) {
            String s1 = in.nextLine();
            String s2 = in.nextLine();
            System.out.println(compute(s1, s2));
        }
    }

    private static int compute(String s1, String s2) {
        int[][] res = new int[s1.length() + 1][s2.length() + 1];
        int priorMax = Integer.MIN_VALUE;
        for (int i = 1; i <= s1.length(); i++) {
            for (int j = 1; j <= s2.length(); j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    res[i][j] = res[i - 1][j - 1] + 1;
                } else {
                    priorMax = Math.max(priorMax, res[i - 1][j - 1]);
                }
            }
        }
        return Math.max(priorMax, res[s1.length()][s2.length()]);
    }

    //从输入流读取输入数据
    public static Scanner getScanner(InputStream is) {
        return new Scanner(is);
    }

    //从文件读取输入数据
    public static Scanner getScanner(String fileName) {
        try {
            return getScanner(new FileInputStream(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}

