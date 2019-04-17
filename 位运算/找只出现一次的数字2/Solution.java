package 找只出现一次的数字2;

public class Solution {
    public int singleNumber(int[] A) {
        int tmp = 0;
        for(int e: A) {
            tmp ^= e;
        }
        return tmp;
    }

    public static void main(String[] args) {
        int[] A = new int[]{1, 2, 2, 3, 3};
        Solution solution = new Solution();
        int single = solution.singleNumber(A);
        System.out.println(single);
    }
}