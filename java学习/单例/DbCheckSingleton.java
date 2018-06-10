package 单例;

import java.io.Serializable;

/**
 * 双重检查锁实现单例
 */
public class DbCheckSingleton implements Serializable, Cloneable {
    private static volatile DbCheckSingleton instance;

    private DbCheckSingleton() {
        if (instance != null) {
            throw new IllegalAccessError("单例对象不能被多次创建");
        }
        System.out.println(this.getClass().getSimpleName() + " 类加载器=" + this.getClass().getClassLoader());
    }

    public static DbCheckSingleton getInstance() {
        if (instance == null) {
            synchronized (DbCheckSingleton.class) {
                if (instance == null) {
                    instance = new DbCheckSingleton();
                }
            }
        }
        return instance;
    }

    private Object readResolve() {
        return instance;
    }

    private static Class getClass(String classname) throws ClassNotFoundException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null) {
            classLoader = DbCheckSingleton.class.getClassLoader();
        }
        return (classLoader.loadClass(classname));
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("单例对象不支持克隆");
//        return super.clone();
    }
}

