package testClass;

/**
 * 静态内部类实现单例
 */
public class StaticInnerClassSingleton {
    private static class SingletonHolder {
        private static final StaticInnerClassSingleton INSTANCE = new StaticInnerClassSingleton();
    }

    private StaticInnerClassSingleton() {
    }

    public static final StaticInnerClassSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }
}  