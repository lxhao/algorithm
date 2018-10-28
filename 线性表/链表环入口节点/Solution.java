package 链表环入口节点;


class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
        next = null;
    }
}

public class Solution {
    public ListNode detectCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;

        ListNode meetNode = null;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;

            if (fast == slow) {
                meetNode = fast;
                break;
            }
        }

        // 不存在环
        if (meetNode == null) {
            return null;
        }

        //找环入口节点
        fast = meetNode;
        slow = head;
        while (fast != slow) {
            slow = slow.next;
            fast = fast.next;
        }

        return fast;
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(0);
        head.next = new ListNode(1);
        head.next.next = new ListNode(2);
        head.next.next.next = new ListNode(3);
        head.next.next.next.next = head;
        Solution solution = new Solution();
        ListNode entryNode = solution.detectCycle(head);
        System.out.println(entryNode.val);
    }
}