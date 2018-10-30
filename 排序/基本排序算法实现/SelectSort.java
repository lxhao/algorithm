package 基本排序算法实现;

import java.util.*;

public class SelectSort {
    //随机测试次数
    private static final int TEST_TIMES = 10;
    //测试数据规模
    private static final int NUMBER_SIZE = 1000;

    public static void main(String args[]) {
        for (int j = 0; j < TEST_TIMES; j++) {
            Random rnd = new Random();
            List<Integer> numbers = new ArrayList<>();
            for (int i = 0; i < NUMBER_SIZE; i++) {
                numbers.add(rnd.nextInt(NUMBER_SIZE));
            }
            System.out.println(numbers);
            sort(numbers);
            System.out.println(numbers);
            //验证排序结果
            int i;
            for (i = 1; i < numbers.size(); i++) {
                if (numbers.get(i - 1) > numbers.get(i)) {
                    System.out.println("sort failure!");
                    break;
                }
            }
            if (i == numbers.size()) {
                System.out.println("sort success!");
            }
            System.out.println("");
        }
    }

    //选择排序
    public static void sort(List<Integer> numbers) {
        if (numbers == null || numbers.size() == 0) {
            return;
        }
        int len = numbers.size();
        int min;
        int minPos = 0;
        for (int i = 0; i < len - 1; i++) {
            min = Integer.MAX_VALUE;
            for (int j = i; j < len; j++) {
                if (numbers.get(j) < min) {
                    min = numbers.get(j);
                    minPos = j;
                }
            }
            Collections.swap(numbers, i, minPos);
            System.out.printf("the %dth iteration: %s\n", i + 1, numbers);
        }
    }
}


