package 基本排序算法实现;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Main {
    public static final int TEST_TIMES = 3;
    public static final int TEST_SIZE = (int) Math.pow(10, 7);

    public static void main(String[] args) {
        Main main = new Main();
//        main.testNlognSort();
//        main.testN2Sort();
        main.testPrintDetail();
    }

    private void testPrintDetail() {
        List<Integer> list = new ArrayList<>();
        Random rnd = new Random(1);
        for (int i = 0; i < 8; i++) {
            list.add(rnd.nextInt(50));
        }

        System.out.println("bubble sort");
        System.out.println("randomly generated array:\n" + list);
        BubbleSort.sort(new ArrayList<>(list));

        System.out.println("insert sort");
        System.out.println("randomly generated array:\n" + list);
        InsertSort.sort(new ArrayList<>(list));

        System.out.println("select sort");
        System.out.println("randomly generated array:\n" + list);
        SelectSort.sort(new ArrayList<>(list));

        System.out.println("quick sort");
        System.out.println("randomly generated array:\n" + list);
        QuickSort.sort(new ArrayList<>(list));

        System.out.println("merge sort");
        System.out.println("randomly generated array:\n" + list);
        MergeSort.sort(new ArrayList<>(list));
    }

    //测试n^2复杂度的排序算法
    private void testN2Sort() {
        Random random = new Random();
        //多次测试
        //生成随机数据
        List<Integer> numbers = new ArrayList<>(TEST_SIZE);
        for (int j = 0; j < 10; j++) {
            numbers.add(random.nextInt());
        }
        for (int i = 0; i < TEST_TIMES; i++) {
            //插入排序
            Collections.shuffle(numbers);
            long startTime = System.currentTimeMillis();
            InsertSort.sort(numbers);
            long endTime = System.currentTimeMillis();
            System.out.println(isSort(numbers) ? "sort success!" : "sort failure!");
            System.out.printf("插入排序耗时:%dms\n", endTime - startTime);

            //冒泡排序
            Collections.shuffle(numbers);
            startTime = System.currentTimeMillis();
            BubbleSort.sort(numbers);
            endTime = System.currentTimeMillis();
            System.out.println(isSort(numbers) ? "sort success!" : "sort failure!");
            System.out.printf("冒泡排序耗时:%dms\n", endTime - startTime);

        }
    }


    //测试nlogn复杂度的排序算法
    private void testNlognSort() {
        Random random = new Random();
        //多次测试
        //生成随机数据
        List<Integer> numbers = new ArrayList<>(TEST_SIZE);
        for (int j = 0; j < TEST_SIZE; j++) {
            numbers.add(random.nextInt());
        }
        for (int i = 0; i < TEST_TIMES; i++) {
            //快速排序
            Collections.shuffle(numbers);
            long startTime = System.currentTimeMillis();
            QuickSort.sort(numbers);
            long endTime = System.currentTimeMillis();
            System.out.println(isSort(numbers) ? "sort success!" : "sort failure!");
            System.out.printf("快速排序耗时:%dms\n", endTime - startTime);

            //堆排序
            Collections.shuffle(numbers);
            startTime = System.currentTimeMillis();
            HeapSort.sort(numbers);
            endTime = System.currentTimeMillis();
            System.out.println(isSort(numbers) ? "sort success!" : "sort failure!");
            System.out.printf("堆排序耗时:%dms\n", endTime - startTime);

            //归并排序
            Collections.shuffle(numbers);
            startTime = System.currentTimeMillis();
            MergeSort.sort(numbers);
            endTime = System.currentTimeMillis();
            System.out.println(isSort(numbers) ? "sort success!" : "sort failure!");
            System.out.printf("归并排序耗时:%dms\n", endTime - startTime);
        }
    }

    private boolean isSort(List<Integer> numbers) {
        //验证排序是否正确
        int len = numbers.size();
        for (int j = 0; j < len - 1; j++) {
            if (numbers.get(j) > numbers.get(j + 1)) {
                return false;
            }
        }
        return true;
    }
}
