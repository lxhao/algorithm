import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class 华为字符串拼接 {
    public static void main(String[] args) {
//        Scanner in = getScanner(System.in);
        Scanner in = getScanner("input.txt");
        while (in.hasNext()) {
            String line = in.next();
            int[] comLen = getNext(line);
            int mostCommonLen = comLen[line.length() - 1];
            System.out.println(line + (mostCommonLen == line.length() / 2 - 1 ? "" : line.substring(mostCommonLen + 1)));
        }
        in.close();
    }

    private static int[] getNext(String s) {
        int[] next = new int[s.length()];
        int len = s.length();
        next[0] = -1;
        for (int i = 0, k = -1; i < len - 1; ) {
            if (k == -1 || s.charAt(i) == s.charAt(k)) {
                i++;
                k++;
                next[i] = k;
            } else {
                k = next[k];
            }
        }
        return next;
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

