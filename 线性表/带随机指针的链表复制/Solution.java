package 带随机指针的链表复制;


class RandomListNode {
    int label;
    RandomListNode next, random;

    RandomListNode(int x) {
        this.label = x;
    }
};

public class Solution {
    public RandomListNode copyRandomList(RandomListNode head) {
        if (head == null) {
            return null;
        }
        // 在原链表每个节点后面插入一个拷贝节点
        RandomListNode curNode = head;
        while (curNode != null) {
            RandomListNode cpNode = new RandomListNode(curNode.label);
            cpNode.next = curNode.next;
            curNode.next = cpNode;
            curNode = cpNode.next;
        }

        // 修改拷贝节点的随机指针
        curNode = head;
        while (curNode != null) {
            if (curNode.random != null) {
                curNode.next.random = curNode.random.next;
            }
            curNode = curNode.next.next;
        }
        // 提出拷贝节点
        RandomListNode newHead = head.next;
        curNode = newHead;
        while (curNode != null && curNode.next != null) {
            curNode.next = curNode.next.next;
            curNode = curNode.next;
        }
        return newHead;
    }

    public static void main(String[] args) {
        RandomListNode head = new RandomListNode(2);
        head.next = null;
        head.random = head;
        Solution solution = new Solution();
        solution.copyRandomList(head);
    }
}