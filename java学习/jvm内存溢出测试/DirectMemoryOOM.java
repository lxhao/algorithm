package jvm内存溢出测试;

import java.util.*;
import java.lang.reflect.*;
import sun.misc.Unsafe;

/**
 * 本机直接内存溢出
 * jdk1.8  -Xmx20M -XX:MaxDirectMemorySize=10M
 */
public class DirectMemoryOOM {
    private static final int _1MB = 1024 * 1024;

    public static void main(String args[]) {
        Field unsafeField = Unsafe.class.getDeclaredFields()[0];
        unsafeField.setAccessible(true);
        Unsafe  unsafe = null;
        try {
            unsafe = (Unsafe) unsafeField.get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 20; i++)
            unsafe.allocateMemory(_1MB);
    }
}
