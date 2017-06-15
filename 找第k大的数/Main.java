import java.util.*;

public class Main {
    //随机测试次数
    private static final int TEST_TIMES = 3;
    //测试数据规模
    private static final int NUMBER_SIZE = 1000000;

    public static void main(String args[]) {
        for (int j = 0; j < TEST_TIMES; j++) {
            Random rnd = new Random();
            List<Integer> numbers = new ArrayList<>();
            long startTime = System.currentTimeMillis();
            for (int i = 0; i < NUMBER_SIZE; i++) {
                numbers.add(rnd.nextInt(NUMBER_SIZE));
            }
            long endTime = System.currentTimeMillis();
            System.out.printf("初始数据耗时：%d\n", endTime - startTime);

            int k = numbers.size() / 2;
            // System.out.println(numbers);
            startTime = System.currentTimeMillis();
            int res = findTheKLargestByDivide(numbers, k, 0, numbers.size() - 1);
            endTime = System.currentTimeMillis();
            System.out.printf("快排方式耗时：%d\n", endTime - startTime);

            startTime = System.currentTimeMillis();
            int res1 = findTheKLargestByHeap(numbers, k);
            endTime = System.currentTimeMillis();
            assert(res1 == res);
            System.out.printf("堆方式耗时：%d\n", endTime - startTime);
            System.out.println("");
        }
    }

    //采用快排的分治方法找出第k大的数的位置
    public static int findTheKLargestByDivide(List<Integer> numbers, int k, int startPos, int endPos) {
        if (numbers == null || numbers.size() == 0) {
            return -1;
        }
        if (k < 0 || k > numbers.size() - 1) {
            return -1;
        }

        int left = startPos;
        int right = endPos;
        int tmp = numbers.get(left);

        while (left < right) {
            //大于等于tmp的放左边，小于tmp的放右边
            while (numbers.get(right) <= tmp && left < right) {
                right--;
            }
            numbers.set(left, numbers.get(right));

            while (numbers.get(left) > tmp && left < right) {
                left++;
            }
            numbers.set(right, numbers.get(left));
        }

        numbers.set(left, tmp);
        if (left == k - 1) {
            return tmp;
        } else if (k - 1 > left) {
            return findTheKLargestByDivide(numbers, k, left + 1, endPos);
        } else {
            return findTheKLargestByDivide(numbers, k, startPos, left - 1);
        }
    }

    //建立最大堆
    public static void buildMaxHead(List<Integer> numbers, int startPos, int endPos) {
        for (int nowPos = startPos, childPos; nowPos * 2 + 1 <= endPos; nowPos = childPos) {
            childPos = nowPos * 2 + 1;
            //得到最大的孩子
            if (childPos + 1 <= endPos && numbers.get(childPos + 1) > numbers.get(childPos)) {
                childPos++;
            }
            if (numbers.get(childPos) > numbers.get(nowPos)) {
                Collections.swap(numbers, childPos, nowPos);
            } else {
                break;
            }
        }

    }

    //建立最小堆
    public static void buildMinHead(List<Integer> numbers, int startPos, int endPos) {
        for (int nowPos = startPos, childPos; nowPos * 2 + 1 <= endPos; nowPos = childPos) {
            childPos = nowPos * 2 + 1;
            //得到最大的孩子
            if (childPos + 1 <= endPos && numbers.get(childPos + 1) < numbers.get(childPos)) {
                childPos++;
            }
            if (numbers.get(childPos) < numbers.get(nowPos)) {
                Collections.swap(numbers, childPos, nowPos);
            } else {
                break;
            }
        }

    }

    //采用堆排序的方法找出第k大的数的位置
    public static int findTheKLargestByHeap(List<Integer> numbers, int k) {
        if (numbers == null || numbers.size() == 0 || k < 0 || k > numbers.size() - 1) {
            throw new IllegalArgumentException("参数错误");
        }
        int i;
        if (numbers.size() / 2 < k) {
            for (i = numbers.size() / 2 - 1; i >= 0; i-- ) {
                buildMinHead(numbers, i, numbers.size() - 1);
            }

            for (i = numbers.size() - 1; i >= k - 1; i--) {
                Collections.swap(numbers, 0, i);
                buildMinHead(numbers, 0, i - 1);
            }
            return numbers.get(i + 1);
        }

        for (i = numbers.size() / 2 - 1; i >= 0; i-- ) {
            buildMaxHead(numbers, i, numbers.size() - 1);
        }

        for (i = numbers.size() - 1; i >= numbers.size() - k; i--) {
            Collections.swap(numbers, 0, i);
            buildMaxHead(numbers, 0, i - 1);
        }
        return numbers.get(i + 1);

    }
}
