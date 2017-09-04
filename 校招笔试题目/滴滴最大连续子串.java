import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class 滴滴最大连续子串 {
    public static void main(String[] args) {
        Scanner in = getScanner(System.in);
        List<Integer> numbers = new ArrayList<>();
//        Scanner in = getScanner("input.txt");
        while (in.hasNext()) {
            numbers.add(in.nextInt());
        }
        System.out.println(maxSum(numbers));
        in.close();
    }

    private static int maxSum(List<Integer> arr) {
        int res = Integer.MIN_VALUE;
        int tmp = 0;
        for (int e : arr) {
            tmp += e;
            res = Math.max(res, tmp);
            tmp = tmp < 0 ? 0 : tmp;
        }
        return res;
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

