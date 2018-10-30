package 基本排序算法实现;

import java.util.Collections;
import java.util.List;

public class BubbleSort {

    public static <T extends Comparable<? super T>> void sort(List<T> list) {
        int len = list.size();
        for (int i = 0; i < len - 1; i++) {
            for (int j = 0; j <= len - i - 2; j++) {
                if (list.get(j).compareTo(list.get(j + 1)) > 0) {
                    Collections.swap(list, j, j + 1);
                }
            }
            System.out.printf("the %dth iteration: %s\n", i + 1, list);
        }
    }
}
