import java.util.*;
import java.util.concurrent.locks.*;

public class ProductorConsumer {
    private static final int PROCTOR_NUMBER = 10;
    private static final int CONSUMER_NUMBER = 10;

    public static void main(String args[]) {

        Storage storage = new Storage();
        for(int i = 0; i < PROCTOR_NUMBER; i++) {
            new ProductorThread(storage).start();
        }

        Thread[] consumers = new Thread[CONSUMER_NUMBER];
        for(int i = 0; i < CONSUMER_NUMBER; i++) {
            new ConsumerThread(storage).start();
        }
    }
}

class ProductorThread extends Thread {
    private Storage storage;

    ProductorThread(Storage storage) {
        this.storage = storage;
    }

    @Override
    public void run() {
        while(true) {
            storage.product();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {

            }
        }
    }
}


class ConsumerThread extends Thread {
    private Storage storage;

    ConsumerThread(Storage storage) {
        this.storage = storage;
    }

    @Override
    public void run() {
        while(true) {
            storage.consumer();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {

            }
        }
    }
}

//仓库
class Storage {
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    private List<Integer> list = new ArrayList<>();
    private int MAX_CAPACITY = 10;

    //生产
    public void product() {
        lock.lock();
        try {
            if (list.size() == MAX_CAPACITY) {
                System.out.println("缓存区满");
                condition.await();
            }
            //再次判断，有可能多个线程由于容量满而进入等待状态
            if (list.size() == MAX_CAPACITY) {
                System.out.println("缓存区满");
            } else {
                //加入一个产品
                list.add(1);
            }
            System.out.printf("生产后缓存区容量:%d\n", list.size());
            condition.signalAll();
        } catch (InterruptedException e) {

        } finally {
            lock.unlock();
        }

    }

    //消费
    public void consumer() {
        lock.lock();
        try {
            if (list.size() == 0) {
                System.out.println("缓存区空");
                condition.await();
            }
            if (list.size() == 0) {
                System.out.println("缓存区空");
            } else {
                //移除第一个元素
                list.remove(0);
            }
            System.out.printf("消费后缓存区容量:%d\n", list.size());
            condition.signalAll();
        }  catch (InterruptedException e) {

        } finally {
            lock.unlock();
        }
    }

}
