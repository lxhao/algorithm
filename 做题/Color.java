import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class Color {
    public static void main(String[] args) {
        Scanner in = getScanner(System.in);
//        Scanner in = getScanner("input.txt");
        while (in.hasNext()) {
            String x1 = in.next();
            int k1 = in.nextInt();
            String x2 = in.next();
            int k2 = in.nextInt();
            StringBuilder sb1 = new StringBuilder(Integer.parseInt(x1));
            for (int i = 0; i < k1 - 1; i++) {
                sb1.append(x1);
            }
            StringBuilder sb2 = new StringBuilder(Integer.parseInt(x2));
            for (int i = 0; i < k2 - 1; i++) {
                sb2.append(x2);
            }
            int comp = sb1.toString().compareTo(sb2.toString());
            if (comp < 0) {
                System.out.println("Less");
            } else if (comp == 0) {
                System.out.println("Equal");
            } else {
                System.out.println("Greater");
            }
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

