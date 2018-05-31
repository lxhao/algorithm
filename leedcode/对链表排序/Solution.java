package 对链表排序;

class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
        next = null;
    }
}

public class Solution {


    public static void main(String[] args) {
        ListNode node = new ListNode(8);
        node.next = new ListNode(1);
        node.next.next = new ListNode(9);
        node.next.next.next = new ListNode(-9);
        Solution solution = new Solution();
        ListNode res = solution.sortList(node);
        while (res != null) {
            System.out.println(res.val);
            res = res.next;
        }
    }

    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        int len = 0;
        ListNode cur = head;
        while (cur != null) {
            len++;
            cur = cur.next;
        }

        ListNode mid = head;
        for (int i = 1; i < len / 2; i++) {
            mid = mid.next;
        }

        ListNode right = mid.next;
        mid.next = null;
        sortList(head);
        sortList(right);
        return mergeList(head, right);
    }

    private ListNode mergeList(ListNode left, ListNode right) {
        ListNode newHead = new ListNode(0);
        ListNode cur = newHead;

        while (left != null && right != null) {
            if (left.val < right.val) {
                cur.next = left;
                left = left.next;
            } else {
                cur.next = right;
                right = right.next;
            }
            cur = cur.next;
        }

        if (left != null) {
            cur.next = left;
        }

        if (right != null) {
            cur.next = right;
        }
        return newHead.next;
    }

}
