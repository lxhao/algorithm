package 覆盖子串的最小区间;

/**
 * 1. Use two pointers: start and end to represent a window.
 * 2. Move end to find a valid window.
 * 3. When a valid window is found, move start to find a smaller window.
 * To check if a window is valid, we use a map to store (char, count) for chars in t. And use counter for the number of chars of t to be found in s. The key part is m[s[end]]--;. We decrease count for each char in s. If it does not exist in t, the count will be negative.
 *  
 * To really understand this algorithm, please see my code which is much clearer, because there is no code like if(map[s[end++]]++>0) counter++;.
 */
public class Solution {
    public static void main(String[] args) {
        String S = "acbba";
        String T = "aab";
        String res = new Solution().minWindow(S, T);
        System.out.println(res);
    }

    public String minWindow(String s, String t) {
        int[] charMap = new int[128];
        for (int i = 0; i < t.length(); i++) {
            charMap[t.charAt(i)]++;
        }

        int start = 0;
        int counter = 0;
        int minStart = -1;
        int minEnd = s.length();
        for (int end = 0; end < s.length(); end++) {
            if (charMap[s.charAt(end)] > 0) {
                counter++;
            }
            charMap[s.charAt(end)]--;

            while (counter == t.length()) {
                if (minEnd - minStart > (end - start)) {
                    minEnd = end;
                    minStart = start;
                }
                charMap[s.charAt(start)]++;
                if (charMap[s.charAt(start)] > 0) {
                    counter--;
                }
                start++;
            }
        }
        return minStart == -1 ? "" : s.substring(minStart, minEnd + 1);
    }
}
