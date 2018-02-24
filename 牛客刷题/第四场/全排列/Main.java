package 第四场.全排列;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        int n = 8;
        int[] numbers = new int[n];
        for (int i = 1; i <= n; i++) {
            numbers[i - 1] = i;
        }
        List<String> res = new ArrayList<>();
        arrange(numbers, 0, res);
        Collections.sort(res);
        for (String e : res) {
            System.out.println(e);
        }
    }

    private static void arrange(int[] numbers, int curPos, List<String> res) {
        if (curPos == numbers.length - 1) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < numbers.length - 1; i++) {
                sb.append(numbers[i]).append(" ");
            }
            sb.append(numbers[numbers.length - 1]);
            res.add(sb.toString());
        }
        for (int i = curPos; i < numbers.length; i++) {
            swap(numbers, curPos, i);
            arrange(numbers, curPos + 1, res);
            swap(numbers, curPos, i);
        }
    }

    private static void swap(int[] numbers, int p1, int p2) {
        int tmp = numbers[p1];
        numbers[p1] = numbers[p2];
        numbers[p2] = tmp;
    }
}

