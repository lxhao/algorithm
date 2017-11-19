package 树的子结构;

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
 * 输入两棵二叉树A，B，判断B是不是A的子结构。（ps：我们约定空树不是任意一个树的子结构）
 * <p>
 * 遍历A树找到和B树根节点相等的节点,再递归去判断剩下的节点是否相等
 */
public class Solution {

    public boolean HasSubtree(TreeNode root1, TreeNode root2) {
        if (root2 == null || root1 == null) {
            return false;
        }
        return subtree(root1, root2) || HasSubtree(root1.left, root2) || HasSubtree(root1.right, root2);
    }

    public boolean subtree(TreeNode root1, TreeNode root2) {
        if (root2 == null) {
            return true;
        }
        if (root1 == null) {
            return false;
        }
        if (root1.val == root2.val) {
            return subtree(root1.left, root2.left) && subtree(root1.right, root2.right);
        }
        return false;
    }
}