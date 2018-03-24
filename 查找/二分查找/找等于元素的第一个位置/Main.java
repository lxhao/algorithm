package 二分查找.找等于元素的第一个位置;

import java.util.*;

public class Main {
    //随机测试次数
    private static final int TEST_TIMES = 10;
    //测试数据规模
    private static final int NUMBER_SIZE = 10;

    public static void main(String args[]) {
        for (int j = 0; j < TEST_TIMES; j++) {
            //生成测试数据
            Random rnd = new Random();
            List<Integer> numbers = new ArrayList<>();
            for (int i = 0; i < NUMBER_SIZE; i++) {
                numbers.add(rnd.nextInt(NUMBER_SIZE));
            }
            System.out.println(numbers);
            Collections.sort(numbers);
            System.out.println(numbers);
            int pos = find(numbers, 0, numbers.size() - 1, 8);
            System.out.println(pos);
            System.out.println("");

        }
    }

    public static int find(List<Integer> numbers, int start, int end, int e) {
        int low  = start;
        int high = end;
        int mid;

        while(low < high) {
            mid = (low + high) >>> 1;
            if(numbers.get(mid) > e) {
                high = mid - 1;
            } else if(numbers.get(mid) < e) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }

        return numbers.get(low) == e ? low : -1;
    }
}

