import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Scanner;

public class Main {
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            int n = scanner.nextInt();
            BigDecimal[] f = new BigDecimal[n + 2];
            f[1] = new BigDecimal(1);
            for (int i = 2; i <= n; i += 2) {
                f[i + 1] = f[i] = f[i - 1].add(f[i / 2]);
            }
            System.out.println(f[n].toBigInteger().mod(new BigInteger("1000000000")));
        }
        scanner.close();
    }
}