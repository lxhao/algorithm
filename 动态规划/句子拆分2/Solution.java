package 句子拆分2;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Solution {
    public boolean wordBreak(String s, Set<String> dict) {
        return wordBreak(s, dict, new HashMap<>());
    }

    public boolean wordBreak(String s, Set<String> dict, HashMap<String, Boolean> res) {
        if (s.length() == 0) {
            return true;
        }
        if (res.containsKey(s)) {

            return res.get(s);
        }

        for (int i = 0; i < s.length(); i++) {
            String subStr = s.substring(0, i + 1);
            if (dict.contains(subStr)) {
                if (wordBreak(s.substring(i + 1), dict, res)) {
                    res.put(s, true);
                    return true;
                }
            }
        }
        res.put(s, false);
        return false;
    }

    public static void main(String[] args) {
        String s = "catsanddogcatsanddogcatsanddog";
        Set<String> set = new HashSet<>();
        String[] setArr = {"cat", "cats", "and", "sand", "dog"};
        Collections.addAll(set, setArr);
        Solution solution = new Solution();
        boolean res = solution.wordBreak(s, set);
        System.out.println(res);
    }
}