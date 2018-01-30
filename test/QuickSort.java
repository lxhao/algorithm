import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuickSort {
    private static final Random random = new Random();

    public static void sort(List<Integer> numbers) {
        if (numbers == null) {
            return;
        }
        sort(numbers, 0, numbers.size() - 1);
    }

    private static void sort(List<Integer> numbers, int start, int end) {
        if (start >= end) {
            return;
        }
        int left = start;
        int right = end;
        int rnd = random.nextInt(end - start + 1) + start;
        Collections.swap(numbers, rnd, left);

        int tmp = numbers.get(left);

        while (left < right) {
            while (left < right && numbers.get(right) >= tmp) {
                right--;
            }
            numbers.set(left, numbers.get(right));

            while (left < right && numbers.get(left) < tmp) {
                left++;
            }
            numbers.set(right, numbers.get(left));
        }
        numbers.set(left, tmp);
        sort(numbers, start, left - 1);
        sort(numbers, left + 1, end);
    }
}
