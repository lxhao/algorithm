package 二叉搜索树与双向链表;


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
 * 输入一棵二叉搜索树，将该二叉搜索树转换成一个排序的双向链表。要求不能创建任何新的结点，
 * 只能调整树中结点指针的指向。
 * <p>
 */
public class Solution {
    public TreeNode Convert(TreeNode pRootOfTree) {
        if (pRootOfTree == null) {
            return null;
        }
        //将左子树转换为链表
        TreeNode front = Convert(pRootOfTree.left);
        TreeNode lastNode = front;
        //遍历到链表的最后一个节点
        while (front != null && front.right != null) {
            front = front.right;
        }
        //将左子树链表与中间节点连接起来
        if (front != null) {
            front.right = pRootOfTree;
            pRootOfTree.left = front;
        }

        //得到右子树的链表
        TreeNode behind = Convert(pRootOfTree.right);
        //连接中间节点和右子数的链表
        if (behind != null) {
            behind.left = pRootOfTree;
            pRootOfTree.right = behind;
        }
        return front == null ? pRootOfTree : lastNode;
    }
}
