package 复杂链表的复制;

import java.util.HashMap;

class RandomListNode {
    int label;
    RandomListNode next = null;
    RandomListNode random = null;

    RandomListNode(int label) {
        this.label = label;
    }
}

/**
 * 题目描述
 * 输入一个复杂链表（每个节点中有节点值，以及两个指针，一个指向下一个节点，
 * 另一个特殊指针指向任意一个节点），返回结果为复制后复杂链表的head。
 * （注意，输出结果中请不要返回参数中的节点引用，否则判题程序会直接返回空）
 * <p>
 * 方法一:
 * 复制一份链表出来,建立一个原始链表和复制链表的map,同时遍历两个链表,根据之前建立的map,
 * 可以找到原始链表随机指针指向的节点对应的复制链表指向的节点.
 * <p>
 * 方法二:
 * 1.在原始链表之间插入复制链表,比如原来是a->b->c,变成a->a'->b->b'->c->c'
 * 2.如果a的随机指针指向了c,a的next指向c的next
 * 3.把两个链表重新拆分出来就是结果
 */
public class Solution {
    public static void main(String[] args) {
        RandomListNode node = new RandomListNode(2);
        node.next = new RandomListNode(3);
        node.next.next = new RandomListNode(4);
        node.next.random = node;
        System.out.println(new Solution().Clone(node).next.random.label);
    }

    public RandomListNode Clone1(RandomListNode pHead) {
        if (pHead == null) {
            return null;
        }
        RandomListNode newHead = new RandomListNode(0);

        RandomListNode q = newHead;
        RandomListNode p = pHead;
        while (p != null) {
            q.next = new RandomListNode(p.label);
            q = q.next;
            p = p.next;
        }

        HashMap<RandomListNode, RandomListNode> map = new HashMap<>();
        q = newHead.next;
        p = pHead;
        while (q != null) {
            map.put(p, q);
            q = q.next;
            p = p.next;
        }

        q = newHead.next;
        p = pHead;
        while (q != null) {
            q.random = map.get(p.random);
            q = q.next;
            p = p.next;
        }
        return newHead.next;
    }

    public RandomListNode Clone(RandomListNode pHead) {
        if (pHead == null) {
            return null;
        }
        RandomListNode p = pHead;
        //在原链表之间插入新链表的节点
        while (p != null) {
            RandomListNode node = new RandomListNode(p.label);
            node.next = p.next;
            p.next = node;
            p = node.next;
        }

        //加上新链表的random指针
        //如果a的随机指针指向了c,a的next指向c的next
        p = pHead;
        while (p != null) {
            if (p.random != null) {
                p.next.random = p.random.next;
            }
            p = p.next.next;
        }

        //把新链表拆分出来
        p = pHead;
        RandomListNode newHead = p.next;
        RandomListNode q = newHead;
        while (true) {
            p.next = q.next;
            p = p.next;
            if (p == null) {
                break;
            }
            q.next = p.next;
            q = q.next;
        }
        return newHead;
    }
}