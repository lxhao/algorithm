package 句子拆分2.句子拆分;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Solution {
    private ArrayList<String> getSentences(List<ArrayList<String>> dp, int pos) {
        ArrayList<String> res = new ArrayList<>();
        for (String word : dp.get(pos)) {
            // 到达第一个单词
            if (pos - word.length() == 0) {
                res.add(word);
                continue;
            }
            // 前面单词拼成的句子
            ArrayList<String> preSentence = getSentences(dp, pos - word.length());
            for (String sentence : preSentence) {
                res.add(sentence + " " + word);
            }
        }
        return res;
    }

    public ArrayList<String> wordBreak(String s, Set<String> dict) {
        List<ArrayList<String>> dp = new ArrayList<>(s.length() + 1);
        for (int i = 0; i < s.length() + 1; i++) {
            dp.add(new ArrayList<>());
        }

        for (int i = 1; i <= s.length(); i++) {
            for (int j = i; j >= 0 && i - j < 20; j--) {
                String subStr = s.substring(j, i);
                if (dict.contains(subStr)) {
                    dp.get(i).add(subStr);
                }
            }
        }
        return getSentences(dp, s.length());
    }


    public static void main(String[] args) {
        String s = "catsanddogcatsanddogcatsanddog";
        Set<String> set = new HashSet<>();
        String[] setArr = {"cat", "cats", "and", "sand", "dog"};
        for (String t : setArr) {
            set.add(t);
        }
        Solution solution = new Solution();
        List<String> res = solution.wordBreak(s, set);
        System.out.println(res);
    }
}