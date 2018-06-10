package 区间求和;

public class Main {
    static class TreeNode {
        int left;
        int right;
        int sum;
        TreeNode lChild;
        TreeNode rChild;
    }

    public static void main(String[] args) {
        int[] numbers = new int[]{
                0, 1, 2, 3, 4, 5, 6
        };
        Main main = new Main();
        TreeNode root = main.build(0, numbers.length - 1, numbers);
        System.out.println(main.query(4, 5, root));
        main.update(root, -1, 4);
        System.out.println(main.query(4, 5, root));
        System.out.println(main.query(0, 5, root));
    }

    public void update(TreeNode node, int val, int pos) {
        int mid = (node.left + node.right) >>> 1;
        if (node.left == node.right) {
            node.sum += val;
            return;
        }
        if (mid < node.left || mid > node.right) {
            throw new IllegalArgumentException();
        }
        node.sum += val;
        if (pos >= mid + 1) {
            update(node.rChild, val, pos);
        } else {
            update(node.lChild, val, pos);
        }
    }

    public TreeNode build(int left, int right, int numbers[]) {
        TreeNode node = new TreeNode();
        node.left = left;
        node.right = right;
        if (left == right) {
            node.sum = numbers[left];
            return node;
        }

        int mid = (left + right) >>> 1;
        node.lChild = build(left, mid, numbers);
        node.rChild = build(mid + 1, right, numbers);
        node.sum = node.lChild.sum + node.rChild.sum;
        return node;
    }

    public int query(int left, int right, TreeNode root) {
        if (root.left == left && root.right == right) {
            return root.sum;
        }
        if (left < root.left || right > root.right) {
            throw new IllegalArgumentException();
        }
        int nodeMid = (root.left + root.right) >>> 1;
        if (right <= nodeMid) {
            return query(left, right, root.lChild);
        } else if (left >= nodeMid + 1) {
            return query(left, right, root.rChild);
        } else {
            return query(left, nodeMid, root) + query(nodeMid + 1, right, root);
        }
    }
}
