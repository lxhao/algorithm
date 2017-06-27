import java.util.*;

class Node {
    boolean indexed;
    List<Integer> childrens = new ArrayList<>();
}

public class Main {
    public static void main(String args[]) {
        int g1 = 2;
        int g2 = 3;
        int[][]  numbers = {
            {1, 2},
            {2, 4},
            {1, 3},
            {4, 3},
            {3, 2}
        };
        System.out.println(cmp(g1, g2, numbers, 4));
    }

    public static int cmp(int g1, int g2, int[][] numbers, int n) {
        if (numbers == null || numbers.length == 0 || g1 == g2) {
            return 0;
        }
        Map<Integer, Node> tree = new HashMap<>();
        for (int i = 0; i < numbers.length; i++) {
            if (tree.get(numbers[i][0]) == null) {
                tree.put(numbers[i][0], new Node());
            }
            tree.get(numbers[i][0]).childrens.add(numbers[i][1]);
        }
        boolean g1gtg2 = search(tree, g1, g2);
        boolean g2gtg1 = search(tree, g2, g1);
        //无法判断
        if (g1gtg2 && g2gtg1) {
            return 0;
        }
        //g2是g1的孩子
        if (g1gtg2) {
            return 1;
            //g1是g2的孩子
        } else if (g2gtg1) {
            return -1;
        } else {
            return 0;
        }
    }

    public static boolean search(Map<Integer, Node> tree, int nowKey, int target) {
        //到了叶子节点
        if (tree.get(nowKey) == null) {
            return false;
        }
        //已经遍历过的节点
        if (tree.get(nowKey).indexed) {
            return false;
        }
        tree.get(nowKey).indexed = true;
        List<Integer> childrens = tree.get(nowKey).childrens;
        //遍历所有的孩子节点
        for (int child : childrens) {
            //找到目标节点
            if (child == target) {
                return true;
            }
            //继续在下一层搜索
            if (search(tree, child, target)) {
                return true;
            }
        }
        return false;
    }
}
