import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Template {
    public static void main(String[] args) throws FileNotFoundException {
        System.setIn(new FileInputStream("input.txt"));
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
        }
        in.close();
    }
}

