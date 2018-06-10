package java多线程.lock使用.testconditon2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestCondition2 {
    public static void main(String args[]) {
        Service service = new Service();
        Thread threadA = new MyThreadA(service);
        threadA.setName("thread-a");
        threadA.start();
        Thread threadB = new MyThreadB(service);
        threadB.setName("thread-a");
        threadB.start();
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {

        }
        service.signalB();
        service.signalA();
    }
}

class Service {
    private Lock lock = new ReentrantLock();
    private Condition  conditionA = lock.newCondition();
    private Condition  conditionB = lock.newCondition();

    public void waitB() {
        lock.lock();
        System.out.printf("conditionB is used.\n");
        try {
            conditionB.await();
        } catch (InterruptedException e) {

        } finally {
            lock.unlock();
        }
        System.out.printf("conditionB receive signal.\n");
    }

    public void waitA() {
        lock.lock();
        System.out.printf("conditionA is used.\n");
        try {
            conditionA.await();
        } catch (InterruptedException e) {

        } finally {
            lock.unlock();
        }
        System.out.printf("conditionA receive signal.\n");
    }

    public void signalA() {
        try {
            lock.lock();
            conditionA.signal();
        } finally {
            lock.unlock();
        }
    }

    public void signalB() {
        try {
            lock.lock();
            conditionB.signal();
        } finally {
            lock.unlock();
        }
    }

}

class MyThreadA extends Thread {
    private Service service;

    MyThreadA(Service service) {
        this.service = service;
    }

    @Override
    public void run() {
        service.waitA();
    }
}

class MyThreadB extends Thread {
    private Service service;

    MyThreadB(Service service) {
        this.service = service;
    }

    @Override
    public void run() {
        service.waitB();
    }
}
