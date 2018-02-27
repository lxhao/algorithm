package 第四场.通知小弟;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        try {
            System.setIn(new FileInputStream("input.txt"));
        } catch (FileNotFoundException ignored) {
        }
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            int n = in.nextInt();
            int m = in.nextInt();
            int[] notifiedHA = new int[m];
            for (int i = 0; i < m; i++) {
                notifiedHA[i] = in.nextInt();
            }

            List<Integer>[] gragh = new ArrayList[n + 1];
            for (int i = 1; i <= n; i++) {
                gragh[i] = new ArrayList<>();
            }

            for (int i = 1; i <= n; i++) {
                int childNum = in.nextInt();
                for (int j = 0; j < childNum; j++) {
                    int child = in.nextInt();
                    gragh[i].add(child);
                }
            }

            Set<Integer>[] treeMap = new Set[n + 1];
            for (int vertex : notifiedHA) {
                Set<Integer> tree = new HashSet<>();
                treeMap[vertex] = tree;
                splitTree(tree, gragh, vertex);
            }
            Set<Integer> reachedVertex = new HashSet<>();
            for (Set<Integer> e : treeMap) {
                reachedVertex.addAll(e);
            }
            if (reachedVertex.size() < n) {
                System.out.println(-1);
            }
        }
    }

    /**
     * 广度优先遍历图，给能访问到的节点加上标记
     *
     * @param gragh
     * @param curVertex
     */
    private static void splitTree(Set<Integer> tree, List<Integer>[] gragh, int curVertex) {
        if (tree.contains(curVertex)) {
            return;
        }
        tree.add(curVertex);
        for (int child : gragh[curVertex]) {
            splitTree(tree, gragh, child);
        }
    }
}

