package 幸运的袋子;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * https://www.nowcoder.com/practice/a5190a7c3ec045ce9273beebdfe029ee?tpId=85&tqId=29839&rp=1&ru=/ta/2017test&qru=/ta/2017test/question-ranking
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
        for (int i = 0; i < n; i++) {
            numbers[i] = in.nextInt();
        }
        Arrays.sort(numbers);
        System.out.println(bfs(numbers, 0, 0, 1));
    }

    private static int bfs(int[] numbers, int curPos, int sum, int mul) {
        for (int i = curPos; i < numbers.length; i++) {
            sum += numbers[curPos];
            mul *= numbers[curPos];
        }

        return 0;
    }
}
