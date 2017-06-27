import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    private static char[] digits = new char[62];

    static {
        int index = 0;
        for (char i = '0'; i <= '9'; i++) {
            digits[index++] = i;
        }
        for (char i = 'a'; i <= 'z'; i++) {
            digits[index++] = i;
        }
        for (char i = 'A'; i <= 'Z'; i++) {
            digits[index++] = i;
        }
    }

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int from, to, number;
        while (in.hasNext()) {
            from = in.nextInt();
            to = in.nextInt();
            number = in.nextInt();
            if (number == 0) {
                System.out.println("0");
                continue;
            }
            boolean isMinus = false;
            if (number < 0) {
                isMinus = true;
            }
            List<Character> res = transform(from, to, isMinus ? -1 * number : number);
            if (isMinus) {
                System.out.print("-");
            }
            for (int index = 0; index < res.size(); index++) {
                System.out.print(res.get(index));
            }
            System.out.println("");
        }
    }

    private static List<Character> transform(int from, int to, int nummber) {
        List<Character> res = new ArrayList<>();
        long value = transformTen(from, nummber);
        for (; value > 0; ) {
            int digit = (int) (value % to);
            res.add(digits[digit]);
            value /= to;
        }
        Collections.reverse(res);
        return res;
    }

    private static long transformTen(int from, int number) {
        String numStr = String.valueOf(number);
        long tmp = 1;
        long res = 0;
        for (int i = numStr.length() - 1; i >= 0; i--) {
            res += tmp * (numStr.charAt(i) - '0');
            tmp *= from;
        }
        return res;
    }
}


