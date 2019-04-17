package 找只出现一次的数字;

public class Solution {
    public int singleNumber(int[] A) {
        int one = 0;
        int two = 0;
        int three = 0;
        for (int e : A) {
            two &= e;
            one &= two;
            three = (two & one);
            one &= ~three;
            two &= ~three;
        }
        return one;
    }

    public static void main(String[] args) {
        int[] A = new int[]{1, 2, 2, 2, 3, 3, 3};
        Solution solution = new Solution();
        int single = solution.singleNumber(A);
        System.out.println(single);
    }
}