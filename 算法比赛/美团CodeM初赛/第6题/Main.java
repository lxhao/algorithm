package 美团CodeM初赛.第6题;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            System.setIn(new FileInputStream("input.txt"));
        } catch (FileNotFoundException ignored) {
        }
        Scanner in = new Scanner(System.in);

        int a = in.nextInt();
        int b = in.nextInt();
        BigDecimal biga = new BigDecimal(String.valueOf(a));
        BigDecimal bigb = new BigDecimal(String.valueOf(b));

        MathContext mc = new MathContext(100000);
        String mulRes = String.valueOf(biga.divide(bigb, mc));
        mulRes = mulRes.substring(mulRes.lastIndexOf('.'));

        String text = in.next();
        int pos = mulRes.indexOf(text);
        System.out.println(pos);
    }
}
