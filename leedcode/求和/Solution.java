package 求和;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {

    public static void main(String[] args) {
        int[] num = {10, 1, 2, 5, 7, 6, 1};
        int target = 8;
        Solution solution = new Solution();
        ArrayList<ArrayList<Integer>> res = solution.combinationSum2(num, target);
        for (List<Integer> e : res) {
            System.out.println(e);
        }
    }

    public void combinationSum2(int[] num, int target, int curPos, List<Integer> tmpRes,
        ArrayList<ArrayList<Integer>> finalRes) {
        if (target < 0) {
            return;
        }
        if (target == 0) {
            if (!finalRes.contains(tmpRes)) {
                finalRes.add(new ArrayList<>(tmpRes));
            }
            return;
        }
        for (int i = curPos; i < num.length; i++) {
            tmpRes.add(num[i]);
            combinationSum2(num, target - num[i], i + 1, tmpRes, finalRes);
            tmpRes.remove(tmpRes.size() - 1);
        }
    }

    public ArrayList<ArrayList<Integer>> combinationSum2(int[] num, int target) {
        ArrayList<ArrayList<Integer>> finalRes = new ArrayList<>();
        Arrays.sort(num);
        combinationSum2(num, target, 0, new ArrayList<>(num.length), finalRes);
        return finalRes;
    }
}