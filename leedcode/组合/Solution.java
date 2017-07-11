import java.util.*;

public class Solution {
    public static void main(String[] args) {
        ArrayList<ArrayList<Integer>> res = new Solution().combine(4, 2);
        System.out.println(res);
    }

    public ArrayList<ArrayList<Integer>> combine(int n, int k) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        ArrayList<Integer> tmp = new ArrayList<>();
        combine(n, k, 0, tmp, res);
        return res;
    }

    private void combine(int n, int k, int nowPos, ArrayList<Integer> tmp, ArrayList<ArrayList<Integer>>  res) {
        if (k == 0) {
            res.add(new ArrayList<>(tmp));
            return;
        }

        for (int i = nowPos; i < n - k + 1; i++) {
            tmp.add(i + 1);
            combine(n, k - 1, i + 1, tmp, res);
            tmp.remove(tmp.size() - 1);
        }
    }
}
