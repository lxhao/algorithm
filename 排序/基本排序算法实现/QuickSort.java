package 基本排序算法实现;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * 该方法的基本思想是：
 * 1．先从数列中取出一个数作为基准数。
 * 2．分区过程，将比这个数大的数全放到它的右边，小于或等于它的数全放到它的左边。
 * 3．再对左右区间重复第二步，直到各区间只有一个数。
 */
public class QuickSort {
    private static final Random random = new Random();

    public static <T extends Comparable<? super T>> void sort(List<T> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        sort(list, 0, list.size() - 1, 1);
    }

    public static <T extends Comparable<? super T>> void sort(List<T> list, int start, int end, int cnt) {
        if (end <= start) {
            return;
        }
        int left = start;
        int right = end;

        //随机挑选一个位置作为基准数, 自己做测试证明随机交换位置能提升性能
//        int randomPos = random.nextInt(end - start + 1) + start;
//        Collections.swap(list, left, randomPos);

        //基准数
        T tmp = list.get(left);

        //大于基准数的放右边,小于基准数的放左边
        while (left < right) {

            //从右边找到第一个小于基准数的位置
            while (left < right && list.get(right).compareTo(tmp) > 0) {
                right--;
            }
            list.set(left, list.get(right));

            //从左边找到第一个大于基准数的位置
            while (left < right && list.get(left).compareTo(tmp) <= 0) {
                left++;
            }
            list.set(right, list.get(left));
        }
        //基准数放中间
        list.set(left, tmp);
        System.out.printf("the %dth iteration: %s\n", cnt, list);
        sort(list, start, left - 1, cnt + 1);
        sort(list, left + 1, end, cnt + 1);
    }
}
