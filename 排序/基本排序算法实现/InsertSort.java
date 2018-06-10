package 基本排序算法实现;

import java.util.Collections;
import java.util.List;

public class InsertSort {
    public static <T extends Comparable<? super T>> void sort(List<T> list) {
        int len = list.size();
        for (int i = 0; i < len; i++) {
            for (int j = i; j >= 1 && list.get(j - 1).compareTo(list.get(j)) > 0; j--) {
                Collections.swap(list, j, j - 1);
            }
        }
    }
}
