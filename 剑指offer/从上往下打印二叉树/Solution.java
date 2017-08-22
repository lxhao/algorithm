package 从上往下打印二叉树;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

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
 * 从上往下打印出二叉树的每个节点，同层节点从左至右打印。
 * <p>
 * 用一个队列保存树节点,首先把根节点入队,然后每次从队列头部取节点,并把节点的左右节点加入到队列尾部
 */
public class Solution {
    public ArrayList<Integer> PrintFromTopToBottom(TreeNode root) {
        ArrayList<Integer> list = new ArrayList<>();
        if (root == null) {
            return list;
        }
        Deque<TreeNode> deque = new ArrayDeque<>();
        deque.addLast(root);
        while (!deque.isEmpty()) {
            TreeNode treeNode = deque.pollFirst();
            list.add(treeNode.val);
            if (treeNode.left != null) {
                deque.addLast(treeNode.left);
            }
            if (treeNode.right != null) {
                deque.addLast(treeNode.right);
            }
        }
        return list;
    }
}