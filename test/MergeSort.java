import org.omg.PortableInterceptor.INACTIVE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MergeSort {
    public static void sort(List<Integer> numbers) {
        int[] tmp = new int[numbers.size()];
        sort(numbers, 0, numbers.size() - 1, tmp);
    }

    private static void sort(List<Integer> numbers, int start, int end, int[] tmp) {
        if (start >= end) {
            return;
        }

        int mid = (start + end) >>> 1;
        sort(numbers, start, mid, tmp);
        sort(numbers, mid + 1, end, tmp);
        merge(numbers, start, end, tmp);
    }

    private static void merge(List<Integer> numbers, int start, int end, int[] tmp) {
        int mid = (start + end) >>> 1;
        int left = start;
        int right = mid + 1;
        int tmpPos = start;
        while (left <= mid && right <= end) {
            if (numbers.get(left) < numbers.get(right)) {
                tmp[tmpPos++] = numbers.get(left++);
            } else {
                tmp[tmpPos++] = numbers.get(right++);
            }
        }

        while (left <= mid) {
            tmp[tmpPos++] = numbers.get(left++);
        }
        while (right <= end) {
            tmp[tmpPos++] = numbers.get(right++);
        }

        for (int i = start; i <= end; i++) {
            numbers.set(i, tmp[i]);
        }
    }
}
