package 栈的压入弹出序列;

import java.util.Stack;

/**
 * 题目描述
 * 输入两个整数序列，第一个序列表示栈的压入顺序，请判断第二个序列是否为该栈的弹出顺序。
 * 假设压入栈的所有数字均不相等。例如序列1,2,3,4,5是某栈的压入顺序，
 * 序列4，5,3,2,1是该压栈序列对应的一个弹出序列，但4,3,5,1,2就不可能是该压栈序列的弹出序列。
 * （注意：这两个序列的长度是相等的）
 * <p>
 * 遍历push数组的元素一个个放入栈,每次入栈后判断栈顶元素是否和pop数组当前的元素相等
 */
public class Solution {
    public static void main(String[] args) {
        int[] pushA = {1, 2, 3, 4, 5};
        int[] popA = {4, 5, 3, 2, 1};
        System.out.println(new Solution().IsPopOrder(pushA, popA));
    }

    public boolean IsPopOrder(int[] pushA, int[] popA) {
        if (pushA == null || popA == null) {
            return false;
        }
        Stack<Integer> stack = new Stack<>();

        int pushPos = 0;
        int popPos = 0;
        for (; pushPos < pushA.length && popPos < popA.length; ) {
            stack.push(pushA[pushPos++]);
            while (!stack.isEmpty() && stack.lastElement() == popA[popPos]) {
                popPos++;
                stack.pop();
            }
        }
        return popPos == popA.length && stack.empty();
    }
}