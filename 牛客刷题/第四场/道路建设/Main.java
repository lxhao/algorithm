package 第四场.道路建设;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    static class Node {
        int vPos;
        int weight;
    }

    public static void main(String[] args) {
        try {
            System.setIn(new FileInputStream("input.txt"));
        } catch (FileNotFoundException e) {
        }
        Scanner in = new Scanner(System.in);
        int cost = in.nextInt();
        int edges = in.nextInt();
        int vertex = in.nextInt();
        ArrayList[] gragh = new ArrayList[vertex + 1];
        for (int i = 1; i < gragh.length; i++) {
            gragh[i] = new ArrayList<Node>();
        }
        for (int i = 0; i < edges; i++) {
            int v1 = in.nextInt();
            int v2 = in.nextInt();
            Node node = new Node();
            node.vPos = v2;
            node.weight = in.nextInt();
            gragh[v1].add(node);
        }
        int minCost = minTree(gragh);
        System.out.println(minCost <= cost ? "Yes" : "No");
        in.close();
    }

    private static int minTree(List<Node>[] gragh) {
        int res = 0;
        Set<Integer> vertexSelected = new HashSet<>();
        Set<Integer> vertexUnselected = new HashSet<>();
        vertexSelected.add(1);
        for (int i = 2; i < gragh.length; i++) {
            vertexUnselected.add(i);
        }
        while (!vertexUnselected.isEmpty()) {
            int minVal = Integer.MAX_VALUE;
            Node minNode = null;
            for (int v : vertexSelected) {
                for(Node n : gragh[v]) {
                    if(vertexUnselected.contains(n.vPos) && n.weight < minVal) {
                        minVal = n.weight;
                        minNode = n;
                    }
                }
            }
            vertexSelected.add(minNode.vPos);
            vertexUnselected.remove(minNode.vPos);
            res += minNode.weight;
        }
        return res;
    }
}

