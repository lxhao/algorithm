import java.util.*;

/**
 * 求最长增序子序列
 */

public class LongestICS {
    public static void main(String args[]) {
        int[] numbers = {1, 4, 6, 2, 8, 9, 7};
        int[] ics = longestICS(numbers);
        int maxLen = longestICSLen(numbers);
        System.out.println("最长增序子序列的长度为 : " + maxLen);
        // for (int e : ics) {
        // System.out.print(e + " ");
        // }
    }

    /**
     * 返回以每个元素结尾的最长子序列长度
     */
    public static int[] longestICS(int[] numbers) {
        int len = numbers.length;
        int[] res = new int[len];
        for (int i = 0; i < len; i++) {
            res[i] = 1;
        }
        int maxLen = 0;
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < i; j++) {
                if (numbers[i] > numbers[j] ) {
                    res[i] = Math.max(res[i], res[j] + 1);
                }
            }
            maxLen = Math.max(res[i], maxLen);
        }
        int tmp = maxLen;
        System.out.println("最长增序列的长度为为:" + maxLen);
        System.out.println("最长增序列为:");
        for (int i = len - 1; i >= 0; i--) {
            if (res[i] == tmp) {
                System.out.print(numbers[i] + " ");
                tmp--;
            }
        }
        System.out.println("");
        return res;
    }

    /**
     * nlog(n)复杂度的解法
     */
    public static int longestICSLen(int[] numbers) {
        List<Integer> cache = new ArrayList<>();
        for(int i = 0; i < numbers.length; i++) {
            int low = 0;
            int high = cache.size() - 1;
            int mid;
            while(low <= high) {
                mid = (low + high) >>> 1;
                if(cache.get(mid) < numbers[i]) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
            if(low >= cache.size()) {
                cache.add(numbers[i]);
            }  else if (cache.get(low) < numbers[i]) {
                cache.set(low + 1, numbers[i]);
            } else {
                cache.set(low, numbers[i]);
            }
        }
        return cache.size();
    }
}
