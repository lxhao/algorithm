package jvm内存溢出测试;

import java.util.*;

public class JavaEthodAreaOOM {
   public static void main(String args[]) {
       while(true) {
           Enhancer enhance = new Enhancer();
       }
   }
}


