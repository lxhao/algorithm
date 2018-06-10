package 素数;

import java.util.*;

public class Main {
   public static void main(String args[]) {
       System.out.println(2 + " ");
       int flag;
       for(int i = 3; i <= 100; i++) {

           flag = (int)Math.sqrt(i) + 1;
           int j = 0;
           for(j = 2; j < flag; j++) {
               if(i % j == 0) {
                   break;
               }
           }
           if(j == flag) {
               System.out.print(i + " ");
           }
       }
   }
}


