package 包含min函数的栈;

import java.util.Stack;

/**
 * 题目描述
 * 定义栈的数据结构，请在该类型中实现一个能够得到栈最小元素的min函数。
 * <p>
 * <p>
 * 单独用一个栈保存最小值
 * 用一个变量保存当前最小值,push元素时更新最小值并把最小值加入栈
 * pop时把元素和最小值都移出栈
 */

public class Solution {

    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.push(1);
        solution.push(3);
        solution.push(2);
        System.out.println(solution.top());
        solution.pop();
        System.out.println(solution.top());
        System.out.println(solution.min());
    }

    private int min = Integer.MAX_VALUE;
    private Stack<Integer> minStack = new Stack<>();
    private Stack<Integer> numbers = new Stack<>();


    public void push(int node) {
        numbers.push(node);
        min = Math.min(min, node);
        minStack.push(min);
    }

    public void pop() {
        minStack.pop();
        numbers.pop();
    }

    public int top() {
        return numbers.lastElement();
    }

    public int min() {
        return minStack.lastElement();
    }
}