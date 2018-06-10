package 基本排序算法实现;

import java.util.*;

public class RadixSort {

    //随机测试次数
    private static final int TEST_TIMES = 10;
    //测试数据规模
    private static final int NUMBER_SIZE = 2000;

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

    public static void sort(List<Integer> numbers) {
        if (numbers == null || numbers.size() == 0) {
            return;
        }
        //得到最长位数
        int max = Integer.MIN_VALUE;
        for (Integer e : numbers) {
            max = Math.max(max, e);
        }
        int maxLen = String.valueOf(max).length();

        msdradixSort(numbers, maxLen, 0, numbers.size() - 1);

    }

    //获取一个数的第pos位的值(从左到右)
    public static int getDigit(int n, int pos) {
        int r = n / (int)Math.pow(10, pos - 1);
        return (r % 10);
    }

    //从左往右
    public static void  msdradixSort(List<Integer> numbers, int nowPos, int start, int end) {
        if (nowPos == 0) {
            return;
        }
        //分配桶空间
        List<List<Integer>> buckets = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            buckets.add(new ArrayList<>());
        }

        //把数据分到桶
        for (int i = start; i <= end; i++) {
            int bucketsIndex = getDigit(numbers.get(i), nowPos);
            buckets.get(bucketsIndex).add(numbers.get(i));
        }

        //把根据当前位置排序好的数据更新到numbers
        int index = start;
        for (List<Integer> bucket : buckets) {
            for(Integer e : bucket) {
                numbers.set(index++, e);
            }
        }

        //对每个桶继续排序
        for (List<Integer> bucket : buckets) {
            //跳过空桶
            if(bucket.size() == 0) {
                continue;
            }
            msdradixSort(numbers, nowPos - 1, start, start + bucket.size() - 1 );
            start += bucket.size();
        }
    }

    //从右往左
    public static void lsdradixSort(List<Integer> numbers) {
        if (numbers == null || numbers.size() == 0) {
            return;
        }
    }
}
