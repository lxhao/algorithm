package 顶点覆盖;

import java.util.Iterator;
import java.util.LinkedList;

class Graph {
    private int V;
    private LinkedList<Integer> adj[];

    //构造函数
    Graph(int v) {
        V = v;
        adj = new LinkedList[v];

        for (int i = 0; i < v; ++i)
            adj[i] = new LinkedList();
    }

    //用邻接表来表示图
    void addEdge(int v, int w) {
        adj[v].add(w);
        adj[w].add(v);
    }

    void printVertexCover() {
        //初始化所有点都没有被遍历
        boolean visited[] = new boolean[V];
        for (int i = 0; i < V; i++)
            visited[i] = false;
        Iterator<Integer> i;
        //遍历所有的点
        for (int u = 0; u < V; u++) {
            if (!visited[u]) {
                //遍历与当前节点相链接的点
                i = adj[u].iterator();
                while (i.hasNext()) {
                    int v = i.next();
                    //对已经访问的点进行标记
                    if (!visited[v]) {
                        visited[v] = true;
                        visited[u] = true;
                        break;
                    }
                }
            }
        }
        //顶点覆盖的结果C
        for (int j = 0; j < V; j++)
            if (visited[j])
                System.out.print(j + " ");
        System.out.println();
        for (boolean v : visited)
            System.out.print(v + " ");
        System.out.println();
        //邻接表
        for (LinkedList<Integer> str : adj)
            System.out.println(str);
    }

    public static void main(String args[]) {
        Graph g = new Graph(7);
        g.addEdge(0, 1);
        g.addEdge(1, 2);
        g.addEdge(2, 3);
        g.addEdge(2, 4);
        g.addEdge(4, 5);
        g.addEdge(3, 5);
        g.addEdge(3, 4);
        g.addEdge(3, 6);

        g.printVertexCover();
    }
}