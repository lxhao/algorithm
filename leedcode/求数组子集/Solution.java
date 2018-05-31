package 求数组子集;

import java.util.*;

/**
 * 子集元素数量为0到S的length
 * 遍历对每种情况求解
 */
public class Solution {
    public static void main(String[] args) {
        int[] data = {4, 1, 0};
        System.out.println(new Solution().subsets(data));
    }

    public ArrayList<ArrayList<Integer>> subsets(int[] S) {
        Arrays.sort(S);
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        ArrayList<Integer> resTmp = new ArrayList<>();
        res.add(new ArrayList<Integer>());
        for (int i = 1; i <= S.length; i++) {
            getSubset(0, S, i, res, resTmp);
        }
        return res;
    }

    /**
     * 使用回溯的思想求组合情况
     *
     * @param startPos
     * @param s
     * @param subsetSize
     * @param res
     * @param resTmp
     */
    private void getSubset(int startPos, int[] s, int subsetSize, ArrayList<ArrayList<Integer>> res, ArrayList<Integer> resTmp) {
        if (subsetSize == 0) {
            ArrayList<Integer> t = new ArrayList<>(resTmp);
            Collections.sort(t);
            res.add(t);
            return;
        }
        for (int i = startPos; i < s.length - subsetSize + 1; i++) {
            resTmp.add(s[i]);
            getSubset(i + 1, s, subsetSize - 1, res, resTmp);
            resTmp.remove(Integer.valueOf(s[i]));
        }
    }
}
