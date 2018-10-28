package 链表归并排序;

class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
        next = null;
    }
}

public class Solution {
    private ListNode merge(ListNode l1, ListNode l2) {
        ListNode newHead = new ListNode(0);
        ListNode curNode = newHead;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                curNode.next = l1;
                curNode = l1;
                l1 = l1.next;
            } else {
                curNode.next = l2;
                curNode = l2;
                l2 = l2.next;
            }
        }

        while (l1 != null) {
            curNode.next = l1;
            curNode = l1;
            l1 = l1.next;
        }

        while (l2 != null) {
            curNode.next = l2;
            curNode = l2;
            l2 = l2.next;
        }
        curNode.next = null;
        return newHead.next;
    }

    private ListNode getMid(ListNode head) {
        ListNode fast = head.next;
        ListNode slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }

    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode mid = getMid(head);
        ListNode tail = mid.next;
        mid.next = null;

        head = sortList(head);
        tail = sortList(tail);
        return merge(head, tail);
    }

    public static void main(String[] args) {
        ListNode l1 = new ListNode(3);
        ListNode l2 = new ListNode(4);
        l1.next = l2;
        l2.next = new ListNode(1);
        Solution solution = new Solution();

        ListNode sorted = solution.sortList(l1);
        while (sorted != null) {
            System.out.println(sorted.val);
            sorted = sorted.next;
        }
    }
}