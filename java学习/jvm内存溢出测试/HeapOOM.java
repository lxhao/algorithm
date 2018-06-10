package jvm内存溢出测试;

import java.util.*;
/**
 *
 * 测试堆溢出
 * java -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError -XX:+UseSerialGC HeapOOM
 */
public class HeapOOM {
   public static void main(String args[]) {
       new HeapOOM().test();
   }

   private void test() {
       List<Object> list = new ArrayList<>();
       while(true) {
           list.add(new Object());
           System.out.println(list.size());
       }
   }
}


