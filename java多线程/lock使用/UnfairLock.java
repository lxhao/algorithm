import java.util.*;
import java.util.concurrent.locks.*;

public class UnfairLock {
    public static void main(String args[]) {
        // System.out.println("公平锁按先入先出的方式分配锁:");
        // Service service = new Service(true);
        // for(int i = 0; i < 10; i++) {
            // new MyThread(service).start();
        // }

        // try{
            // Thread.sleep(2000);
        // } catch(InterruptedException e) {

        // }
        System.out.println("非公平锁按随机分配锁:");
        Service service = new Service(false);
        for(int i = 0; i < 10; i++) {
            new MyThread(service).start();
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
        System.out.printf("%s start running\n", Thread.currentThread().getName());
        service.testMethod();
    }
}

class Service {
    //是否创建公平锁
    private boolean isFair;
    private Lock lock;

    Service(boolean isFair) {
        this.isFair = isFair;
        this.lock = new ReentrantLock(isFair);
    }

    public void testMethod() {
        try {
            lock.lock();
            System.out.printf("%s excuted.\n", Thread.currentThread().getName());
            Thread.sleep(100);
        } catch(InterruptedException e) {

        } finally {
            lock.unlock();
        }
    }

}

