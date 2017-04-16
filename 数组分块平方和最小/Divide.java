import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Divide {
    //保存求和结果, 避免每次都去计算
    private static int[] sumArr;
    //保存分组结果
    private static int[][][] result;

    private int divide(int[] number, int start, int end, int k) {
        if (k == 1) {
            return calSum(number, start, end) * calSum(number, start, end);
        }

        int min = Integer.MAX_VALUE;
        int temp = 0;
        int i;
        for (i = start; i <= end; i++) {

            int resultOfSum1 = Integer.MAX_VALUE;
            if (end - i >= k - 1) {
                resultOfSum1 = result[i + 1][end][k - 1] > 0 ? result[i + 1][end][k - 1] : divide(number, i + 1, end, k - 1);
                result[i + 1][end][k - 1] = resultOfSum1;
                resultOfSum1 += divide(number, start, i, 1);
            }


            int resultOfSum2 = Integer.MAX_VALUE;
            if (i - start >= k - 1) {
                resultOfSum2 = result[start][i - 1][k - 1] > 0 ? result[start][i - 1][k - 1] : divide(number, start, i - 1, k - 1);
                result[start][i - 1][k - 1] = resultOfSum2;
                resultOfSum2 += divide(number, i, end, 1);
            }

            temp = resultOfSum1 < resultOfSum2 ? resultOfSum1 : resultOfSum2;

            if (temp < min) {
                min = temp;
            }
        }
        return min;
    } // end of procedure divide

    private int calSum(int[] A, int start, int end) {
        if (start == 0) {
            return sumArr[end];
        }
        return sumArr[end] - sumArr[start - 1];
    }

    private void test(String filePath) {
        List<List<Integer>> data = readData(filePath);
        int corectAmount = 0;
        for (List<Integer> testData : data) {
            //分组数
            int k = testData.get(0);
            //结果
            int result = testData.get(1);
            int[] number = new int[testData.size() - 2];
            for (int i = 2; i < testData.size(); i++) {
                number[i - 2] = testData.get(i);
            }
            if (result == compute(number, k)) {
                corectAmount++;
            }
        }
        System.out.printf("正确率: %.2f\n", corectAmount * 100.0 / data.size());
    }

    private int compute(int[] number, int k) {
        sumArr = new int[number.length];
        result = new int[number.length][number.length][k];
        sumArr[0] = number[0];
        for (int i = 1; i < number.length; i++) {
            sumArr[i] = number[i] + sumArr[i - 1];
        }
        return divide(number, 0, number.length - 1, k);
    }


    /**
     * 每组数据用A分隔,第一行为分组数,第二行为最优解,剩余部分为输入的数据
     *
     * @param filePath
     * @return
     */
    private List<List<Integer>> readData(String filePath) {
        List<List<Integer>> data = new ArrayList<>();
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get(filePath), Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (lines == null) {
            return null;
        }
        List<Integer> temp = new ArrayList<>();
        for (String line : lines) {
            if (line.equals("A")) {
                data.add(temp);
                temp = new ArrayList<>();
                continue;
            }
            temp.add(Integer.valueOf(line));
        }
        return data;
    }


    public static void main(String[] args) {
        new Divide().test(args[0]);
    }
}
