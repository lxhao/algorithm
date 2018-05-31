import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        try {
            System.setIn(new FileInputStream("input.txt"));
        } catch (FileNotFoundException ignored) {
        }
//        StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
//        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {

        }
    }
}

