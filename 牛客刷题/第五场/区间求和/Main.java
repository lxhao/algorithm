package 第五场.区间求和;

import java.io.*;

public class Main {
    static class TreeNode {
        TreeNode lChild;
        TreeNode rChild;
        int left;
        int right;
        long sum;
    }

    public static void main(String[] args) throws IOException {
        try {
            System.setIn(new FileInputStream("input.txt"));
        } catch (FileNotFoundException ignored) {
        }
        StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (in.nextToken() != StreamTokenizer.TT_EOF) {
            int n = (int) in.nval;
            in.nextToken();
            int m = (int) in.nval;
            int[] numbers = new int[n];
            for (int i = 0; i < n; i++) {
                in.nextToken();
                numbers[i] = (int) in.nval;
            }
            TreeNode root = build(numbers, 0, numbers.length - 1);
            for (int i = 0; i < m; i++) {
                in.nextToken();
                int type = (int) in.nval;
                in.nextToken();
                int x = (int) in.nval;
                in.nextToken();
                int y = (int) in.nval;
                if (type == 1) {
                    update(root, x - 1, y);
                } else {
                    out.println(getSum(root, x - 1, y - 1));
                }
            }
            out.flush();
        }
    }

    /**
     * 构造线段树
     *
     * @param numbers
     * @param left
     * @param right
     * @return
     */
    private static TreeNode build(int[] numbers, int left, int right) {
        int mid = (left + right) >>> 1;
        TreeNode node = new TreeNode();
        node.left = left;
        node.right = right;
        if (left == right) {
            node.sum = numbers[right];
            return node;
        }
        node.lChild = build(numbers, left, mid);
        node.rChild = build(numbers, mid + 1, right);
        node.sum = node.lChild.sum + node.rChild.sum;
        return node;
    }

    /**
     * 更新
     *
     * @param root
     * @param pos
     * @param val
     */
    private static void update(TreeNode root, int pos, int val) {
        root.sum += val;
        if (root.left == root.right) {
            return;
        }
        int nodeMid = (root.left + root.right) >>> 1;
        if (pos <= nodeMid) {
            update(root.lChild, pos, val);
        } else {
            update(root.rChild, pos, val);
        }
    }

    /**
     * 求和
     *
     * @param root
     * @param left
     * @param right
     * @return
     */
    private static long getSum(TreeNode root, int left, int right) {
        if (root.left == left && root.right == right) {
            return root.sum;
        }
        int nodeMid = (root.left + root.right) >>> 1;
        if (right <= nodeMid) {
            return getSum(root.lChild, left, right);
        } else if (left >= nodeMid + 1) {
            return getSum(root.rChild, left, right);
        } else {
            return getSum(root, left, nodeMid) + getSum(root, nodeMid + 1, right);
        }
    }
}

