import java.util.*;

//堆排序
public class HeapSort {
    //随机测试次数
    private static final int TEST_TIMES = 10;
    //测试数据规模
    private static final int NUMBER_SIZE = 1000;

    public static void main(String args[]) {
        for (int j = 0; j < TEST_TIMES; j++) {
            //生成测试数据
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

    //建立最大堆
    public static void buildHeap(List<Integer> numbers, int startPos, int endPos) {
        for (int child, nowPos = startPos; nowPos * 2 + 1 <= endPos ; nowPos = child) {
            child = nowPos * 2 + 1;
            if (child + 1 <= endPos && numbers.get(child + 1) > numbers.get(child)) {
                child += 1;
            }
            if (numbers.get(child) > numbers.get(nowPos)) {
                Collections.swap(numbers, nowPos, child);
            } else {
                break;
            }
        }
    }

    public static void sort(List<Integer> numbers) {
        //从第一个父节点开始建立堆，叶子节点可以认为已经是堆排序的
        int len = numbers.size();
        for (int i = len / 2 - 1; i >= 0; i--) {
            buildHeap(numbers, i, len - 1);
        }

        for (int i = len - 1; i > 0; i--) {
            //把堆顶的最大元素放到后面，并重新堆化把剩余元素的最大值放到堆顶
            Collections.swap(numbers, i, 0);
            buildHeap(numbers, 0, i - 1);
        }
    }
}
