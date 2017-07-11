public class Solution {
    public static void main(String[] args) {
        System.out.println(new Solution().GetUglyNumber_Solution(7));
    }

    public int GetUglyNumber_Solution(int index) {
        if (index < 1) {
            return 0;
        }
        //保存丑数
        int[] uglyNums = new int[index];
        //第一个丑数为1
        uglyNums[0] = 1;

        int pos2 = 0;
        int pos3 = 0;
        int pos5 = 0;

        int pos = 0;
        while (pos + 1 < index) {
            uglyNums[++pos] = min(uglyNums[pos2] * 2, uglyNums[pos3] * 3, uglyNums[pos5] * 5);

            while (uglyNums[pos2] * 2 <= uglyNums[pos]) {
                pos2++;
            }
            while (uglyNums[pos3] * 3 <= uglyNums[pos]) {
                pos3++;
            }
            while (uglyNums[pos5] * 5 <= uglyNums[pos]) {
                pos5++;
            }
        }
        return uglyNums[index - 1];
    }

    private int min(int n1, int n2, int n3) {
        n1 = Math.min(n1, n2);
        return Math.min(n1, n3);
    }
}
