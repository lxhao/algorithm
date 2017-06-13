import java.util.*;

public class BucketSort {
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

    public static void sort(List<Integer> numbers) {
        if(numbers == null || numbers.size() == 0) {
            return;
        }
        //得到最值
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for (Integer e : numbers) {
            max = Math.max(max, e);
            min = Math.min(min, e);
        }

        //桶大小
        int bucketSize = (max - min) / numbers.size() + 1;
        //桶
        List<List<Integer>> buckets = new ArrayList<>();
        for (int i = 0; i < bucketSize; i++) {
            buckets.add(new ArrayList<>());
        }

        //把数据放入桶中
        for (Integer e : numbers ) {
            buckets.get((e - min) % bucketSize).add(e);
        }

        //对每个桶的元素排序
        for (List<Integer> bucket : buckets) {
            Collections.sort(bucket);
        }

        //保存排序后的元素
        int i = 0;
        for (List<Integer> bucket : buckets) {
            for (Integer elem : bucket) {
                numbers.set(i++, elem);
            }
        }

    }
}

