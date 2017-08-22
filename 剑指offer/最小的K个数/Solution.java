package 最小的K个数;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 题目描述
 * 输入n个整数，找出其中最小的K个数。例如输入4,5,1,6,2,7,3,8这8个数字，则最小的4个数字是1,2,3,4,。
 */
public class Solution {
    public static void main(String[] args) {
        int[] input = {4, 5, 1, 6, 2, 7, 3, 8};
        Solution solution = new Solution();
        System.out.println(solution.GetLeastNumbers_Solution(input, 8));
    }

    public ArrayList<Integer> GetLeastNumbers_Solution(int[] input, int k) {
        ArrayList<Integer> res = new ArrayList<>();
        if (input == null || k <= 0 || k > input.length) {
            return res;
        }
        //方法一
//        int[] inputCopied = Arrays.copyOf(input, input.length);
//        partion(input, 0, inputCopied.length - 1, k - 1);
//        for (int i = 0; i < k; i++) {
//            res.add(input[i]);
//        }
//        Collections.sort(res);
//        return res;

        /**
         * 方法二
         * 初始化一个大小为k的最大堆,依次加入剩下的元素,如果小于堆顶元素则移除堆顶元素并堆化
         * 时间复杂度为nlog(k),适合处理海量数据
         */
        int[] heap = initHeap(input, k);
        for (int i = k; i < input.length; i++) {
            if (input[i] < heap[0]) {
                heap[0] = input[i];
                heap(heap, 0, heap.length - 1);
            }
        }
        for (int i = 0; i < k; i++) {
            res.add(heap[i]);
        }
        Collections.sort(res);
        return res;
    }


    /**
     * 读取input的k个元素初始化大小为k的堆数组
     *
     * @param input
     * @param k
     * @return
     */
    private int[] initHeap(int[] input, int k) {
        int[] heap = new int[k];
        for (int i = 0; i < k; i++) {
            heap[i] = input[i];
        }
        for (int i = 0; i < heap.length; i++) {
            heap(heap, i, heap.length - 1);
        }
        return heap;
    }

    /**
     * 最大堆堆化数组区间startPos到stopPos
     *
     * @param heap
     * @param startPos
     * @param stopPos
     */
    private void heap(int[] heap, int startPos, int stopPos) {
        for (int parent = startPos, child; parent * 2 + 1 <= stopPos; parent = child) {
            child = parent * 2 + 1;
            if (child + 1 <= startPos && heap[child + 1] > heap[child]) {
                child++;
            }
            if (heap[parent] < heap[child]) {
                swap(heap, parent, child);
            } else {
                break;
            }
        }
    }

    private void swap(int[] arr, int p1, int p2) {
        int tmp = arr[p1];
        arr[p1] = arr[p2];
        arr[p2] = tmp;
    }

    /**
     * 方法一:
     * 用快排的思想把最小的k个数移动到数组前面的k个位置
     * 时间复杂度为0(n)
     */
    private void partion(int[] input, int startPos, int endPos, int k) {
        int t = input[startPos];
        int left = startPos;
        int right = endPos;
        while (left < right) {
            while (left < right && input[right] > t) {
                right--;
            }
            input[left] = input[right];

            while (left < right && input[left] <= t) {
                left++;
            }
            input[right] = input[left];
        }
        input[left] = t;
        if (left == k) {
            return;
        } else if (left > k) {
            partion(input, startPos, left - 1, k);
        } else {
            partion(input, left + 1, endPos, k);
        }
    }
}