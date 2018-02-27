package 第五场.逆序对;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        try {
            System.setIn(new FileInputStream("input.txt"));
        } catch (FileNotFoundException ignored) {
        }
        StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
        while (in.nextToken() != StreamTokenizer.TT_EOF) {
            int n = (int) in.nval;
            int[] numbers = new int[n];
            for (int i = 0; i < n; i++) {
                in.nextToken();
                numbers[i] = (int) in.nval;
            }
            System.out.println(reverseNum(numbers, 0, numbers.length - 1, new int[n]));
        }
    }

    private static long reverseNum(int[] numbers, int left, int right, int[] tmp) {
        if (left == right) {
            return 0;
        }
        int mid = (left + right) >>> 1;
        long res = reverseNum(numbers, left, mid, tmp) + reverseNum(numbers, mid + 1, right, tmp);
        res += compute(numbers, left, right, tmp);
        return res;
    }

    private static long compute(int[] numbers, int left, int right, int[] tmp) {
        int mid = (left + right) >>> 1;
        int leftPos = left;
        int rightPos = mid + 1;
        long reverseNum = 0;
        int tmpPos = left;
        while (leftPos <= mid && rightPos <= right) {
            if (numbers[leftPos] < numbers[rightPos]) {
                tmp[tmpPos++] = numbers[leftPos++];
                reverseNum += rightPos - mid - 1;
            } else {
                tmp[tmpPos++] = numbers[rightPos++];
            }
        }

        while (rightPos <= right) {
            tmp[tmpPos++] = numbers[rightPos++];
        }

        while (leftPos <= mid) {
            tmp[tmpPos++] = numbers[leftPos++];
            reverseNum += rightPos - mid - 1;
        }
        System.arraycopy(tmp, left, numbers, left, right + 1 - left);
        return reverseNum;
    }
}

