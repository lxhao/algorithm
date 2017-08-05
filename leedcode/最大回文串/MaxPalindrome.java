import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * Created by lxhao on 17-8-4.
 * <p>
 * 求最长回文子串
 * <p>
 * http://www.61mon.com/index.php/archives/181/
 */
public class MaxPalindrome {
    public static void main(String[] args) {
//        Scanner in = getScanner(System.in);
        Scanner in = getScanner("input.txt");
        while (in.hasNext()) {
            System.out.println(solution(in.next()));
        }
        in.close();
    }

    private static int solution(String s) {
        //在字符间插入#
        char[] chars = new char[s.length() * 2 + 1];
        for (int i = 0; i < chars.length; i += 2) {
            chars[i] = '#';
            if ((i + 1) / 2 >= s.length()) {
                break;
            }
            chars[i + 1] = s.charAt((i + 1) / 2);
        }

        int maxRight = 0;
        int id = 0;
        int maxLen = Integer.MIN_VALUE;
        int[] len = new int[chars.length];
        for (int i = 1; i < chars.length; i++) {
            len[i] = i < maxRight ? Math.min(len[2 * id - 1], maxRight - i) : 1;
            while (i - len[i] >= 0 && i + len[i] < chars.length && chars[i - len[i]] == chars[i + len[i]]) {
                len[i]++;
            }

            if (len[i] > maxRight) {
                id = i;
                maxRight = len[i];
                maxLen = Math.max(maxLen, maxRight - 1);
            }
        }

        return maxLen;
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
