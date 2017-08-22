package 斐波那契数列;

public class Solution {
    public static void main(String[] args) {
        System.out.println(new Solution().Fibonacci(4));
    }

    public int Fibonacci(int n) {
        if (n <= 0) {
            return 0;
        }
        int first = 0;
        int second = 1;
        for (int i = 0; i < n - 1; i++) {
            second += first;
            first = second - first;
        }
        return second;
    }
}