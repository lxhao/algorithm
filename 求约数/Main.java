import java.util.*;

public class Main {
    public static void main(String args[]) {
        List<Integer> primeNumbers = new ArrayList<>();
        Scanner in = new Scanner(System.in);
        primeNumbers.add(2);
        int n;
        while (in.hasNext()) {
            n = in.nextInt();
            for (int i = 3; i < n; i++) {
                if (isPrime(i, primeNumbers)) {
                    primeNumbers.add(i);
                }
            }
        }
        getDivisors(n, primeNumbers);
        //求约数
    }
    public static List<Integer> getDivisors(int n, List<Integer> primeNumbers) {

    }

    //判断一个自然数是否是质数
    public static boolean isPrime(int k, List<Integer> prime) {
        if (k <= 0) {
            return false;
        }
        if (k % 2 == 0) {
            return false;
        }
        int stop = (int) Math.sqrt(k) + 1;
        for (int i = 0; i < prime.size() && prime.get(i) < stop; i++) {
            if (k % prime.get(i) == 0) {
                return false;
            }
        }
        return true;
    }
}
