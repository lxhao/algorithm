package 数组和ArrayList相互转换;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
   public static void main(String args[]) {
       String[] arr = {"hello", "world"};
       //数组转ArrayList, 可以删除和修改
       ArrayList<String> toList = new ArrayList<String>(arr.length);
       for(String s : arr) {
           toList.add(s);
       }
       System.out.println(toList);

       //数组转List, 不可以删除和修改
       List<String> toList2 = Arrays.asList(arr);
       System.out.println(toList2);

       //ArrayList转数组, 可以删除和修改
       String[] toArr = toList.toArray(new String[toList.size()]);
       for(String s : toArr) {
           System.out.print(s + " ");
       }
       System.out.println("");
   }
}


