package 分苹果;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * https://www.nowcoder.com/practice/a174820de48147d489f64103af152709?tpId=85&tqId=29834&rp=1&ru=/ta/2017test&qru=/ta/2017test/question-ranking
 */
public class Main {

    public static void main(String[] args) throws IOException {
        try {
            System.setIn(new FileInputStream("input.txt"));
        } catch (FileNotFoundException ignored) {
        }
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] numbers = new int[n];
        int sum = 0;
        for (int i = 0; i < n; i++) {
            numbers[i] = in.nextInt();
            sum += numbers[i];
        }

        if (sum % n != 0) {
            System.out.println(-1);
            return;
        }

        int avg = sum / n;
        int diff = 0;
        for (int e : numbers) {
            if ((e - avg) % 2 != 0) {
                System.out.println(-1);
                return;
            }
            diff += Math.abs(e - avg);
        }

        System.out.println(diff / 4);
    }
}
