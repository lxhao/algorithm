package 优先队列实现;

import java.util.ArrayList;
import java.util.List;

/**
 * 为了熟悉堆，模拟了优先队列(java.util.PriorityQueue)的主要方法
 *
 * @param <T>
 */

public class PriorityQueue<T extends Comparable<? super T>> {
    public static void main(String[] args) {
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        for (int i = 5; i >= 0; i--) {
            priorityQueue.add(i);
            System.out.print(priorityQueue.poll() + " ");
        }
        System.out.println(priorityQueue);
        System.out.println("");
    }

    private List<T> elements;

    public PriorityQueue() {
        this.elements = new ArrayList<>();
    }

    public PriorityQueue(List<T> elements) {
        this.elements = elements;
    }

    /**
     * 删除堆顶元素后把最后一个元素放到堆顶位置, 需要调整堆顶元素使数组维持堆状
     *
     * @param pos
     * @param e
     */
    private void siftDown(int pos, T e) {
        int curPos = pos;
        while (curPos * 2 + 1 < elements.size()) {
            int childPos = curPos * 2 + 1;
            //拿到最小的那个孩子
            if (childPos + 1 < elements.size() && elements.get(childPos + 1).compareTo(elements.get(childPos)) < 0) {
                childPos += 1;
            }
            if (elements.get(childPos).compareTo(e) >= 0) {
                break;
            }
            elements.set(curPos, elements.get(childPos));
            curPos = childPos;
        }
        elements.set(curPos, e);
    }

    /**
     * 加入元素后，调整新加入元素使数组维持堆状
     *
     * @param pos
     * @param e
     */
    private void siftUp(int pos, T e) {
        int curPos = pos;
        while (curPos > 0) {
            int parent = (curPos - 1) / 2;
            if (elements.get(parent).compareTo(e) <= 0) {
                break;
            }
            elements.set(curPos, elements.get(parent));
            curPos = parent;
        }
        elements.set(curPos, e);
    }

    /**
     * 往队列加入元素
     */
    public void add(T e) {
        elements.add(e);
        siftUp(elements.size() - 1, e);
    }

    /**
     * 删除堆顶元素
     */
    public T pop() {
        if (elements.size() == 0) {
            return null;
        }
        T removedE = elements.remove(elements.size() - 1);
        if (elements.size() == 0) {
            return removedE;
        }
        elements.set(0, removedE);
        siftDown(0, removedE);
        return removedE;
    }

    /**
     * 查看堆顶元素
     */
    public T poll() {
        return elements.get(0);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (T e : elements) {
            sb.append(e).append(" ");
        }
        return sb.toString();
    }
}

