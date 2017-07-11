import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = getScanner(System.in);
//        Scanner in = getScanner("input.txt");
        List<Integer> numbers = new ArrayList<>();
        while (in.hasNext()) {
            numbers.clear();
            int n = in.nextInt();
            for (int i = 0; i < n; i++) {
                numbers.add(in.nextInt());
            }

            int res = Integer.MIN_VALUE;
            int min;
            for (int i = 0; i < numbers.size(); i++) {
                min = Integer.MAX_VALUE;
                for (int j = i; j < numbers.size(); j++) {
                    min = Math.min(min, numbers.get(j));
                    res = Math.max(res, (j - i + 1) * min);
                }
            }
            System.out.println(res);
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

