package 二叉树中和为某一值的路径;

import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;

import java.util.ArrayList;
import java.util.List;

class TreeNode {
    int val = 0;
    TreeNode left = null;
    TreeNode right = null;

    public TreeNode(int val) {
        this.val = val;

    }

}

/**
 * 题目描述
 * 输入一颗二叉树和一个整数，打印出二叉树中结点值的和为输入整数的所有路径。
 * 路径定义为从树的根结点开始往下一直到叶结点所经过的结点形成一条路径。
 * <p>
 * 深度优先遍历树,走过节点时调整target,并保存走过的节点,如果到叶子节点时发现target等于叶子节点的值,
 * 把当前走过的路径加入到结果
 */
public class Solution {
    public ArrayList<ArrayList<Integer>> FindPath(TreeNode root, int target) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        ArrayList<Integer> walked = new ArrayList<>();
        if (root == null) {
            return res;
        }
        FindPath(root, target, walked, res);
        return res;
    }

    public void FindPath(TreeNode root, int target, List<Integer> walked, ArrayList<ArrayList<Integer>> res) {
        if (root == null || target < 0) {
            return;
        }
        walked.add(root.val);
        //到达叶子节点,当前节点的val等于target,加入到结果数组里面
        if (root.right == null && root.left == null && target == root.val) {
            res.add(new ArrayList<>(walked));
            walked.remove(walked.size() - 1);
            return;
        }
        if (root.left != null) {
            FindPath(root.left, target - root.val, walked, res);
        }
        if (root.right != null) {
            FindPath(root.right, target - root.val, walked, res);
        }
        walked.remove(walked.size() - 1);
    }
}