package YB要大炉石;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        System.setIn(new FileInputStream("input.txt"));
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            int n = in.nextInt();
            int[] numbers = new int[n];
            for (int i = 0; i < n; i++) {
                numbers[i] = in.nextInt();
            }
            System.out.println(getMaxLen(numbers) >= 30 ? "yes" : "no");
        }
        in.close();
    }

    private static int getMaxLen(int[] numbers) {
        int[] dp = new int[numbers.length];
        Arrays.fill(dp, 1);
        int maxLen = 0;
        for (int i = 1; i < numbers.length; i++) {
            for (int j = 0; j < i; j++) {
                if (numbers[i] >= numbers[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
                maxLen = Math.max(maxLen, dp[i]);
            }
        }
        return maxLen;
    }
}

