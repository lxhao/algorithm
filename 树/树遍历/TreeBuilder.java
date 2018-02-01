package 树遍历;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

public class TreeBuilder {
    /**
     * 从文件读取数据按层次遍历的方式创建一个二叉树 (测试用)
     * 文件输入只能是'#'或数字，'#'表示null
     * 测试用例1： 1 2 3 4 5 # # 6 # 7 9
     * 测试用例2： 1 2 3 4 5
     *
     * @return
     */
    public TreeNode<Integer> createTree(Scanner in) {
        String val = in.next();
        if (val.equals("#")) {
            return null;
        }
        TreeNode<Integer> node = new TreeNode<>(Integer.valueOf(val));
        TreeNode<Integer> root = node;
        Queue<TreeNode<Integer>> queue = new ArrayDeque<>();
        queue.add(node);
        while (!queue.isEmpty()) {
            int len = queue.size();
            for (int i = 0; i < len; i++) {
                node = queue.remove();
                if (!in.hasNext()) {
                    break;
                }
                val = in.next();
                if (val.equals("#")) {
                    node.left = null;
                } else {
                    node.left = new TreeNode<>(Integer.valueOf(val));
                    queue.add(node.left);
                }
                if (!in.hasNext()) {
                    break;
                }
                val = in.next();
                if (val.equals("#")) {
                    node.right = null;
                } else {
                    node.right = new TreeNode<>(Integer.valueOf(val));
                    queue.add(node.right);
                }
            }
        }
        return root;
    }
}
