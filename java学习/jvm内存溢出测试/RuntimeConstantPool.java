package jvm内存溢出测试;

import java.util.*;

/**
 * String.intern()方法:把字符串加入常量池,然后返回常量池保存的该字符串的地址
 * ps : 如果该字符串不存在,常量池会保存该字符串的引用,返回的地址就是当前字符串的地址
 */
public class RuntimeConstantPool {
   public static void main(String args[]) {
      // 从java7开始把常量池放在堆上, str1.intern()返回堆上的地址, 就是str1的地址
       String str1 = new StringBuilder("计算机").append("软件").toString();
       System.out.println(str1.intern() == str1);

      // "java"已经存在了,所以str.intern()返回的地址和新new出来的str2的地址不一样
       String str2 = new StringBuilder("ja").append("va").toString();
       System.out.println(str2.intern() == str2);
   }
}

