import java.util.*;

public class BucketSort {
   public static void main(String args[]) {
       Random rnd = new Random();
       List<Integer> numbers = new ArrayList<>();
       for(int i = 0; i < 10; i++) {
           numbers.add(rnd.nextInt(100));
       }
       System.out.println(numbers);
       sort(numbers);
       System.out.println(numbers);
   }

   public static void sort(List<Integer> numbers) {
       //得到最值
       int max = Integer.MIN_VALUE;
       int min = Integer.MAX_VALUE;
       for(Integer e : numbers) {
           max = Math.max(max, e);
           min = Math.min(min, e);
       }

       //桶大小
       int bucketSize = (max - min) / numbers.size() + 1;
       //桶
       List<List<Integer>> buckets = new ArrayList<>();
       for(int i = 0; i < bucketSize; i++) {
           buckets.add(new ArrayList<>());
       }

       //把数据放入桶中
       for(Integer e : numbers ) {
           buckets.get((e - min) % bucketSize).add(e);
       }

       //对每个桶的元素排序
       for(List<Integer> bucket : buckets) {
           Collections.sort(bucket);
       }

       //保存排序后的元素
        int i = 0;
       for(List<Integer> bucket : buckets) {
           for(Integer elem : bucket) {
               numbers.set(i++, elem);
           }
       }

   }
}


