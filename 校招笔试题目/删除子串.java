import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.Stack;

public class 删除子串 {
    public static void main(String[] args) {
//        Scanner in = getScanner(System.in);
        Scanner in = getScanner("input.txt");
        while (in.hasNext()) {
            String a = in.nextLine();
            String b = in.nextLine();
            System.out.println(delSubString(a, b));
        }
        in.close();
    }

    static class Node {
        int val;
        int pos;

        Node(int val, int pos) {
            this.val = val;
            this.pos = pos;
        }
    }

    public static String delSubString(String a, String b) {
        //栈里面留下的就是结果
        Stack<Node> stack = new Stack<>();
        for (int i = 0, j = 0; i < a.length(); i++) {
            // 当前位置能直接匹配
            if (a.charAt(i) == b.charAt(j)) {
                stack.push(new Node(a.charAt(i), j));
                j++;
                // 匹配到最后一个元素，弹出元素
                if (j == b.length()) {
                    for (int k = 0; k < b.length(); k++) {
                        stack.pop();
                    }
                    j = stack.isEmpty() ? 0 : (stack.peek().pos + 1);
                }
            //当前位置能跟b的起始位置匹配
            } else if (a.charAt(i) == b.charAt(0)) {
                stack.push(new Node(a.charAt(i), 0));
                j = 1;
            } else {
                stack.push(new Node(a.charAt(i), -1));
                j = 0;
            }
        }
        StringBuilder sb = new StringBuilder();
        for (Node e : stack) {
            sb.append((char) e.val);
        }
        return sb.toString();
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

