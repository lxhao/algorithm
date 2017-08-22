import java.util.*;
/**
 * 大对象直接进入老年代
 * java -XX:+UseSerialGC -Xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8 -XX:PretenureSizeThreshold=3000000 -XX:+PrintGCDetails TestPretenureSizeThreshold
 *
 */
public class TestPretenureSizeThreshold {
    private static final int _1MB = 1024 * 1024;
    public static void main(String args[]) {
        byte[] b = new byte[4 * _1MB];
    }
}
