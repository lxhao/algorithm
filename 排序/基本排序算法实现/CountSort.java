package 基本排序算法实现;

import java.util.*;

public class CountSort {
    //随机测试次数
    private static final int TEST_TIMES = 100;
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
            for(i = 1; i < numbers.size(); i++) {
                if(numbers.get(i - 1) > numbers.get(i)) {
                    System.out.println("sort failure!");
                    break;
                }
            }
            if(i == numbers.size()) {
                System.out.println("sort success!");
            }
            System.out.println("");
        }
    }

    //计数排序，list中元素的取值范围为1 -> numbers.size();
    public static void sort(List<Integer> numbers) {
        if(numbers == null || numbers.size() == 0) {
            return;
        }
        int[] times = new int[numbers.size() + 1];
        //记录每个数出现的次数
        for (Integer e : numbers) {
            times[e]++;
        }

        //记录小于等于i的元素数量
        for (int i = 1; i < times.length; i++) {
            times[i] = times[i - 1] + times[i];
        }

        Integer[] numbersCopy = numbers.toArray(new Integer[numbers.size()]);
        //排序
        for (int i = numbersCopy.length - 1; i >= 0; i--) {
            //数据
            Integer elem = numbersCopy[i];
            //数据在有序数组中应该保存的位置
            int pos = times[elem] - 1;
            numbers.set(pos, elem);
            //小于等于elem的元素数量减1
            times[elem]--;
        }
    }
}

