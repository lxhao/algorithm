import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        System.setIn(new FileInputStream("input.txt"));
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            int n = in.nextInt();
            System.out.println(multi(n));
        }
        in.close();
    }

    private static String multi(int n) {
        if (n <= 1) {
            return String.valueOf(n);
        }
        BigInteger bg = new BigInteger("1");
        for (int i = 1; i <= n; i++) {
            bg = bg.multiply(BigInteger.valueOf(i));
        }
        return bg.toString();
    }
}

