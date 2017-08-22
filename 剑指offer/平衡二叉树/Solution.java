package 平衡二叉树;

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
 * 输入一棵二叉树，判断该二叉树是否是平衡二叉树。
 * <p>
 * 递归的搜索树深度,左右子树的高度差不超过1则满足平衡二叉树
 */

public class Solution {
    private int depth = 0;

    public boolean IsBalanced_Solution(TreeNode root) {
        if (root == null) {
            depth = 0;
            return true;
        }
        if (!IsBalanced_Solution(root.left)) {
            return false;
        }
        int leftDepth = depth;
        if (!IsBalanced_Solution(root.right)) {
            return false;
        }
        int rightDepth = depth;
        depth = 1 + Math.max(leftDepth, rightDepth);
        return Math.abs(leftDepth - rightDepth) <= 1;
    }
}