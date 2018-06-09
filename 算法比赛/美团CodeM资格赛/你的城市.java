package 美团CodeM资格赛;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class 你的城市 {
    public static void main(String[] args) {
        try {
            System.setIn(new FileInputStream("input.txt"));
        } catch (FileNotFoundException ignored) {
        }
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            int n = in.nextInt();
            int m = in.nextInt();
            Graph graph = new Graph(n);
            for (int i = 0; i < m; i++) {
                int x = in.nextInt();
                int y = in.nextInt();
                int c = in.nextInt();
                String ts = in.next();
                String td = in.next();
                Graph.Edge edge = new Graph.Edge();
                edge.city = y;
                edge.cost = c;
                edge.ts = Integer.valueOf(ts.split(":")[0]) * 60 + Integer.valueOf(ts.split(":")[1]);
                edge.td = Integer.valueOf(td.split(":")[0]) * 60 + Integer.valueOf(td.split(":")[1]);
                graph.getVertex(x).addEdge(edge);
            }
//            assert (graph.dfs(0, graph.getVertex(1), -30) == in.nextInt());
            int rightAns = in.nextInt();
            int myAns = graph.dfs(0, graph.getVertex(1), -30);
            System.out.printf("right ans %d, my ans %d\n", rightAns, myAns == Integer.MAX_VALUE ? -1 : myAns);
            System.out.println(graph.dijkstra(1, 3));
        }
    }
}

class Graph {
    Vertex[] vertices;

    Graph(int n) {
        vertices = new Vertex[n];
        for (int i = 0; i < n; i++) {
            vertices[i] = new Vertex();
        }
    }

    Vertex getVertex(int n) {
        return vertices[n - 1];
    }

    static class Vertex {

        // 如果时间比当前点早，且cost比当前点小，更新两个值
        // 如果后面的路径到当前点时发现，到达的最早时间比已有时间晚，且cost比已有cost大，放弃继续搜索
        int costRch = Integer.MAX_VALUE;
        int lastTimeRch = 25 * 60;

        List<Edge> edges = new ArrayList<>();

        void addEdge(Edge edge) {
            edges.add(edge);
        }
    }


    static class Edge {
        int city;
        int cost;
        int ts;
        int td;
    }

    static class VertexDis implements Comparable<VertexDis> {
        int city;
        int dis;

        VertexDis(int city, int dis) {
            this.city = city;
            this.dis = dis;
        }

        @Override
        public int compareTo(VertexDis o) {
            return this.dis - o.dis;
        }
    }

    class MyPriorityQueue extends PriorityQueue<VertexDis> {
        private HashMap<Integer, VertexDis> hashMap = new HashMap<>();

        public boolean add(VertexDis vertexDis) {
            hashMap.put(vertexDis.city, vertexDis);
            return super.add(vertexDis);
        }

        VertexDis getVertexDisByCity(int ciyt) {
            return hashMap.get(ciyt);
        }
    }

    int dijkstra(int start, int des) {
        Vertex startVertex = vertices[start - 1];
        Vertex desVertex = vertices[des - 1];
        MyPriorityQueue priorityQueue = new MyPriorityQueue();
        for (Edge edge : startVertex.edges) {
            priorityQueue.add(new VertexDis(edge.city, edge.cost));
        }
        Set<Vertex> setReached = new HashSet<>();
        setReached.add(startVertex);
        while (!setReached.contains(desVertex)) {
            VertexDis minVertex = priorityQueue.poll();
            Vertex nextVertex = vertices[minVertex.city - 1];
            setReached.add(nextVertex);
            for (Edge edge : nextVertex.edges) {
                if (priorityQueue.getVertexDisByCity(edge.city) != null) {
                    if (priorityQueue.getVertexDisByCity(edge.city).dis > minVertex.dis + edge.cost) {
                        priorityQueue.getVertexDisByCity(edge.city).dis = minVertex.dis + edge.cost;
                    }
                } else {
                    priorityQueue.add(new VertexDis(edge.city, edge.cost + minVertex.dis));
                }
            }
        }
        return priorityQueue.getVertexDisByCity(des).dis;

    }

    int dfs(int cost, Vertex curVertex, int maxTd) {
        Collections.sort(curVertex.edges, (o1, o2) -> {
            if (o1.city == o2.city) {
                return o1.ts - o2.ts;
            } else {
                return o1.city - o2.city;
            }

        });
        int size = curVertex.edges.size();
        int minCost = Integer.MAX_VALUE;
        for (int i = 0; i < size; i++) {
            Edge edge = curVertex.edges.get(i);
            // 中转要30分钟
            if (edge.ts - maxTd < 30) {
                continue;
            }
            int backupEdgePos = i;
            Edge backupEdge = curVertex.edges.get(backupEdgePos);
            // 备份路径改签要30分钟
            int backupTd = Integer.MAX_VALUE;
            while (backupEdgePos + 1 < size && backupEdge.city == edge.city) {
                if (backupEdge.ts - edge.ts >= 30) {
                    backupTd = backupEdge.td;
                }
                backupEdgePos++;
            }
            if (backupTd != Integer.MAX_VALUE) {
                if (edge.city == vertices.length) {
                    minCost = Integer.min(minCost, cost + edge.cost);
                    continue;
                }
                minCost = Integer.min(minCost, dfs(cost + edge.cost, vertices[edge.city - 1], Integer.max(backupTd, edge.td)));
            }
        }
        return minCost;
    }
}
