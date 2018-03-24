package Wannafly12.T95要减肥;

import java.io.*;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        try {
            System.setIn(new FileInputStream("input.txt"));
        } catch (FileNotFoundException ignored) {
        }
        StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
        while (in.nextToken() != StreamTokenizer.TT_EOF) {
            int n = (int) in.nval;
            in.nextToken();
            int m = (int) in.nval;
            int[] happiness = new int[n];
            int[] pain = new int[n];

            for (int i = 0; i < n; i++) {
                in.nextToken();
                pain[i] = (int) in.nval;
            }

            for (int i = 0; i < n; i++) {
                in.nextToken();
                happiness[i] = (int) in.nval;
            }

            System.out.println(solve(happiness, pain, m));

        }
    }

    private static long solve(int[] happiness, int[] pain, int m) {
        long ans = 0;
        Arrays.sort(happiness);
        Arrays.sort(pain);
        int len = happiness.length;
        for (int i = 0; i < len; i++) {
            if (happiness[len - i - 1] >= pain[i]) {
                ans += happiness[len - i - 1] - pain[i];
                if ((i + 1) % 3 == 0) {
                    ans += m;
                }
                continue;
            }

            long incre = happiness[len - i - 1] - pain[i];
            while ((i + 1) % 3 != 0 && i < len - 1) {
                i++;
                incre += happiness[len - i - 1] - pain[i];
            }
            if ((i + 1) % 3 == 0) {
                incre += m;
            }
            if (incre <= 0) {
                break;
            }
            ans += incre;
        }
        return ans;
    }
}

