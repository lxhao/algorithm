import java.util.*;

public class ReferenceCountingGC {
    private static final int _1MB = 1024 * 1024;
    private byte[] bigSize = new byte[1 * _1MB];
    /**
     * 测试对象循环引用能否被gc
     */
    private ReferenceCountingGC instance;
    public static void main(String args[]) {
        new ReferenceCountingGC().testGC();
    }

    private void testGC() {
        ReferenceCountingGC a = new ReferenceCountingGC();
        ReferenceCountingGC b = new ReferenceCountingGC();
        a.instance = b;
        b.instance = a;

        a = null;
        b = null;

        System.gc();
    }
}

