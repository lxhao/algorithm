import java.util.*;
/**
 * 是否开启指针压缩对对象大小的影响
 * java -javaagent:SizeOfObject.jar -XX:-UseCompressedOops TestObjectSize
 * java -javaagent:SizeOfObject.jar -XX:+UseCompressedOops TestObjectSize
 */

public class TestObjectSize {
    //obj 12bit
    private static int[] b = new int[2];//8bit
    public static void main(String args[]) {
        System.out.println(SizeOfObject.sizeOf(b));
    }
}

class A {
    int a;
}


