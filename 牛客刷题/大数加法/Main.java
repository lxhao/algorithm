package 大数加法;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        System.setIn(new FileInputStream("input.txt"));
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            String a = in.next();
            String b = in.next();
            System.out.println(add(a, b));
        }
        in.close();
    }

    private static String add(String a, String b) {
        if (a == null || b == null) {
            throw new IllegalArgumentException();
        }
        int aIdx = 0;
        int bIdx = 0;
        StringBuilder res = new StringBuilder();
        int aLen = a.length();
        int bLen = b.length();
        int sum, carry = 0;
        for (; aIdx < aLen || bIdx < bLen || carry != 0; aIdx++, bIdx++) {
            sum = carry;
            if (aIdx < aLen) {
                sum += a.charAt(aLen - aIdx - 1) - '0';
            }
            if (bIdx < bLen) {
                sum += b.charAt(bLen - bIdx - 1) - '0';
            }
            carry = sum / 10;
            sum = sum % 10;
            res.append(sum);
        }
        return res.reverse().toString();
    }
}

