import java.util.*;

public class CountSort {
    public static void main(String args[]) {
        Random rnd = new Random();
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            numbers.add(rnd.nextInt(10));
        }
        System.out.println(numbers);
        sort(numbers);
        System.out.println(numbers);
    }

    //计数排序，list中元素的取值范围为1 -> numbers.size();
    public static void sort(List<Integer> numbers) {
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

