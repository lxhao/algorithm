package 矩形覆盖;

/**
 * 我们可以用2*1的小矩形横着或者竖着去覆盖更大的矩形。
 * 请问用n个2*1的小矩形无重叠地覆盖一个2*n的大矩形，总共有多少种方法？
 * <p>
 * f(n)表示用n个矩形取覆盖2n大矩形的方法数量
 * 第一个矩形竖着放时,剩余部分的方法数量为f(n - 1)
 * 第一个矩形横着放时,还必须有一个矩形横着放,所以剩余部分的方法数量为f(n - 2)
 * 故 f(n) = f(n - 1) + f(n - 2)
 */

public class Solution {
    public static void main(String[] args) {
        System.out.println(new Solution().RectCover(3));
    }

    public int RectCover(int target) {
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