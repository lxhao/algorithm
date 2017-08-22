package 青蛙跳台阶;

public class Solution {
    public static void main(String[] args) {
        System.out.println(new Solution().JumpFloor(3));
    }
    public int JumpFloor(int target) {
        if (target <= 0) {
            return 0;
        }

        int first = 0;
        int second = 1;
        for (int i = 0; i < target; i++) {
            second += first;
            first = second - first;
        }
        return second;
    }
}