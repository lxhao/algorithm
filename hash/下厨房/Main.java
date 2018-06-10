package 下厨房;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * https://www.nowcoder.com/practice/ca5c9ba9ebac4fd5ae9ba46114b0f476?tpId=85&tqId=29832&tPage=1&rp=1&ru=%2Fta%2F2017test&qru=%2Fta%2F2017test%2Fquestion-ranking
 */

public class Main {
    public static void main(String[] args) {
        try {
            System.setIn(new FileInputStream("input.txt"));
        } catch (FileNotFoundException ignored) {
        }
        Scanner in = new Scanner(System.in);
        Set<String> set = new HashSet<>();
        while (in.hasNext()) {
            set.add(in.next());
        }
        System.out.println(set.size());
    }
}
