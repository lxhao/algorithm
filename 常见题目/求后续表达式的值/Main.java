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
