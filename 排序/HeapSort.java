import java.util.Collections;
import java.util.List;

public class HeapSort {

    private static <T extends Comparable<? super T>> void buildSort(List<T> list, int startPos, int endPos) {
        int childPos;
        //从startPos开始与孩子节点比较,如果父节点已经比两个孩子节点大了,直接返回,
        //如果比孩子节点小则与孩子节点交换位置,交换位置的孩子节点成为新的父节点继续比较
        for (int parentPos = startPos; parentPos * 2 + 1 <= endPos; parentPos = childPos) {
            childPos = parentPos * 2 + 1;
            if (childPos + 1 <= endPos &&
                    list.get(childPos + 1).compareTo(list.get(childPos)) > 0) {
                childPos += 1;
            }
            if (list.get(parentPos).compareTo(list.get(childPos)) < 0) {
                Collections.swap(list, parentPos, childPos);
            } else {
                break;
            }
        }
    }

    public static <T extends Comparable<? super T>> void sort(List<T> list) {
        //初始化堆
        for (int i = list.size() / 2 - 1; i >= 0; i--) {
            buildSort(list, i, list.size() - 1);
        }

        //堆顶元素肯定是最大的,每次拿掉堆顶元素,然后重新初始化堆
        for (int i = list.size() - 1; i >= 0; i--) {
            Collections.swap(list, 0, i);
            buildSort(list, 0, i - 1);
        }
    }
}
