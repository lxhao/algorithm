import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class 网易反转数字 {
    public static void main(String[] args) {
        Scanner in = getScanner(System.in);
//        Scanner in = getScanner("input.txt");
        while (in.hasNext()) {
            String line = in.nextLine().trim();
            boolean isNegative = false;
            if (line.charAt(0) == '-') {
                isNegative = true;
            }
            String reverse = new StringBuilder(line).reverse().toString();
            if (isNegative) {
                line = line.substring(1, reverse.length());
                reverse = reverse.substring(0, reverse.length() - 1);
            }

            long res = Integer.parseInt(line) + Integer.parseInt(reverse);
            System.out.println((isNegative ? "-" : "") + res);
        }
        in.close();
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

