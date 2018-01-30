package 顶点覆盖;

import java.util.*;

public class MinVertexCover {
    private int V;
    private LinkedList<Integer> adj[];
    private Map<Integer, Integer> wt = new HashMap<Integer, Integer>();
    private int W;

    //构造函数
    MinVertexCover(int v) {
        V = v;
        adj = new LinkedList[v];

        for (int i = 0; i < v; ++i)
            adj[i] = new LinkedList();
    }

    void addWeight(int v, int w) {
        wt.put(v, w);
    }

    public int getWeight(int value) {
        int weight = wt.get(value);
        return weight;
    }

    void addEdge(int v, int w) {
        adj[v].add(w);
        adj[w].add(v);
    }

    //取图中的所有，边返回一个list
    public List getEdge() {
        List<Integer> list1 = new ArrayList<Integer>();
        List<List<Integer>> outList = new ArrayList<List<Integer>>();
        for (int i = 0; i < V; i++) {
            for (Integer substr : adj[i]) {
                if (!list1.contains(substr)) {
                    List<Integer> list2 = new ArrayList<Integer>();
                    list2.add(i);
                    list2.add(substr);

                    outList.add(list2);
                }
                list1.add(i);

            }

        }
        return outList;
    }

    //实现最小Cost的覆盖近似算法
    public List minVexterCover(MinVertexCover g) {
        List everyEdge = g.getEdge();
        Map weight = wt;
        List<Integer> outlist = new ArrayList<Integer>();

        for (int i = 0; i < everyEdge.size(); i++) {

            int min;
            int a = ((List<Integer>) everyEdge.get(i)).get(0);
            int wa = wt.get(a);
            int b = ((List<Integer>) everyEdge.get(i)).get(1);
            int wb = wt.get(b);
            if (wa > wb) {
                min = wb;
            } else {
                min = wa;
            }
            wa -= min;
            wb -= min;
            weight.put(a, wa);
            weight.put(b, wb);
        }
        Set<Integer> set = weight.keySet();
        for (int i : set) {
            int value = (int) weight.get(i);
            if (value == 0) {
                outlist.add(i);
            }
        }

        return outlist;

    }

    public static void main(String args[]) {
        MinVertexCover g = new MinVertexCover(6);
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(0, 3);
        g.addEdge(0, 4);
        g.addEdge(0, 5);
        g.addWeight(0, 100);
        g.addWeight(1, 1);
        g.addWeight(2, 1);
        g.addWeight(3, 1);
        g.addWeight(4, 1);
        g.addWeight(5, 0);
        List minCover = g.minVexterCover(g);
        System.out.println(minCover);
    }
}