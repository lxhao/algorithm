import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * 最长公共连续子序列
 * <p>
 * 输入:
 * abcde
 * abgde
 * <p>
 * 输出:
 * 2
 */
public class MaxSubsequence {
    public static void main(String[] args) {
        Scanner in = getScanner(System.in);
//        Scanner in = getScanner("input.txt");
        while (in.hasNext()) {
            String line1 = in.nextLine();
            String line2 = in.nextLine();
            System.out.println(solve(line1, line2));
        }
        in.close();
    }

    /**
     * 和最长连续递增子序列类似的做法,不同的是len[i][j]保存的是以str1以i处字符结尾
     * 和str2以j处字符结尾的连续子序列长度, 如果i和j处的字符不相等,len[i][j] = 0,
     * 否则等于1 + len[i - 1][j - 1]
     *
     * @param str1
     * @param str2
     * @return
     */
    private static int solve(String str1, String str2) {
        int[][] len = new int[str1.length() + 1][str2.length() + 1];
        int maxLen = 0;
        for (int i = 1; i <= str1.length(); i++) {
            for (int j = 1; j <= str2.length(); j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    len[i][j] = 1 + len[i - 1][j - 1];
                    maxLen = Math.max(maxLen, len[i][j]);
                }
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

