import java.util.*;

//归并排序
public class MergeSort {

    //随机测试次数
    private static final int TEST_TIMES = 5;
    //测试数据规模
    private static final int NUMBER_SIZE = 10;

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
        Integer[] tmp = new Integer[numbers.size()];
        mergeSort(numbers, 0, numbers.size() - 1, tmp);
    }

    public static void mergeSort(List<Integer> numbers, int left, int right, Integer[] tmp) {
        if (left >= right) {
            return;
        }

        int mid = (left + right) >>> 1;
        mergeSort(numbers, left, mid, tmp);
        mergeSort(numbers, mid + 1, right, tmp);
        mergeNumbers(numbers, left, mid, right, tmp);
    }

    public static void mergeNumbers(List<Integer> numbers, int left, int mid, int right, Integer[] tmp) {
        int i = left;
        int iEnd = mid;
        int j = mid + 1;
        int jEnd = right;

        int index = left;

        while (i <= iEnd && j <= jEnd) {
            if (numbers.get(i) < numbers.get(j)) {
                tmp[index++] = numbers.get(i++);
            } else {
                tmp[index++] = numbers.get(j++);
            }
        }

        while (i <= iEnd) {
            tmp[index++] = numbers.get(i++);
        }

        while (j <= jEnd) {
            tmp[index++] = numbers.get(j++);
        }

        for (index = left; index <= right; index++ ) {
            numbers.set(index, tmp[index]);
        }
    }
}
