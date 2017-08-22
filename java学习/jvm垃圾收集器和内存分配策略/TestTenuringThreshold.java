import java.util.*;
/**
 * 长期存活的对象进入老年代
 *
 * java -XX:+UseSerialGC -Xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=1 -XX:+PrintGCDetails TestTenuringThreshold
 */
public class TestTenuringThreshold {
    private static final int _1MB = 1024 * 1024;
    public static void main(String args[]) {
        byte[] b1, b2, b3;
        b1 = new byte[_1MB * 2];
        b2 = new byte[_1MB * 2];
        b3 = new byte[_1MB * 2];
        b3 = null;
        b3 = new byte[_1MB * 4];
    }
}
