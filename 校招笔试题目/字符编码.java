import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

public class 字符编码 {
    public static void main(String[] args) {
        Scanner in = getScanner(System.in);
//        Scanner in = getScanner("input.txt");
        while (in.hasNext()) {
            String line = in.nextLine();
            System.out.println(solve(line));
        }
        in.close();
    }

    //哈弗曼树的节点
    static class TreeNode implements Comparable<TreeNode>{
        TreeNode left;
        TreeNode right;
        int val;
        int weitht;

        TreeNode(int weitht) {
            this.weitht = weitht;
        }

        @Override
        public int compareTo(TreeNode o) {
            return this.weitht - o.weitht;
        }
    }

    private static int solve(String line) {
        if (line.length() == 1) {
            return 1;
        }
        Map<Integer, Integer> counter = charCounter(line);
        PriorityQueue<TreeNode> queue = new PriorityQueue<>();
        for (Map.Entry<Integer, Integer> e : counter.entrySet()) {
            TreeNode node = new TreeNode(e.getValue());
            node.val = e.getKey();
            queue.offer(node);
        }
        while (queue.size() > 1) {
            TreeNode n1 = queue.poll();
            TreeNode n2 = queue.poll();
            TreeNode newNode = new TreeNode(n1.weitht + n2.weitht);
            newNode.left = n1;
            newNode.right = n2;
            queue.offer(newNode);
        }

        TreeNode root = queue.poll();
        return getTotalLength(root, 0);
    }


    /**
     * 获取所有叶子节点的高度和
     *
     * @return
     */
    private static int getTotalLength(TreeNode node, int depth) {
        if (node == null) {
            return 0;
        }
        if (node.val == 0) {
            return getTotalLength(node.left, depth + 1) + getTotalLength(node.right, depth + 1);
        } else {
            return node.weitht * depth;
        }
    }

    private static Map<Integer, Integer> charCounter(String s) {
        Map<Integer, Integer> counter = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            int val = s.charAt(i);
            if (counter.get(val) == null) {
                counter.put(val, 0);
            }
            counter.put(val, counter.get(val) + 1);
        }
        return counter;
    }

    //从输入流读取输入数据
    public static Scanner getScanner(InputStream is) {
        return new Scanner(is);
    }

    //从文件读取输入数据
    public static Scanner getScanner(String fileName) {
        try {
            return getScanner(new FileInputStream(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}

