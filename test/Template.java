import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Template {

    public static void main(String[] args) throws IOException {
        try {
            System.setIn(new FileInputStream("input.txt"));
        } catch (FileNotFoundException ignored) {
        }
        Scanner in = new Scanner(System.in);
//        StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
//        in.nextToken();
//        int n = (int) in.nval;
    }
}
