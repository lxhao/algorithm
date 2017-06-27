import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class Main {
    private static char[] digits = {
            '0', '1', '2', '3', '4',
            '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F'
    };

    /**
     * 给出一个正整数n，我们把1..n在k进制下的表示连起来记为s(n,k)，
     * 例如s(16,16)=123456789ABCDEF10, s(5,2)=11011100101。
     * 现在对于给定的n和字符串t，我们想知道是否存在一个k(2 ≤ k ≤ 16)，
     * 使得t是s(n,k)的子串。
     * <p>
     * 输入描述:
     * 第一行一个整数n(1 ≤ n ≤ 50,000)。
     * 第二行一个字符串t(长度 ≤ 1,000,000)
     * <p>
     * <p>
     * 输出描述:
     * "yes"表示存在满足条件的k，否则输出"no"
     * <p>
     * 输入例子:
     * 8
     * 01112
     * <p>
     * 输出例子:
     * yes
     */
    public static void main(String[] args) {
        Scanner in = getScanner("input.txt");
        while (in.hasNext()) {
            int n = Integer.parseInt(in.nextLine());
            String s = in.nextLine();
            if (s.trim().length() == 0) {
                System.out.println("yes");
                continue;
            }
            int radix;
            StringBuilder numStrSb = new StringBuilder();
            StringBuilder sbTmp = new StringBuilder();
            //暴力遍历二到十六进制的所有可能
            for (radix = 2; radix <= 16; radix++) {
                numStrSb.setLength(0);
                for (int num = 1; num <= n; num++) {
                    sbTmp.setLength(0);
                    for (int value = num; value > 0; value /= radix) {
                        sbTmp.append(digits[value % radix]);
                    }
                    numStrSb.append(sbTmp.reverse());
                }
                if (numStrSb.toString().contains(s)) {
                    System.out.println("yes");
                    break;
                }
            }
            if (radix > 16) {
                System.out.println("no");
                break;
            }
        }
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

