import java.util.*;
/**
 * 动态对象年龄判断
 * 我用server模式运行,执行结果和书上描述的有区别
 *
 * java -XX:+UseSerialGC -Xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=15 -XX:+PrintGCDetails TestTenuringThreshold
 */
public class TestTenuringThreshold2 {
    private static final int _1MB = 1024 * 1024;
    public static void main(String args[]) {
        byte[] b1, b2, b3, b4;
        b2 = new byte[_1MB / 4];
        b1 = new byte[_1MB * 1];
        b3 = new byte[_1MB * 4];
        b3 = null;
        b4 = new byte[_1MB * 6];
    }
}
