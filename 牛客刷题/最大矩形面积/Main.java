package 最大矩形面积;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

/**
 * 链接：https://www.nowcoder.com/questionTerminal/13ba51c3fec74b58bbc8fa8c3eedf877
 * 来源：牛客网
 * <p>
 * 有一个直方图，用一个整数数组表示，其中每列的宽度为1，求所给直方图包含的最大矩形面积。比如，对于直方图[2,7,9,4],它所包含的最大矩形的面积为14(即[7,9]包涵的7x2的矩形)。
 * 给定一个直方图A及它的总宽度n，请返回最大矩形面积。保证直方图宽度小于等于500。保证结果在int范围内
 * <p>
 * 单调栈和线段树使用
 */

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        System.setIn(new FileInputStream("input.txt"));
        Scanner in = new Scanner(System.in);
        MaxInnerRec maxInnerRec = new MaxInnerRec();
        while (in.hasNext()) {
            int n = in.nextInt();
            int[] numbers = new int[n];
            for (int i = 0; i < n; i++) {
                numbers[i] = in.nextInt();
            }
            int ans = maxInnerRec.countArea(numbers, n);
            System.out.println(ans);
        }
        in.close();
    }
}

class MaxInnerRec {
    public int countArea(int[] A, int n) {
        Stack<Integer> stack = new Stack<>();
        int maxArea = Integer.MIN_VALUE;
        for (int i = 0; i < A.length; i++) {
            while (!stack.isEmpty() && A[stack.peek()] > A[i]) {
                // 当元素出栈时，计算以该位置为长方形高时的最大面积
                int h = A[stack.pop()];
                int index = stack.isEmpty() ? -1 : stack.peek();
                maxArea = Math.max(maxArea, h * (i - index - 1));
            }
            stack.push(i);
        }
        return maxArea;
    }
}

