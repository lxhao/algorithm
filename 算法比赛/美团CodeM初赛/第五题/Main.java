package 美团CodeM初赛.第五题;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class Main {


    static class Kruskal {

        class UnionFind {
            int n;
            int[] parent;

            UnionFind(int n) {
                this.n = n;
                this.parent = new int[n + 1];
                for (int i = 1; i <= n; i++) {
                    parent[i] = i;
                }
            }

            void union(int e1, int e2) {
                int e1Root = find(e1);
                int e2Root = find(e2);
                parent[e1Root] = e2Root;

            }

            int find(int e) {
                int root = e;
                while (parent[root] != root) {
                    root = parent[root];
                }

                // union find with path compression
                while (parent[e] != root) {
                    parent[e] = root;
                    e = parent[e];
                }
                return root;
            }

            boolean isConnected(int e1, int e2) {
                int e1Root = find(e1);
                int e2Root = find(e2);
                return e1Root == e2Root;
            }
        }

        static class Edge {
            int from;
            int to;
            int cost;

            Edge(int from, int to, int cost) {
                this.from = from;
                this.to = to;
                this.cost = cost;
            }
        }

        public static void main(String[] args) {
            try {
                System.setIn(new FileInputStream("input.txt"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            Scanner in = new Scanner(System.in);
            int verticesNum = in.nextInt();
            int edgesNum = in.nextInt();
            PriorityQueue<Edge> edges = new PriorityQueue<>(Comparator.comparingInt(o -> o.cost));
            // read all directed edges.
            // It is assumed that the input data is in the correct format.
            for (int i = 0; i < edgesNum; i++) {
                int from = in.nextInt();
                int to = in.nextInt();
                int cost = in.nextInt();
                edges.add(new Edge(from, to, cost));
            }

            Kruskal kruskal = new Kruskal();
            System.out.println(kruskal.minTree(verticesNum, edges));
            in.close();
        }

        private long minTree(int verticesNum, PriorityQueue<Edge> edges) {
            UnionFind unionFind = new UnionFind(verticesNum);
            long ans = 0;
            while (!edges.isEmpty()) {
                Edge next = edges.poll();
                if (unionFind.isConnected(next.from, next.to)) {
                    continue;
                }
                ans += next.cost;
                unionFind.union(next.from, next.to);
            }
            return ans;
        }

    }
}
