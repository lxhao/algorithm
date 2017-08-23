import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class 进制转换 {

    final static char[] digits = {
            '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b',
            'c', 'd', 'e', 'f', 'g', 'h',
            'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F',
            'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'X',
            'Y', 'Z'
    };

    public static void main(String[] args) {
//        Scanner in = getScanner(System.in);
        Scanner in = getScanner("input.txt");
        while (in.hasNext()) {
            int from = in.nextInt();
            int to = in.nextInt();
            String number = in.next();
            System.out.println(solve(from, to, number));
        }
        in.close();
    }

    /**
     * 先转换成10进制,再从十进制转换为目标进制,需要考虑负数和0
     *
     * @param from
     * @param to
     * @param number
     * @return
     */
    private static String solve(int from, int to, String number) {
        //转换为10进制
        boolean nagative = false;
        int pos = 0;
        if (number.charAt(0) == '-') {
            nagative = true;
            pos = 1;
        }

        int len = number.length();
        //还原成十进制
        int fromNuber = 0;
        for (; pos < len; pos++) {
            fromNuber += getVal(number.charAt(pos)) * Math.pow(from, len - pos - 1);
        }
        if (fromNuber == 0) {
            return "0";
        }
        //从十进制转换为任意进制
        StringBuilder sb = new StringBuilder();
        while (fromNuber != 0) {
            sb.append(digits[fromNuber % to]);
            fromNuber /= to;
        }
        sb.reverse();
        return (nagative ? "-" : "") + sb.toString();
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

    /**
     * 获取字符对应的整数
     *
     * @param c
     * @return
     */
    private static int getVal(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        }

        if (c >= 'a' && c <= 'z') {
            return c - 'a' + 10;
        }

        if (c >= 'A' && c <= 'Z') {
            return c - 'A' + 10 + 26;
        }

        return -1;
    }

}

