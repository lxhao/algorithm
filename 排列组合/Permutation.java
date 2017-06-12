import java.util.*;

public class Permutation {
   public static void main(String args[]) {
       String s = "abc";
       List<String> res = new ArrayList<>();
       permutation(s.toCharArray(), 0, res);
       System.out.println(res);
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


