import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class BigNumberMul {
    public static void main(String[] args) {
        Scanner in = getScanner(System.in);
//        Scanner in = getScanner("input.txt");
        while (in.hasNext()) {
            String num1 = in.next();
            String num2 = in.next();
            System.out.println(mutile(num1, num2));
        }
        in.close();
    }

    private static String mutile(String num1, String num2) {
        int[] res = new int[num1.length() + num2.length()];
        int num1Len = num1.length();
        int num2Len = num2.length();
        for (int i = 0; i < num1Len; i++) {
            for (int j = 0; j < num2Len; j++) {
                int n1 = num1.charAt(i) - '0';
                int n2 = num2.charAt(j) - '0';
                res[i + j + 1] += n1 * n2;
            }
        }
        int radis = 0;
        for (int i = res.length - 1; i >= 0; i--) {
            res[i] += radis;
            radis = res[i] / 10;
            if (res[i] >= 10) {
                res[i] = res[i] % 10;
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < res.length; i++) {
            if (i == 0 && res[0] == 0) {
                continue;
            }
            sb.append(res[i]);
        }
        return sb.toString();
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

