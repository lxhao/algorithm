import java.util.*;

public class Permutation {
   public static void main(String args[]) {
       long startTime = System.currentTimeMillis();
       String s = "1234567890";
       List<String> res = new ArrayList<>();
       permutation(s.toCharArray(), 0, res);
       for(String e : res) {
           System.out.println(e);
       }
       long endTime = System.currentTimeMillis();
       System.out.println((endTime - startTime) / 1000.0 / 60);
   }

   public static void permutation(char[] charArr, int start, List<String> res) {
       if(start == charArr.length - 1) {
           res.add(new String(charArr));
       }
       for(int i = start; i < charArr.length; i++) {
           swap(charArr, i, start);
           permutation(charArr, start + 1, res);
           swap(charArr, start, i);
       }
   }

   private static void swap(char[] charArr, int from, int to) {
       char temp = charArr[from];
       charArr[from] = charArr[to];
       charArr[to] = temp;
   }
}


