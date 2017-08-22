import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class TestCondition {
    public static void main(String args[]) {
        Service service = new Service();
        Thread thread = new MyThread(service);
        thread.setName("thread-a");
        thread.start();
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {

        }
        service.signal();
    }
}

class Service {
    private Lock lock = new ReentrantLock();
    private Condition  condition = lock.newCondition();

    public void testMethod() {
        lock.lock();
        System.out.printf("thread:%s is waiting.\n", Thread.currentThread().getName());
        try {
            condition.await();
        } catch (InterruptedException e) {

        } finally {
            lock.unlock();
        }
        System.out.printf("thread:%s is waked.", Thread.currentThread().getName());
    }

    public void signal() {
        try {
            lock.lock();
            condition.signal();
        } finally {
            lock.unlock();
        }
    }
}

class MyThread extends Thread {
    private Service service;

    MyThread(Service service) {
        this.service = service;
    }

    @Override
    public void run() {
        service.testMethod();
    }
}
