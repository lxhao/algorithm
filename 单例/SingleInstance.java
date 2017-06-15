import java.util.*;

/**
 * 恶汉单例模式
 *
 * 优点： 实现简单
 * 缺点： 1. 在类加载时便创建了对象，浪费资源
 *        2. 如果对象初始化依赖一些其它数据，很难保证其它数据在它初始化之前准备好
 *
 * 实现单例的理由：虚拟机能够保证类加载时线程同步
 * 适用场景：对象创建占用资源少，不依赖其余数据
 */
public class SingleInstance {
    //记录该类别实例次数
    private static int createdTimes = 0;
    private static final SingleInstance instance = new SingleInstance();

    private SingleInstance() {
        setCreatedTimes(createdTimes + 1);
    }

    public static SingleInstance getInstance() {
        return instance;
    }

    public synchronized static int getCreatedTimes() {
        return createdTimes;
    }

    public synchronized static void setCreatedTimes(int createdTimes) {
        SingleInstance.createdTimes = createdTimes;
    }
}


