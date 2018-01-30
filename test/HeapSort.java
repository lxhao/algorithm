import java.util.Collections;
import java.util.List;

public class HeapSort {
    public static void sort(List<Integer> numbers) {
        for (int i = numbers.size() / 2 - 1; i >= 0; i--) {
            siftDown(numbers, i, numbers.size() - 1);
        }

        for (int i = numbers.size() - 1; i > 0; i--) {
            Collections.swap(numbers, 0, i);
            siftDown(numbers, 0, i - 1);
        }
    }

    private static void siftDown(List<Integer> numbers, int start, int end) {
        int parent = start;
        while (parent < (end + 1) / 2) {
            int child = parent * 2 + 1;
            int right = child + 1;
            if (right <= end && numbers.get(right) > numbers.get(child)) {
                child = right;
            }

            if (numbers.get(child) <= numbers.get(parent)) {
                break;
            }

            Collections.swap(numbers, child, parent);
            parent = child;
        }
    }
}
