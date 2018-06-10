package 计算数组最大间隔;

import java.util.*;

public class Main {
   public static void main(String args[]) {
       int[] numbers = {7, 6, 5, 3, 2, 9};
       int maxDistance = getMaxDistance(numbers);
       System.out.println(maxDistance);
   }

   public static int getMaxDistance(int[] numbers) {
       int min = numbers[0];
       int maxDistance = 0;
       for(int e : numbers) {
           maxDistance = Math.max(e - min, maxDistance);
           e = Math.min(e, min);
       }
       return maxDistance;
   }
}


