import java.util.*;

/**
 * 计数排序
 */
public class CountSort {
   public static void main(String args[]) {
       int[] numbers = {1, 5, 8, 1, 2, 6,  7, 8, 8};
       int[] sortedArr = countSort(numbers);
       for(int e : sortedArr) {
           System.out.print(e + " ");
       }
   }

   //要求数组最大元素不超过数组长度
   public static int[] countSort(int[] numbers) {
       int[] countArr = new int[numbers.length];
       for(int e : numbers) {
           countArr[e]++;
       }
       //统计小于等于每个元素的元素数量
       for(int i = 1; i < countArr.length; i++) {
           countArr[i] = countArr[i - 1] + countArr[i];
       }

       //根据统计结果排序
       int[] sortedArr = new int[numbers.length];
       for(int e : numbers) {
           sortedArr[countArr[e] - 1] = e;
           countArr[e]--;
       }
       return sortedArr;
   }
}


