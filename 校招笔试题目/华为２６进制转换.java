import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class 华为２６进制转换 {
    public static void main(String[] args) {
//        Scanner in = getScanner(System.in);
        Scanner in = getScanner("input.txt");
        while (in.hasNext()) {
            String line = in.nextLine();
            if (line.charAt(0) <= 'z' && line.charAt(0) >= 'a') {
                System.out.println(getNumValue(line));
            } else {
                System.out.println(getStrValue(line));
            }
        }
        in.close();
    }

    private static String getStrValue(String line) {
        StringBuilder sb = new StringBuilder();
        int value;
        try {
            value = Integer.valueOf(line);
        } catch (NumberFormatException e) {
            return "ERROR";
        }
        while (value != 0) {
            sb.append((char)(value % 26 + 'a' - 1));
            value = value / 26;
        }
        return sb.reverse().toString();
    }

    private static String getNumValue(String line) {
        long res = 0;
        boolean isRight = true;
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) <= 'z' && line.charAt(i) >= 'a') {
                res *= 26;
                res += line.charAt(i) - 'a' + 1;
            } else {
                isRight = false;
                break;
            }
        }
        return (isRight ? String.valueOf(res) : "ERROR");
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

