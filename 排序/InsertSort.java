import java.util.*;

//插入排序
public class InsertSort {
    //随机测试次数
    private static final int TEST_TIMES = 10;
    //测试数据规模
    private static final int NUMBER_SIZE = 100;

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
        int j, tmp;
        for(int i = 1; i < numbers.size(); i++) {
            tmp = numbers.get(i);
            for(j = i - 1; j >= 0; j--) {
                if(numbers.get(j) > tmp) {
                    //往后移动
                    numbers.set(j + 1, numbers.get(j));
                } else {
                    //插入下标为i的元素
                    numbers.set(j + 1, tmp);
                    break;
                }
            }
            if(j < 0) {
                numbers.set(0, tmp);
            }
        }
    }
}

