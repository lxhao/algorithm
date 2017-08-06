/**
 * Given two words word1 and word2, find the minimum number of steps required to convert word1 to word2. (each operation is counted as 1 step.)
 * You have the following 3 operations permitted on a word:
 * a) Insert a character
 * b) Delete a character
 * c) Replace a character
 */
public class Solution {
    public static void main(String[] args) {
        String word1 = "ab";
        String word2 = "bc";
        System.out.println(new Solution().minDistance(word1, word2));
    }

    public int minDistance(String word1, String word2) {
        if (word1 == null && word2 == null) {
            return 0;
        }

        if (word1 == null || word1.length() == 0) {
            return word2.length();
        }

        if (word2 == null || word2.length() == 0) {
            return word1.length();
        }

        int word1Len = word1.length();
        int word2Len = word2.length();
        int[][] res = new int[word1Len + 1][word2Len + 1];

        //一个字符串为空的值
        for (int i = 0; i <= word1Len; i++) {
            res[i][0] = i;
        }
        for (int i = 0; i <= word2Len; i++) {
            res[0][i] = i;
        }
        /**
         * res[i][j]表示长度为word1.subString(0, i + 1)转为word2.subString(0, j + 1)
         * 的最少步数
         */
        for (int i = 1; i <= word1Len; i++) {
            for (int j = 1; j <= word2Len; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    res[i][j] = res[i - 1][j - 1];
                } else {
                    //插入,删除和修改的最小的操作步数
                    res[i][j] = Math.min(res[i][j - 1], res[i - 1][j]) + 1;
                    res[i][j] = Math.min(res[i][j], res[i - 1][j - 1] + 1);
                }
            }
        }
        return res[word1Len][word2Len];
    }
}