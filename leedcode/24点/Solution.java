
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner in = getScanner("input.txt");
        int[] values;
        String line;
        String[] valuesStr;
        boolean hasError;
        while (in.hasNext()) {
            line = in.nextLine();
            valuesStr = line.split("\\s");
            values = new int[valuesStr.length];
            hasError = false;
            for (int i = 0; i < valuesStr.length; i++) {
                if (valuesStr[i].toLowerCase().equals("joker")) {
                    hasError = true;
                    System.out.println("ERROR");
                    break;
                }
                values[i] = getValue(valuesStr[i]);
            }
            if (hasError) {
                continue;
            }
            String[] res = new String[values.length + 3];
            solution(values, res, 0, 0);
        }
    }

    public static void solution(int[] values, String[] res, int nowPos, double computedValue) {
        if (nowPos == values.length) {
            if (computedValue == 24.0) {
                if (Calculator.compute(res) == 24.0) {
                    for (String re : res) {
                        System.out.print(re);
                    }
                    System.out.println("");
                }
            }
            return;
        }
        for (int i = nowPos; i < values.length; i++) {
            if (nowPos == 0) {
                computedValue = values[i];
                res[0] = String.valueOf(values[i]);
                swap(values, nowPos, i);
                solution(values, res, nowPos + 1, computedValue);
                swap(values, nowPos, i);
                continue;
            }
            if (computedValue + values[nowPos] <= 24.0) {
                computedValue += values[nowPos];
                res[nowPos * 2 - 1] = "+";
                res[nowPos * 2] = String.valueOf(values[i]);
                swap(values, nowPos, i);
                solution(values, res, nowPos + 1, computedValue);
                swap(values, nowPos, i);
                computedValue -= values[nowPos];
            }
            if (computedValue - values[nowPos] <= 24.0) {
                computedValue -= values[nowPos];
                res[nowPos * 2 - 1] = "-";
                res[nowPos * 2] = String.valueOf(values[i]);
                swap(values, nowPos, i);
                solution(values, res, nowPos + 1, computedValue);
                swap(values, nowPos, i);
                computedValue += values[nowPos];
            }
            if (computedValue * values[nowPos] <= 24.0) {
                computedValue *= values[nowPos];
                res[nowPos * 2 - 1] = "*";
                res[nowPos * 2] = String.valueOf(values[i]);
                swap(values, nowPos, i);
                solution(values, res, nowPos + 1, computedValue);
                swap(values, nowPos, i);
                computedValue /= values[nowPos];
            }
            if (computedValue / values[nowPos] <= 24.0) {
                computedValue /= values[nowPos];
                res[nowPos * 2 - 1] = "/";
                res[nowPos * 2] = String.valueOf(values[i]);
                swap(values, nowPos, i);
                solution(values, res, nowPos + 1, computedValue);
                swap(values, nowPos, i);
                computedValue *= values[nowPos];
            }
        }
    }

    private static void swap(int[] values, int p1, int p2) {
        int tmp = values[p1];
        values[p1] = values[p2];
        values[p2] = tmp;
    }

    public static int getValue(String c) {
        if (c == null || c.length() > 2 || c.length() == 0) {
            throw new IllegalArgumentException();
        }
        if (c.length() == 2) {
            return 10;
        }
        char valueChar = c.charAt(0);
        if (valueChar >= '0' && valueChar <= '9') {
            return valueChar - '0';
        }

        switch (valueChar) {
        case 'A':
            return 1;
        case 'J':
            return 11;
        case 'Q':
            return 12;
        case 'K':
            return 13;
        }
        return -1;
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
