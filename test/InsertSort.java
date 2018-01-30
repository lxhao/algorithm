import java.util.Collections;
import java.util.List;

public class InsertSort {
    public static void sort(List<Integer> numbers) {
        for (int i = 1; i < numbers.size(); i++) {
            for (int j = i; j >= 1 && numbers.get(j) < numbers.get(j - 1); j--) {
                Collections.swap(numbers, j, j - 1);
            }
        }
    }
}
