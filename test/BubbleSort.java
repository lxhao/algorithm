import java.util.Collections;
import java.util.List;

public class BubbleSort {
    public static void sort(List<Integer> numbers) {
        for (int i = 0; i < numbers.size(); i++) {
            for (int j = 0; j < numbers.size() - i - 1; j++) {
                if (numbers.get(j) > numbers.get(j + 1)) {
                    Collections.swap(numbers, j, j + 1);
                }
            }
        }
    }
}
