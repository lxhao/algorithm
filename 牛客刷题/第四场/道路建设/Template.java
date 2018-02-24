package 第四场.道路建设;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Template {
    public static void main(String[] args) {
        try {
            System.setIn(new FileInputStream("input.txt"));
        } catch (FileNotFoundException e) {
        }
        Scanner in = new Scanner(System.in);
        int cost = in.nextInt();
        int edges = in.nextInt();
        int vertex = in.nextInt();
        int[][] gragh = new int[vertex][vertex];
        for (int i = 0; i < edges; i++) {
            int v1 = in.nextInt();
            int v2 = in.nextInt();
            gragh[v1 - 1][v2 - 1] = in.nextInt();
        }
        int minCost = minTree(gragh);
        System.out.println(minCost <= cost ? "Yes" : "No");
        in.close();
    }

    private static int minTree(int[][] gragh) {
        return 0;
    }
}

