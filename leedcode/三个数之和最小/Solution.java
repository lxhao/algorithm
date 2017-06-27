
import java.util.Arrays;

/**
 *
 * 求最接近target的三个数之和
 */
public class Solution {

    public static void main(String args[]) {
        int[] num = {0, 1, 2};
        int res = new Solution().threeSumClosest(num, 10);
        System.out.println(res);
    }

    public int threeSumClosest(int[] num, int target) {
        Arrays.sort(num);
        int sum = num[0] + num[1] + num[2];
        int min = Math.abs(sum - target);
        int res = sum;
        int start, end;
        for (int i = 0; i < num.length - 2; i++) {
            start = i + 1;
            end = num.length - 1;
            while (start < end) {
                sum = num[i] + num[start] + num[end];
                if (Math.abs(sum - target) < min) {
                    min = Math.abs(sum - target);
                    res = sum;
                }
                if (sum > target) {
                    end--;
                } else if (sum < target) {
                    start++;
                } else {
                    return 0;
                }
            }
        }
        return res;
    }
}

