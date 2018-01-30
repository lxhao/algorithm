import java.util.Arrays;
import java.util.Random;

public class 第k大的数 {
    public static void main(String[] args) {
        new 第k大的数().test();
    }

    private void test() {
        int n = 10;
        int[] numbers = new int[n];
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            numbers[i] = random.nextInt(100);
        }
        int k = random.nextInt(n);
        System.out.println(getKNumber(numbers, k, 0, numbers.length - 1));
        Arrays.sort(numbers);
        for (int i : numbers) {
            System.out.print(i + " ");
        }
        System.out.println("");
        System.out.printf("k is %d\n", k);
    }

    private void swap(int[] numbers, int p1, int p2) {
        int tmp = numbers[p1];
        numbers[p1] = numbers[p2];
        numbers[p2] = tmp;
    }

    private void siftDown(int[] numbers, int k) {
        while (k < numbers.length / 2) {
            int child = 2 * k + 1;
            int right = child + 1;
            if (right < numbers.length && numbers[right] < numbers[child]) {
                child = right;
            }
            if (numbers[k] <= numbers[child]) {
                break;
            }
            swap(numbers, k, child);
            k = child;
        }
    }

    private int getKNumber(int[] numbers, int k, int start, int end) {
        if (numbers == null || numbers.length == 0 || k <= 0 || start > end) {
            throw new IllegalArgumentException();
        }
        int[] heap = Arrays.copyOf(numbers, k);
        for (int i = 0; i < heap.length / 2; i++) {
            siftDown(heap, i);
        }

        for (int i = k; i < numbers.length; i++) {
            if (numbers[i] > heap[0]) {
                heap[0] = numbers[i];
                siftDown(heap, 0);
            }
        }
        return heap[0];
    }
}
