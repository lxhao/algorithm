/**
 * 求二叉树中两个节点的最大距离
 */
public class MaxDistance {

    //二叉树节点
    class Node {
        Node left;
        Node right;
    }

    public static void main(String[] args) {

        new MaxDistance().test();
    }

    public void test() {
        Node root = new Node();
        root.left = new Node();
        root.right = new Node();
        root.left.left = new Node();
        root.left.left.right = new Node();
        root.left.left.left = new Node();
        root.left.left.left.right = new Node();
        System.out.println(getMaxDistance(root)[0]);
    }

    /**
     * @return 返回root下面两个节点的最大距离和root到另外一个节点的最大距离
     */
    public int[] getMaxDistance(Node root) {
        int[] res = new int[2];
        if (root == null) {
            return res;
        }

        int[] leftDis = getMaxDistance(root.left);
        int[] rightDis = getMaxDistance(root.right);

        res[0] = Math.max(leftDis[0], rightDis[0]);
        res[0] = Math.max(res[0], leftDis[1] + rightDis[1]);
        res[1] = Math.max(leftDis[1], rightDis[1]) + 1;
        return res;
    }
}
