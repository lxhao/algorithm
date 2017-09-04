import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;

public class 美团最长交错子序列 {
    public static void main(String[] args) {
        Scanner in = getScanner("input.txt");
        while (in.hasNext()) {
            int n = in.nextInt();
            int[] numbers = new int[n];
            for (int i = 0; i < n; i++) {
                numbers[i] = in.nextInt();
            }
            System.out.println(solve(numbers));
        }
    }

    private static int solve(int[] numbers) {
        int[] len = new int[numbers.length];
        len[0] = 1;
        for (int i = 1; i < numbers.length; i++) {
            len[i] = len[i - 1];
            for (int j = 0; j <= i; j++) {
                if (numbers[j] != numbers[i]) {
                    len[i] = Math.max(len[i], len[j] + 1);
                }
            }
        }
        return len[numbers.length - 1];
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
