package java多线程.lock使用;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestLock {
    public static void main(String args[]) {
        MyService service = new MyService();
        new MyThread(service).start();
        new MyThread(service).start();
        new MyThread(service).start();
        new MyThread(service).start();
        new MyThread(service).start();
        new MyThread(service).start();
    }
}

class MyService {
    private Lock lock = new ReentrantLock();

    //使用lock代替synchronized
    public void testMethod() {
        lock.lock();
        for(int i = 0; i < 5; i++) {
            System.out.printf("ThreadName:%s %d\n", Thread.currentThread().getName(), i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {

            }
        }
        lock.unlock();
    }
}

class MyThread extends Thread {
    private MyService service;

    MyThread(MyService service) {
        this.service = service;
    }

    @Override
    public void run() {
        service.testMethod();
    }
}


