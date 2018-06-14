package 数列还原;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        try {
            System.setIn(new FileInputStream("input.txt"));
        } catch (FileNotFoundException ignored) {
        }
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int k = in.nextInt();
        int[] numbers = new int[n];
        List<Integer> misingPos = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            numbers[i] = in.nextInt();
            if (numbers[i] == 0) {
                misingPos.add(i);
            }
        }
        List<List<Integer>> pmtOfmissingNum = permutation(getMissingNum(numbers));

        int ans = 0;
        for (List<Integer> missing : pmtOfmissingNum) {
            for (int i = 0; i < misingPos.size(); i++) {
                numbers[misingPos.get(i)] = missing.get(i);
            }
            if (numOfOrderPeers(numbers) == k) {
                ans++;
            }
        }
        System.out.println(ans);
    }

    /**
     * 获取缺失的数字
     *
     * @param numbers
     * @return
     */
    private static List<Integer> getMissingNum(int[] numbers) {
        int[] flag = new int[numbers.length + 1];
        for (int e : numbers) {
            flag[e] = 1;
        }
        List<Integer> missingNum = new ArrayList<>();
        for (int i = 1; i < flag.length; i++) {
            if (flag[i] == 0) {
                missingNum.add(i);
            }

        }
        return missingNum;
    }

    /**
     * 全排列
     *
     * @param missingNum
     * @return
     */
    private static List<List<Integer>> permutation(List<Integer> missingNum) {
        List<List<Integer>> res = new ArrayList<>();
        permutation(missingNum, 0, res);
        return res;
    }

    private static void permutation(List<Integer> missingNum, int startPos, List<List<Integer>> res) {
        if (startPos == missingNum.size() - 1) {
            res.add(new ArrayList<>(missingNum));
            return;
        }

        for (int i = startPos; i < missingNum.size(); i++) {
            Collections.swap(missingNum, startPos, i);
            permutation(missingNum, startPos + 1, res);
            Collections.swap(missingNum, startPos, i);
        }
    }

    /**
     * 计算顺序对
     *
     * @param numbers
     * @return
     */
    private static int numOfOrderPeers(int[] numbers) {
        int res = 0;
        for (int i = 0; i < numbers.length; i++) {
            for (int j = i + 1; j < numbers.length; j++) {
                if (numbers[i] < numbers[j]) {
                    res++;
                }
            }
        }
        return res;
    }

}
