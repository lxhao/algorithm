import java.util.*;
import java.math.*;

public class Main {
    public static void main(String args[]) {
        int TIMES = 10 * (int)Math.pow(10, 8);
        long startTime = System.currentTimeMillis();
        BigInteger sum = new BigInteger("0");
        for(int i = 0; i < TIMES; i++ ) {
            // sum.add(BigInteger.valueOf(i));
        }
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
        System.out.println(sum);
    }
}


