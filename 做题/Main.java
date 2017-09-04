import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
//        Scanner in = getScanner(System.in);
        Scanner in = getScanner("input.txt");
        while (in.hasNext()) {
            int n = in.nextInt();
            int[] numbers = new int[n];
            for (int i = 0; i < n; i++) {
                numbers[i] = in.nextInt();
            }
            System.out.println(solve(numbers));
        }
        in.close();
    }

    private static String solve(int[] numbers) {
        if (numbers.length <= 1) {
            return "No";
        }
        return (dispatch(numbers, 0, numbers[0])) ? "Yes" : "No";
    }

    private static boolean dispatch(int[] numbers, int startPos, int rest) {
        if (startPos == numbers.length) {
            return (rest >= numbers[0] && numbers[0] <= sum(numbers, 1));
        }
        for (int i = startPos; i < numbers.length; i++) {
            if (numbers[i] > rest && i != 0) {
                continue;
            }
            swap(numbers, i, startPos);
            if (dispatch(numbers, i + 1, numbers[i] + rest - Math.min(numbers[i], rest))) {
                return true;
            }
            swap(numbers, i, startPos);
        }
        return false;
    }

    private static void swap(int[] numbers, int p1, int p2) {
        int tmp = numbers[p1];
        numbers[p1] = numbers[p2];
        numbers[p2] = tmp;
    }

    private static int sum(int[] numbers, int startPos) {
        int sum = 0;
        for (int i = startPos; i < numbers.length; i++) {
            sum += numbers[i];
        }
        return sum;
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

