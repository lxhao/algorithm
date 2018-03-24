
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;


public class Kruskal {

    class UnionSet {
        int n;
        int[] members;

        UnionSet(int n) {
            this.n = n;
            this.members = new int[n + 1];
            for (int i = 1; i <= n; i++) {
                members[i] = i;
            }
        }

        void union(int e1, int e2) {
            int e1Root = find(e1);
            int e2Root = find(e2);
            members[e1Root] = e2Root;

        }

        int find(int e) {
            int root = e;
            while (members[root] != root) {
                root = members[root];
            }

            // union find with path compression
            while (members[e] != root) {
                members[e] = root;
                e = members[e];
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
        UnionSet unionSet = new UnionSet(verticesNum);
        long ans = 0;
        while (!edges.isEmpty()) {
            Edge next = edges.poll();
            if (unionSet.isConnected(next.from, next.to)) {
                continue;
            }
            ans += next.cost;
            unionSet.union(next.from, next.to);
        }
        return ans;
    }

}
