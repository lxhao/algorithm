package 链表插入排序;

class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
        next = null;
    }
}

public class Solution {
    private ListNode insert(ListNode sortedList, ListNode e) {
        if (e.val <= sortedList.val) {
            e.next = sortedList;
            return e;
        }
        ListNode curNode = sortedList;
        while (curNode.next != null) {
            if (curNode.next.val >= e.val) {
                e.next = curNode.next;
                curNode.next = e;
                return sortedList;
            }
            curNode = curNode.next;
        }
        // 到了最后一个节点，所有节点的值都大于e.val
        e.next = null;
        curNode.next = e;
        return sortedList;
    }

    public ListNode insertionSortList(ListNode head) {
        if (head == null) {
            return null;
        }

        ListNode sortedList = head;
        head = head.next;
        sortedList.next = null;
        while (head != null) {
            ListNode tmp = head;
            head = head.next;
            sortedList = insert(sortedList, tmp);
        }
        return sortedList;
    }

    public static void main(String[] args) {
        ListNode l1 = new ListNode(3);
        ListNode l2 = new ListNode(4);
        l1.next = l2;
        l2.next = new ListNode(1);
        Solution solution = new Solution();

        ListNode sorted = solution.insertionSortList(l1);
        while (sorted != null) {
            System.out.println(sorted.val);
            sorted = sorted.next;
        }
    }
}