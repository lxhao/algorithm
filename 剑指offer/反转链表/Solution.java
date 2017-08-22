package 反转链表;

class ListNode {
    int val;
    ListNode next = null;

    ListNode(int val) {
        this.val = val;
    }
}

/**
 * 自己建立一个头结点,然后用尾插法重新插入所有元素
 */
public class Solution {
    public ListNode ReverseList(ListNode head) {
        if (head == null) {
            return head;
        }

        ListNode newHead = new ListNode(0);

        ListNode p = head;
        ListNode tmp;

        while (p != null) {
            tmp = p;
            p = p.next;
            tmp.next = newHead.next;
            newHead.next = tmp;
        }
        return newHead.next;
    }
}