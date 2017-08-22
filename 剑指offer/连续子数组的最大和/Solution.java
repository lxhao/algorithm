package 连续子数组的最大和;

/**
 * 题目描述
 * HZ偶尔会拿些专业问题来忽悠那些非计算机专业的同学。今天测试组开完会后,他又发话了:
 * 在古老的一维模式识别中,常常需要计算连续子向量的最大和,当向量全为正数的时候,问题很好解决。
 * 但是,如果向量中包含负数,是否应该包含某个负数,并期望旁边的正数会弥补它呢？
 * 例如:{6,-3,-2,7,-15,1,2,2},连续子向量的最大和为8(从第0个开始,到第3个为止)。你会不会被他忽悠住？
 * (子向量的长度至少是1)
 * <p>
 * 一个遍历保存结果,一个变量保存数组和,直接遍历数组元素并累加,如果大于0,与结果比较是否需要更新,
 * 如果小于0,把数组和置0
 */
public class Solution {
    public static void main(String[] args) {
        int[] array = {1, -2, 3, 10, -4, 7, 2, -5};
        System.out.println(new Solution().FindGreatestSumOfSubArray(array));
    }

    public int FindGreatestSumOfSubArray(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        int res = 0;
        int sum = Integer.MIN_VALUE;
        for (int e : array) {
            res += e;
            sum = Math.max(sum, res);
            if (res < 0) {
                res = 0;
            }
        }
        return sum;
    }
}