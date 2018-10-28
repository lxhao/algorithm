package 混合燃料;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * https://www.nowcoder.com/practice/5b1116081ee549f882970eca84b4785a?tpId=85&tqId=29838&rp=1&ru=%2Fta%2F2017test&qru=%2Fta%2F2017test%2Fquestion-ranking
 * <p>
 * <p>
 * 遍历一遍所有的数
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
        System.out.println(solve(numbers));
    }

    private static int solve(int[] numbers) {
        //每次循环消去最高位的1
        for (int i = numbers.length - 1; i >= 0; i--) {
            Arrays.sort(numbers);
            for (int j = i - 1; j >= 0 && Integer.highestOneBit(numbers[i]) == Integer.highestOneBit(numbers[j]); j--) {
                numbers[j] ^= numbers[i];
            }
        }
        int res = 0;
        for (int e : numbers) {
            if (e != 0) {
                res++;
            }
        }
        return res;
    }
}
