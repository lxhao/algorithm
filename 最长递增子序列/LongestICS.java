import java.util.*;

/**
 * 求最长增序子序列
 */

public class LongestICS {
    public static void main(String args[]) {
        int[] numbers = {1, 4, 6, 2, 8, 9, 7};
        int[] ics = longestICS(numbers);
        for (int e : ics) {
            System.out.print(e + " ");
        }
    }

    /**
     * 返回以每个元素结尾的最长子序列长度
     */
    public static int[] longestICS(int[] numbers) {
        int len = numbers.length;
        int[] res = new int[len];
        for(int i = 0; i < len; i++) {
            res[i] = 1;
        }
        int maxLen = 0;
        for(int i = 0; i < len; i++) {
            for(int j = 0; j < i; j++) {
                if(numbers[i] > numbers[j] ) {
                    res[i] = Math.max(res[i], res[j] + 1);
                }
            }
            maxLen = Math.max(res[i], maxLen);
        }
        int tmp = maxLen;
        for(int i = len - 1; i >= 0; i--) {
            if(res[i] == tmp) {
                System.out.print(numbers[i] + " ");
                tmp--;
            }
        }
        System.out.println("");
        return res;
    }
}
