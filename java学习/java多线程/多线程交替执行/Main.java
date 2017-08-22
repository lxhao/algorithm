import java.util.*;
import java.util.concurrent.locks.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    private static final int THREAD_SIZE = 4;
    public static void main(String args[]) {
        Object lock = new Object();

        MyThread thread0 = new MyThread(0, THREAD_SIZE, lock);
        thread0.setName("thread-0");
        thread0.start();

        MyThread thread1 = new MyThread(1, THREAD_SIZE, lock);
        thread1.setName("thread-1");
        thread1.start();

        MyThread thread2 = new MyThread(2, THREAD_SIZE, lock);
        thread2.setName("thread-2");
        thread2.start();

        MyThread thread3 = new MyThread(3, THREAD_SIZE, lock);
        thread3.setName("thread-3");
        thread3.start();
    }
}

class MyThread extends Thread {
    private final int myPos;
    private int threadsSize;
    private Object lock;
    //用原子整形保存一个多个线程共享的变量
    private static AtomicInteger nowPos = new AtomicInteger();


    MyThread(int myPos, int threadsSize, Object lock) {
        this.myPos = myPos;
        this.threadsSize = threadsSize;
        this.lock = lock;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (lock) {
                try {
                    if (nowPos.getAndIncrement() % threadsSize == myPos) {
                        System.out.printf("%s is running.\n", Thread.currentThread().getName());
                        Thread.sleep(1000);
                        lock.notify();
                        lock.wait();
                    }
                } catch (InterruptedException ignore) {
                }
            }
        }
    }
}
