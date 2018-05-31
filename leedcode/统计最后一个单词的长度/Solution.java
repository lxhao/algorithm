package 统计最后一个单词的长度;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) throws IOException {
        List<Character> chars = new ArrayList<>();
        InputStream is = System.in;
        while (is.available() > 0) {
            char c = (char) is.read();
            if (c == ' ') {
                chars.clear();
            } else if (c == '\n') {
                System.out.println(chars.size());
            } else {
                chars.add(c);
            }
        }
        chars.clear();
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


