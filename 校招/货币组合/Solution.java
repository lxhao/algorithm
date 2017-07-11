import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner in = getScanner(System.in);
//        Scanner in = getScanner("input.txt");
        int[] currency = {1, 5, 10, 20, 50, 100};
        while (in.hasNext()) {
            int sum = in.nextInt();
            long[][] res = new long[currency.length][sum + 1];
            System.out.println(solution(currency, currency.length - 1, res, sum));
        }
    }

    /**
     * @param currency       货币面值
     * @param nowCurrencyPos 当前计算到的货币位置
     * @param res            结果
     * @param sum            总和
     * @return 组合数量
     */
    private static long solution(int[] currency, int nowCurrencyPos, long[][] res, int sum) {
        //总和为1或者只剩下最后一种货币
        if (sum == 0 || nowCurrencyPos == 0) {
            return 1;
        }
        //每次保存计算过的结果，对计算过的值不重新计算
        if (res[nowCurrencyPos][sum] != 0) {
            return res[nowCurrencyPos][sum];
        }
        int stop = sum / currency[nowCurrencyPos];
        //对当前货币取0到stop张的总和加起来
        for (int i = 0; i <= stop; i++) {
            int restSum = sum - i * currency[nowCurrencyPos];
            res[nowCurrencyPos - 1][restSum] = solution(currency, nowCurrencyPos - 1, res, restSum);
            res[nowCurrencyPos][sum] += res[nowCurrencyPos - 1][restSum];
        }
        return res[nowCurrencyPos][sum];
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

