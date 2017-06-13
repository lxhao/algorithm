import java.util.*;

//快速排序
public class QuickSort {
    private static final Random rnd = new Random();
    //随机测试次数
    private static final int TEST_TIMES = 100;
    //测试数据规模
    private static final int NUMBER_SIZE = 10;

    public static void main(String args[]) {
        for (int j = 0; j < TEST_TIMES; j++) {
            List<Integer> numbers = new ArrayList<>();
            for (int i = 0; i < NUMBER_SIZE; i++) {
                numbers.add(rnd.nextInt(100));
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
        divideSort(numbers, 0, numbers.size() - 1);
    }

    public static void divideSort(List<Integer> numbers, int left, int right) {
        //递归终止条件
        if (left >= right) {
            return;
        }

        int i = left;
        int j = right;
        //随机与一个元素交换
        Collections.swap(numbers, left, rnd.nextInt(right - left + 1) + left);
        //临时变量
        Integer tmp = numbers.get(left);


        //小于tmp的放tmp左边，大于等于tmp的放tmp右边
        while (i < j) {
            while (numbers.get(j) >= tmp && i < j) {
                j--;
            }
            numbers.set(i, numbers.get(j));

            while (numbers.get(i) < tmp && i < j) {
                i++;
            }
            numbers.set(j, numbers.get(i));
        }
        numbers.set(i, tmp);

        divideSort(numbers, left, i - 1);
        divideSort(numbers, i + 1, right);
    }
}
