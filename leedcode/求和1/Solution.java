package 求和1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Solution {

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] candidates = new int[]{2, 3, 5, 7, 8};
        ArrayList<ArrayList<Integer>> res = solution.combinationSum(candidates, 7);
        for (ArrayList<Integer> e : res) {
            System.out.println(e);
        }
    }

    public ArrayList<ArrayList<Integer>> combinationSum(int[] candidates, int target) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        Arrays.sort(candidates);
        bfs(candidates, 0, target, new LinkedList<>(), res);
        return res;
    }

    private void bfs(int[] candidates, int curPos, int target, LinkedList<Integer> midRes,
        ArrayList<ArrayList<Integer>> res) {
        if (target < 0) {
            return;
        }
        if (target == 0) {
            res.add(new ArrayList<>(midRes));
            return;
        }

        for (int i = curPos; i < candidates.length; i++) {
            midRes.addLast(candidates[i]);
            bfs(candidates, i, target - candidates[i], midRes, res);
            midRes.removeLast();
        }
    }
}