import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

//测试单例
public class Main {
    public static void main(String args[]) {

        Main main = new Main();
        System.out.println("");

        //不同的类加载器测试
        main.testClassLoader();
        System.out.println("");

        //序列化测试
        main.testSerializable();
        System.out.println("");

        //反射测试
        main.testReflect();
        System.out.println("");

        //克隆测试
        main.testClone();
        System.out.println("");

        //枚举类反射测试
        main.testEnumReflected();
        System.out.println("");

        //枚举类序列化测试
        main.testEnumSerializable();
        System.out.println("");
    }

    private void testEnumSerializable() {
        String objFilename = "enumObject";
        //写入单例对象到文件
        ObjectOutputStream oos;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(objFilename));
            oos.writeObject(EnumSingleton.INSTANCE);
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(objFilename));
            EnumSingleton newInstance = (EnumSingleton) ois.readObject();
            System.out.println("枚举类序列化后还是同一个对象吗？" + (newInstance == EnumSingleton.INSTANCE));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void testEnumReflected() {
        System.out.println("测试枚举反射");
        EnumSingleton singleton = EnumSingleton.INSTANCE;
        try {
            Constructor<EnumSingleton> constructor = EnumSingleton.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            EnumSingleton objReflected = (EnumSingleton) constructor.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        System.out.println("");
    }

    private void testClone() {
        try {
            DbCheckSingleton objCloned = (DbCheckSingleton) DbCheckSingleton.getInstance().clone();
            System.out.println("克隆后还是同一个对象吗？" +
                    (objCloned == DbCheckSingleton.getInstance()));
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    private void testReflect() {
        try {
            Constructor constructor = DbCheckSingleton.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            DbCheckSingleton objReflected = (DbCheckSingleton) constructor.newInstance();
            System.out.println("反射后还是同一个对象吗？" +
                    (objReflected == DbCheckSingleton.getInstance()));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void testSerializable() {
        String objFilename = "object";
        //写入单例对象到文件
        ObjectOutputStream oos;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(objFilename));
            oos.writeObject(DbCheckSingleton.getInstance());
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(objFilename));
            DbCheckSingleton newInstance = (DbCheckSingleton) ois.readObject();
            System.out.println("序列化后还是同一个对象吗？" +
                    (newInstance == DbCheckSingleton.getInstance()));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


    private void testClassLoader() {
        MyClassLoader classLoader = new MyClassLoader("./output/");
        Class clazz;
        try {
            clazz = classLoader.findClass("DbCheckSingleton");
            Constructor constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            Object obj = constructor.newInstance();
            System.out.println("使用不同的类加载器后还是同一个对象吗？" +
                    (obj == DbCheckSingleton.getInstance()));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
