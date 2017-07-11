
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner in = getScanner(System.in);
//        Scanner in = getScanner("input.txt");
        Solution solution = new Solution();
        while (in.hasNext()) {
            String line = in.nextLine();
            System.out.println(solution.numDecodings(line));
        }
    }

    public int numDecodings(String s) {
        if (s == null || s.length() == 0 || s.charAt(0) == '0') {
            return 0;
        }
        int[] res = new int[s.length() + 1];
        res[0] = 1;
        res[1] = 1;
        for (int i = 2; i <= s.length(); i++) {
            if (s.charAt(i - 2) == '1' || s.charAt(i - 2) == '2' && s.charAt(i - 1) <= '6') {
                res[i] += res[i - 2];
            }
            if (s.charAt(i - 1) != '0') {
                res[i] = res[i - 1];
            }
        }
        return res[s.length()];
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
