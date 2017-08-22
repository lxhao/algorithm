import java.util.*;

/**
 * 测试fullGC和MinorGC
 * -Xms20m -Xmx20m -Xmn10m -XX:SurvivorRatio=8 -XX:+PrintGCDetails
 *
 */
public class TestFullGCAndMinorGC {
    private static final int _1MB = 1024 * 1024;
    public static void main(String args[]) {
        byte[] b1 = new byte[3 * _1MB];
        System.out.println("new b1");
        byte[] b2 = new byte[3 * _1MB];
        System.out.println("new b2");
        byte[] b3 = new byte[4 * _1MB];
        System.out.println("new b3");
        byte[] b4 = new byte[3 * _1MB];
        System.out.println("new b4");
        byte[] b5 = new byte[3 * _1MB];
        System.out.println("new b5");
    }
}

