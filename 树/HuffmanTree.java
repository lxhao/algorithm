import java.util.*;

public class HuffmanTree {
    class Node {
        int val;
        int weight;
        Node left;
        Node right;
        Node parent;

        Node(int weight) {
            this.weight = weight;
        }
    }

    /**
     * 计算哈弗曼编码
     *
     * @param counter key为对应的字符,value为字符出现的次数
     * @return 返回一个map, key为字符, value为对应的哈弗曼编码
     */
    public Map<Integer, String> getHuffmanCode(Map<Integer, Integer> counter) {
        //根据权重排序建立一个优先队列来保存节点,方便每次取最小的两个节点
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(
                new Comparator<Node>() {
                    @Override
                    public int compare(Node o1, Node o2) {
                        return o1.weight - o2.weight;
                    }
                }
        );
        //用数组保存哈弗曼树, 哈弗曼数的度为0的节点为counter.size(), 度为1的节点为0
        //度为2的节点为counter.size() - 1
        Node[] huffmanTree = new Node[counter.size() * 2 - 1];
        int index = 0;
        //把叶子节点放在数组前面的counter.size()个位置
        for (Map.Entry<Integer, Integer> entry : counter.entrySet()) {
            huffmanTree[index] = new Node(entry.getValue());
            huffmanTree[index].val = entry.getKey();
            priorityQueue.add(huffmanTree[index]);
            index++;
        }

        //每次从优先队列中取权重最小的两个节点生成新的父节点
        for (int i = counter.size(); i < huffmanTree.length; i++) {
            Node n1 = priorityQueue.poll();
            Node n2 = priorityQueue.poll();
            huffmanTree[i] = new Node(n1.weight + n2.weight);
            huffmanTree[i].left = n1;
            huffmanTree[i].right = n2;
            n1.parent = huffmanTree[i];
            n2.parent = huffmanTree[i];
            priorityQueue.add(huffmanTree[i]);
        }

        //levelTraverse(huffmanTree);
        //保存每个字符的哈弗曼编码
        Map<Integer, String> res = new HashMap<>();
        //计算每个字符的哈弗曼值
        StringBuilder huffmanCode = new StringBuilder();
        Node parent, curNode;
        for (int i = 0; i < counter.size(); i++) {
            huffmanCode.setLength(0);
            curNode = huffmanTree[i];
            parent = huffmanTree[i].parent;
            //从下往上计算huffmanTree[i].val的的哈弗曼编码
            while (parent != null) {
                //当前节点在父节点的左边,哈弗曼编码为0,在右边为1
                if (parent.left == curNode) {
                    huffmanCode.append("0");
                } else {
                    huffmanCode.append("1");
                }
                curNode = parent;
                parent = parent.parent;
            }
            //因为是从下往上生成的,所以需要逆置一下
            huffmanCode.reverse();
            res.put(huffmanTree[i].val, huffmanCode.toString());
        }
        return res;
    }

    /**
     * 从上往下打印哈弗曼数的权值,调试用
     *
     * @param huffamanTree
     */
    private void levelTraverse(Node[] huffamanTree) {
        for (int i = huffamanTree.length - 1; i >= 0; i--) {
            System.out.print(huffamanTree[i].weight + " ");
        }
        System.out.println("");
    }
}

