package 链表反转;


import java.util.LinkedList;

class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
        next = null;
    }
}

public class Solution {
    public ListNode reverse(ListNode head) {
        ListNode newHead = new ListNode(0);
        ListNode curNode = head;
        while (curNode != null) {
            ListNode tmp = curNode;
            curNode = curNode.next;

            tmp.next = newHead.next;
            newHead.next = tmp;
        }
        return newHead.next;
    }

    private ListNode getMid(ListNode root) {
        ListNode fast = root;
        ListNode slow = root;

        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }

        return slow;
    }


    private void printList(ListNode l) {
        while (l != null) {
            System.out.println(l.val);
            l = l.next;
        }
    }

    public void reorderList(ListNode head) {
        if (head == null) {
            return;
        }
        ListNode mid = getMid(head);
        mid = reverse(mid);

        ListNode end = mid;
        while (head != end && mid.next != null) {
            ListNode tmp = head;
            head = head.next;
            tmp.next = mid;

            tmp = mid;
            mid = mid.next;
            tmp.next = head;

        }
    }

    public static void main(String[] args) {
        ListNode l1 = new ListNode(1);
        l1.next = new ListNode(2);
        Solution solution = new Solution();

        solution.reorderList(l1);
        solution.printList(l1);
    }
}
