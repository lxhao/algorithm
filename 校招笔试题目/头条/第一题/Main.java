package 头条.第一题;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        try {
            System.setIn(new FileInputStream("input.txt"));
        } catch (FileNotFoundException ignored) {
        }
        StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
        while (in.nextToken() != StreamTokenizer.TT_EOF) {
            int n = (int) in.nval;
            in.nextToken();
            int k = (int) in.nval;
            int[] numbers = new int[n];
            for (int i = 0; i < n; i++) {
                in.nextToken();
                numbers[i] = (int) in.nval;
            }
            System.out.println(solve(numbers, k));
        }
    }

    private static long solve(int[] numbers, int k) {
        Arrays.sort(numbers);
        int left = 0;
        int right = 0;
        long ans = 0;
        while (left < numbers.length && right < numbers.length) {
            if (left > 0 && numbers[left] == numbers[left - 1]) {
                left++;
                continue;
            }
            int diff = numbers[right] - numbers[left];
            if (diff == k) {
                ans++;
                left++;
                right++;
            } else if (diff < k) {
                right++;
            } else {
                left++;
            }
        }
        return ans;
    }
}

