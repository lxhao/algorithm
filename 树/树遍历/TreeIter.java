package 树遍历;

import java.util.*;

public class TreeIter<T> {

    /**
     * 层次遍历，用队列实现
     *
     * @param root
     * @return
     */
    public List<T> levelOrder(TreeNode<T> root) {
        List<T> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        Queue<TreeNode<T>> queue = new ArrayDeque<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode<T> node = queue.remove();
                res.add(node.val);
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
        }
        return res;
    }

    /**
     * 前序遍历非递归
     *
     * @param root
     * @return
     */
    public List<T> preOrder(TreeNode<T> root) {
        List<T> res = new ArrayList<>();
        if (root == null) {
            return res;
        }

        TreeNode<T> curNode = root;
        Stack<TreeNode<T>> stack = new Stack<>();
        while (curNode != null || !stack.isEmpty()) {
            while (curNode != null) {
                stack.add(curNode);
                res.add(curNode.val);
                curNode = curNode.left;
            }
            curNode = stack.pop();
            curNode = curNode.right;
        }
        return res;
    }

    /**
     * 中序遍历非递归
     *
     * @param root
     * @return
     */
    public List<T> midOrder(TreeNode<T> root) {
        List<T> res = new ArrayList<>();
        if (root == null) {
            return res;
        }

        Stack<TreeNode<T>> stack = new Stack<>();
        TreeNode<T> curNode = root;
        while (curNode != null || !stack.isEmpty()) {
            while (curNode != null) {
                stack.push(curNode);
                curNode = curNode.left;
            }
            curNode = stack.pop();
            res.add(curNode.val);
            curNode = curNode.right;
        }
        return res;
    }

    /**
     * 后序遍历非递归(双栈法，好理解)
     *
     * @param root
     * @return
     */
    public List<T> postOrder(TreeNode<T> root) {
        List<T> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        Stack<TreeNode<T>> stackRes = new Stack<>();
        Stack<TreeNode<T>> stackTmp = new Stack<>();
        stackTmp.push(root);
        TreeNode<T> curNode;

        while (!stackTmp.isEmpty()) {
            curNode = stackTmp.pop();
            stackRes.push(curNode);
            if (curNode.left != null) {
                stackTmp.add(curNode.left);
            }
            if (curNode.right != null) {
                stackTmp.add(curNode.right);
            }
        }

        while (!stackRes.isEmpty()) {
            res.add(stackRes.pop().val);
        }
        return res;
    }

    /**
     * 后序遍历非递归
     * <p>
     * 参考链接，注释非常详细
     * http://www.cnblogs.com/ybwang/archive/2011/10/04/lastOrderTraverse.html
     *
     * @param root
     * @return
     */
    public List<T> postOrder2(TreeNode<T> root) {
        List<T> res = new ArrayList<>();
        if (root == null) {
            return res;
        }

        Stack<TreeNode<T>> stack = new Stack<>();
        stack.push(root);
        TreeNode<T> curNode = null;
        TreeNode<T> preNode = null;
        while (!stack.isEmpty()) {
            curNode = stack.peek();
            if (preNode == null || preNode.left == curNode || preNode.right == curNode) {
                if (curNode.left != null) {
                    stack.push(curNode.left);
                } else if (curNode.right != null) {
                    stack.push(curNode.right);
                } else {
                    res.add(stack.pop().val);
                }
            } else if (curNode.left == preNode) {
                if (curNode.right != null) {
                    stack.push(curNode.right);
                } else {
                    res.add(stack.pop().val);
                }
            } else if (curNode.right == preNode) {
                res.add(stack.pop().val);
            }
            preNode = curNode;
        }
        return res;
    }
}
