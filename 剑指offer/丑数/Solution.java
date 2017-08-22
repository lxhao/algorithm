package 丑数;

/**
 * 题目描述
 * 把只包含因子2、3和5的数称作丑数（Ugly Number）。
 * 例如6、8都是丑数，但14不是，因为它包含因子7。 习惯上我们把1当做是第一个丑数。
 * 求按从小到大的顺序的第N个丑数。
 * <p>
 * 用一个数组保存已经生成的丑数,下一个丑数肯定是前面已经生成的丑数乘以2,3,5后,在大于最后一个丑数的数中取最小值
 * 所以,我们只需要保存三个索引,分别表示乘以2,3,5大于最后一个丑数的位置,每次取三个数的最小值,同时更新三个索引的位置
 */
public class Solution {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.GetUglyNumber_Solution(12));
    }

    public int GetUglyNumber_Solution(int index) {
        if (index <= 0) {
            return 0;
        }
        int[] uglyNumbers = new int[index];
        uglyNumbers[0] = 1;
        int num2 = 0, num3 = 0, num5 = 0;
        for (int i = 1; i < index; i++) {
            uglyNumbers[i] = min(uglyNumbers[num2] * 2, uglyNumbers[num3] * 3, uglyNumbers[num5] * 5);
            if (uglyNumbers[num2] * 2 <= uglyNumbers[i]) {
                num2++;
            }
            if (uglyNumbers[num3] * 3 <= uglyNumbers[i]) {
                num3++;
            }
            if (uglyNumbers[num5] * 5 <= uglyNumbers[i]) {
                num5++;
            }
        }
        return uglyNumbers[index - 1];
    }

    private int min(int n1, int n2, int n3) {
        n1 = Math.min(n1, n2);
        return Math.min(n1, n3);
    }
}