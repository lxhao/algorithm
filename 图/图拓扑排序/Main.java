package 图拓扑排序;

import java.util.*;

public class Main {
    public static void main(String args[]) {

    }

    public static Queue<Integer> getTopo(int[][] graph) {
        if (graph == null || graph.length == 0 || graph.length != graph[0].length) {
            return null;
        }
        //保存入度不为0的节点
        Set<Integer> rest = new HashSet<>();
        //结果
        Queue<Integer> topoSort = new LinkedList<>();
        //计算入度
        int[] inDegree = new int[graph.length];
        for (int i = 0; i < graph.length; i++) {
            rest.add(i);
            for (int j = 0; j < graph.length; j++) {
                if (graph[j][i] > 0) {
                    inDegree[i] += 1;
                }
            }
        }

        while (!rest.isEmpty()) {
            for (int i = 0; i < rest.size(); i++) {
                if (inDegree[i] == 0) {
                    rest.remove(i);
                    topoSort.offer(i);
                    for (Integer e : rest) {
                        if (graph[i][e] > 0) {
                            inDegree[e]--;
                        }
                    }
                }
            }
        }
        return topoSort;
    }
}

