import java.util.*;

/**
 * 求最长增序子序列
 */

public class LongestICS {
    public static void print(int[] arr) {
        for (int e : arr) {
            System.out.printf("%d ", e);
        }
        System.out.printf("\n");
    }

    public static void main(String args[]) {
        int[] numbers = {1, 1, 2, 2, 3, 8, 9, 7};
        System.out.println("输入数据为：");
        print(numbers);
        int[] ics = longestICS(numbers);
        int maxIcsLen = ics[0];
        System.out.println("增续数组为：");
        print(ics);
        for (int e : ics) {
            maxIcsLen = Math.max(maxIcsLen, e);
        }
        System.out.println("最长增序子序列的长度为 : " + maxIcsLen);

        List<int[]> allIcsNumbers = new ArrayList<>();
        allIcsNumbers.add(new int[maxIcsLen]);
        for (int i = maxIcsLen - 1; i < ics.length; i++) {
            if (ics[i] == maxIcsLen) {
                allIcsNumbers.get(0)[maxIcsLen - 1] = numbers[i];
                getAllICSNumers(numbers, ics, allIcsNumbers, maxIcsLen - 1, i);
            }
        }
        allIcsNumbers.remove(0);
        for (int i = 0; i < allIcsNumbers.size(); i++) {
            System.out.printf("增序数列结果%d为:\n", i + 1);
            print(allIcsNumbers.get(i));
        }
    }

    public static void getAllICSNumers(int[] numbers, int[] ics, List<int[]> res, int maxIcsLen, int nowPos) {
        if (maxIcsLen == 0) {
            res.add(res.get(0).clone());
            return;
        }
        for (int i = nowPos - 1; i >= maxIcsLen - 1; i--) {
            if (ics[i] == maxIcsLen && numbers[i] < numbers[nowPos]) {
                res.get(0)[maxIcsLen - 1] = numbers[i];
                getAllICSNumers(numbers, ics, res, maxIcsLen - 1, i);
            }
        }
    }


    public static int[] getICSNumers(int[] numbers, int[] ics) {
        int maxIcsLen = ics[0];
        for (int e : ics) {
            maxIcsLen = Math.max(maxIcsLen, e);
        }
        int[] res = new int[maxIcsLen];
        int index = res.length - 1;
        for (int i = ics.length - 1; i >= 0; i--) {
            if (ics[i] == maxIcsLen) {
                res[index--] = numbers[i];
                maxIcsLen--;
            }
        }
        return res;
    }

    /**
     * 返回以每个元素结尾的最长子序列长度
     */
    public static int[] longestICS(int[] numbers) {
        int len = numbers.length;
        int[] icsLen = new int[len];
        for (int i = 0; i < len; i++) {
            icsLen[i] = 1;
        }

        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++ ) {
                if (numbers[i] > numbers[j]) {
                    icsLen[i] = Math.max(icsLen[i], icsLen[j] + 1);
                }
            }
        }
        return icsLen;
    }

    /**
     * nlog(n)复杂度的解法
     */
    public static int longestICSLen(int[] numbers) {
        List<Integer> cache = new ArrayList<>();
        for (int i = 0; i < numbers.length; i++) {
            int low = 0;
            int high = cache.size() - 1;
            int mid;
            while (low <= high) {
                mid = (low + high) >>> 1;
                if (cache.get(mid) < numbers[i]) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
            if (low >= cache.size()) {
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
