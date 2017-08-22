import java.util.*;

/**
 * java -XX:+UseSerialGC -XX:+PrintGCDetails -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8 TestAllocation
 *
 */
public class TestAllocation {
    private static final int _1MB = 1024 * 1024;
    public static void main(String args[]) {
        byte[] t1 = new byte[_1MB * 2];
        byte[] t2 = new byte[_1MB * 4];
        byte[] t3 = new byte[_1MB * 4];
    }
}

