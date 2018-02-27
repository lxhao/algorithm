package 第四场.求交集;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        try {
            System.setIn(new FileInputStream("input.txt"));
        } catch (FileNotFoundException ignored) {
        }
        StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
        PrintWriter out = new PrintWriter(System.out);
        while (in.nextToken() != StreamTokenizer.TT_EOF) {
            int n = (int) in.nval;
            in.nextToken();
            int m = (int) in.nval;
            int[] numbers1 = new int[n];
            for (int i = 0; i < n; i++) {
                in.nextToken();
                numbers1[i] = (int) in.nval;
            }
            int[] numbers2 = new int[m];
            for (int i = 0; i < m; i++) {
                in.nextToken();
                numbers2[i] = (int) in.nval;
            }
            List<Integer> interNumbers = intersection(numbers1, numbers2);
            if (interNumbers.size() == 0) {
                System.out.println("empty");
                continue;
            }
            for (int i = 0; i < interNumbers.size() - 1; i++) {
                out.append(String.valueOf(interNumbers.get(i))).append(" ");
            }
            out.append(String.valueOf(interNumbers.get(interNumbers.size() - 1))).append("\n");
            out.flush();
        }
    }

    private static List<Integer> intersection(int[] numbers1, int[] numbers2) {
        List<Integer> res = new ArrayList<>();
        int i = 0;
        int j = 0;
        while (i < numbers1.length && j < numbers2.length) {
            if (numbers1[i] < (numbers2[j])) {
                i++;
                continue;
            }
            if (numbers1[i] > (numbers2[j])) {
                j++;
                continue;
            }
            if (res.size() == 0 || !res.get(res.size() - 1).equals(numbers1[i])) {
                res.add(numbers1[i]);
            }
            i++;
            j++;
        }
        return res;
    }
}

