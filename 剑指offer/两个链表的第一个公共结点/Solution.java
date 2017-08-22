package 两个链表的第一个公共结点;

class ListNode {
    int val;
    ListNode next = null;

    ListNode(int val) {
        this.val = val;
    }
}

/**
 * 题目描述
 * 输入两个链表，找出它们的第一个公共结点。
 *
 * 长的链表多走完多出的节点,然后一起走
 */

public class Solution {
    public ListNode FindFirstCommonNode(ListNode pHead1, ListNode pHead2) {
        if (pHead1 == null || pHead2 == null) {
            return null;
        }
        ListNode p1 = pHead1;
        ListNode p2 = pHead2;
        int len1 = 0;
        int len2 = 0;
        //得到链表的长度
        while (p1 != null) {
            p1 = p1.next;
            len1++;
        }
        while (p2 != null) {
            p2 = p2.next;
            len2++;
        }

        p1 = pHead1;
        p2 = pHead2;
        if (len1 < len2) {
            p1 = pHead2;
            p2 = pHead1;
        }
        for (int i = 0; i < Math.abs(len1 - len2); i++) {
            p1 = p1.next;
        }
        while (p1 != p2) {
            p1 = p1.next;
            p2 = p2.next;
        }
        return p1;
    }
}