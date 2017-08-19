import java.util.List;

public class MergeSort {

    public static <T extends Comparable<? super T>> void sort(List<T> list) {
        if (list == null) {
            return;
        }
        Object[] tmp = new Object[list.size() + 1];
        sort(list, 0, list.size() - 1, tmp);
    }

    private static <T extends Comparable<? super T>> void sort(List<T> list, int startPos, int endPos, Object[] tmp) {
        if (endPos - startPos == 0) {
            return;
        }
        int mid = (startPos + endPos) / 2;
        sort(list, startPos, mid, tmp);
        sort(list, mid + 1, endPos, tmp);
        merge(list, startPos, endPos, tmp);
    }

    private static <T extends Comparable<? super T>> void merge(List<T> list, int left, int right, Object[] tmp) {

        int mid = (left + right) / 2;
        int leftIndex = left;
        int leftEnd = mid;

        int rightIndex = mid + 1;
        int rightEnd = right;

        int index = left;

        while (leftIndex <= leftEnd && rightIndex <= rightEnd) {
            if (list.get(leftIndex).compareTo(list.get(rightIndex)) <= 0) {
                tmp[index++] = list.get(leftIndex++);
            } else {
                tmp[index++] = list.get(rightIndex++);
            }
        }

        while (leftIndex <= leftEnd) {
            tmp[index++] = list.get(leftIndex++);
        }

        while (rightIndex <= rightEnd) {
            tmp[index++] = list.get(rightIndex++);
        }

        for (int i = left; i <= right; i++) {
            list.set(i, (T) tmp[i]);
        }
    }

}
