package Manacher找最长回文串;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * http://www.61mon.com/index.php/archives/181/
 */
public class Manacher {
    public static void main(String[] args) {
//        Scanner in = getScanner(System.in);
        Scanner in = getScanner("input.txt");
        while (in.hasNext()) {
            String line = in.next();
            System.out.println(manacher(line));
        }
        in.close();
    }

    private static int manacher(String s) {
        //插入#,奇偶统一处理
        char[] chars = new char[s.length() * 2 + 1];
        for (int i = 0, index = 0; i < s.length(); i++) {
            chars[index++] = '#';
            chars[index++] = s.charAt(i);
        }
        chars[chars.length - 1] = '#';

        int[] len = new int[chars.length];
        int maxRight = 0;
        int index = 0;
        int maxLen = 0;
        for (int i = 0; i < chars.length; i++) {
            if (i < maxRight) {
                len[i] = Math.min(len[2 * index - i], maxRight - i);
            }
            while (i + len[i] < chars.length && i - len[i] >= 0 && chars[i + len[i]] == chars[i - len[i]]) {
                len[i]++;
            }
            if (len[i] + i > maxRight) {
                maxRight = len[i] + i;
                index = i;
            }
            maxLen = Math.max(maxLen, len[i]);
        }
        return maxLen - 1;
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

