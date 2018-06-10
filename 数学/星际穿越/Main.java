package 星际穿越;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * https://www.nowcoder.com/practice/53e4c208b8cf497086ecd65ef45349bb?tpId=85&tqId=29835&rp=1&ru=/ta/2017test&qru=/ta/2017test/question-ranking
 */

public class Main {

    public static void main(String[] args) throws IOException {
        try {
            System.setIn(new FileInputStream("input.txt"));
        } catch (FileNotFoundException ignored) {
        }
        Scanner in = new Scanner(System.in);
        long n = in.nextLong();
        int ans = (int) Math.floor((-1 + Math.sqrt(1 + 4 * n)) / 2);
        System.out.println(ans);
    }
}
