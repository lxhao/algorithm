package 链表中倒数第k个节点;


class ListNode {
    int val;
    ListNode next = null;

    ListNode(int val) {
        this.val = val;
    }
}

/**
 * 题目描述
 * 输入一个链表，输出该链表中倒数第k个结点。
 *
 * 用快慢来个指针,快指针先移动k个节点,然后两个指针一起移动,快指针到达尾节点时,慢指针到达倒数第k个节点
 */
public class Solution {
    public ListNode FindKthToTail(ListNode head, int k) {
        if (head == null || k <= 0) {
            return null;
        }

        ListNode fast = head;
        ListNode slow = head;
        for (int i = 1; i < k && fast != null; i++) {
            fast = fast.next;
        }

        if (fast == null) {
            return null;
        }

        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
    }
}