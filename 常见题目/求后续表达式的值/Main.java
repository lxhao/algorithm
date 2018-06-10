package 求后续表达式的值;

public class Main {
    //print program info
    public static void main(String args[]) {
        try {
            new Calculator().runCalculator();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }
}
