import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class 滴滴第k大的数 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        List<Integer> arr = new ArrayList<>();
        while (in.hasNext()) {
            arr.add(in.nextInt());
        }
        int k = arr.remove(arr.size() - 1);
        System.out.println(getKMaxNumber(arr, k, 0, arr.size() - 1));
        in.close();
    }

    private static int getKMaxNumber(List<Integer> numbers, int k, int start, int end) {
        int left = start;
        int right = end;
        int pivot = numbers.get(left);

        while (left < right) {

            while (left < right && numbers.get(right) <= pivot) {
                right--;
            }
            numbers.set(left, numbers.get(right));

            while (left < right && numbers.get(left) > pivot) {
                left++;
            }
            numbers.set(right, numbers.get(left));
        }
        numbers.set(left, pivot);
        if (left == k - 1) {
            return numbers.get(left);
        } else if (k - 1 < left) {
            return getKMaxNumber(numbers, k, start, left - 1);
        } else {
            return getKMaxNumber(numbers, k, left + 1, end);
        }
    }
}


