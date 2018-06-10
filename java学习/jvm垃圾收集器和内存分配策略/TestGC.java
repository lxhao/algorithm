package jvm垃圾收集器和内存分配策略;

import java.util.*;

public class TestGC {
    public static void main(String args[]) {
        MyThread t1 = new MyThread();
        MyThread t2 = new MyThread();
        t1.brother = t2;
        t2.brother = t1;
        t1.start();
        //置引用为null
        t1 = null;
        t2 = null;
        //尝试进行垃圾回收
        System.gc();
        System.out.println("尝试在线程运行过程中进行垃圾回收");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.gc();
        System.out.println("尝试在线程运行结束后进行垃圾回收");
        //等待垃圾收集线程完成回收
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class MyThread extends Thread {
    MyThread brother;

    public void run() {
        System.out.println(Thread.currentThread().getName() + "开始执行");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "运行结束");
    }

    public void finalize() {
        System.out.println("准备回收对象->" + this.toString());
    }
}

