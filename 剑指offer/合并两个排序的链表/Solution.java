package 合并两个排序的链表;

/**
 * 题目描述
 * 输入两个单调递增的链表，输出两个链表合成后的链表，当然我们需要合成后的链表满足单调不减规则。
 * <p>
 * 新建一个头结点保存新链表,用两个指针遍历两个链表,取小的尾插法加入新链表
 */
class ListNode {
    int val;
    ListNode next = null;

    ListNode(int val) {
        this.val = val;
    }
}

public class Solution {
    public ListNode Merge(ListNode list1, ListNode list2) {
        ListNode newHead = new ListNode(0);
        ListNode tmp;
        ListNode tail = newHead;
        while (list1 != null && list2 != null) {
            if (list1.val < list2.val) {
                tmp = list1;
                list1 = list1.next;
            } else {
                tmp = list2;
                list2 = list2.next;
            }
            tail.next = tmp;
            tail = tmp;
        }

        if (list1 != null) {
            tail.next = list1;
        }

        if (list2 != null) {
            tail.next = list2;
        }
        return newHead.next;
    }
}