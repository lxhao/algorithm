import java.util.*;

//测试单例
public class Main {
   public static void main(String args[]) {
       //20个线程并发创建对象实例, 如果创建了多个实例，会增加createdTimes的值
       for(int i = 0; i < 20; i++) {
           new CreatedObjectThread().start();
       }
   }
}

class CreatedObjectThread extends Thread {
    @Override
    public void run() {
        for(int i = 0; i < 10; i++) {
            SingleInstance.getInstance();
        }
        System.out.println(SingleInstance.getCreatedTimes());
    }
}


