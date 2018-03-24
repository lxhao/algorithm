package 头条.第二题;

import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        try {
            System.setIn(new FileInputStream("input.txt"));
        } catch (FileNotFoundException ignored) {
        }
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            int n = in.nextInt();
            String s = "a";
            String m = "a";
            long ans = solve(s, m, n);
            System.out.println(ans);
        }
    }

    private static long solve(String s, String m, int n) {
        if (s.length() == n) {
            return 0;
        }
        if (s.length() > n) {
            return Integer.MAX_VALUE;
        }
        long method1 = solve(s + s, s, n);
        long methos2 = solve(s + m, m, n);
        return 1 + Math.min(method1, methos2);
    }
}

