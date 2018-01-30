package 吐泡泡;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        System.setIn(new FileInputStream("input.txt"));
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            String line = in.nextLine();
            System.out.println(solve(line));
        }
        in.close();
    }

    private static String solve(String line) {
        Stack<Character> res = new Stack<>();
        for (int i = 0; i <= line.length(); ) {
            if (res.size() >= 2) {
                char a = res.pop();
                char b = res.pop();
                if (a == 'o' && b == 'o') {
                    res.push('O');
                    continue;
                }
                if (a == 'O' && b == 'O') {
                    continue;
                } else {
                    res.push(b);
                    res.push(a);
                }
            }
            if (i < line.length()) {
                res.push(line.charAt(i));
            }
            i++;
        }

        StringBuilder resStr = new StringBuilder();
        for (Character c : res) {
            resStr.append(c);
        }
        return resStr.toString();
    }
}

