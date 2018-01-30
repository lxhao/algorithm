package 讨厌的数字个数;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        System.setIn(new FileInputStream("input.txt"));
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            int start = in.nextInt();
            int end = in.nextInt();
            if (start == 0 && end == 0) {
                break;
            }
            int ans = 0;
            ans += getNumbersOf4(end) - getNumbersOf4(start - 1);
            ans += getNumbersOf38(end) - getNumbersOf38(start - 1);
            System.out.println(ans);
        }
        in.close();
    }

    private static int getNumbersOf38(int n) {
        return 0;
    }

    private static int getNumbersOf4(int n) {
        int count = 0;
        for (int i = 1; i <= n; i *= 10) {
            int a = n / i;
            int b = n % i;
            count += (a + 5) / 10 * i;
            count += (a % 10 == 4) ? b + 1 : 0;
            count -= (i - 1) * 10;
        }
        return count;
    }
}

