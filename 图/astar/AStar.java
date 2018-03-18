package astar;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;


public class AStar {
    class Vertex {
        double x;
        double y;
        double z;
        List<Integer> adjacentNodes;

        double g;
        double h;

        Vertex(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
            adjacentNodes = new ArrayList<>();
        }

        double getF() {
            return g + h;
        }

    }

    public static void main(String[] args) {
        try {
            System.setIn(new FileInputStream("input.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scanner in = new Scanner(System.in);
        AStar aStar = new AStar();

        Vertex[] vertices = aStar.readGragh(in);
//        aStar.printGraphInfo(vertices);
        in.close();
        double ans = aStar.astarSearch(1, vertices.length - 1, vertices);
        System.out.println(ans);
    }

    private double astarSearch(int from, int to, Vertex[] vertices) {
        //we use priority queue to implement open set in order to reduce the complexity of finding the element which has smallest f value .
        PriorityQueue<Vertex> openSet = new PriorityQueue<>(Comparator.comparingDouble(Vertex::getF));
        Set<Vertex> closedSet = new HashSet<>();
        openSet.add(vertices[from]);

        while (!closedSet.contains(vertices[to])) {
            Vertex current = openSet.poll();
            closedSet.add(current);
            for (int vertex : current.adjacentNodes) {
                Vertex candinadateNode = vertices[vertex];
                // already in close set
                if (closedSet.contains(candinadateNode)) {
                    continue;
                }

                double g = current.g + getDis(current, candinadateNode);
                if (openSet.contains(candinadateNode) && g > candinadateNode.g) {
                    continue;
                }
                candinadateNode.g = g;
                candinadateNode.h = getDis(candinadateNode, vertices[to]);
                openSet.offer(candinadateNode);
            }
        }
        return vertices[to].g;
    }

    private void printGraphInfo(Vertex[] vertices) {
        System.out.println("graph info:");
        for (int i = 1; i < vertices.length; i++) {
            System.out.printf("vertex %d, position is (%d,%d,%d), adjacent nodes is %s\n",
                    i,
                    (int) vertices[i].x,
                    (int) vertices[i].y,
                    (int) vertices[i].z,
                    vertices[i].adjacentNodes);
        }
    }

    /**
     * read gragh info.
     * It is assumed that the input data is in the correct format
     *
     * @param in
     * @return
     */
    private Vertex[] readGragh(Scanner in) {
        int verticesNum = in.nextInt();
        int edgesNum = in.nextInt();
        // read vertices
        Vertex[] vertices = new Vertex[verticesNum + 1];
        for (int i = 1; i <= verticesNum; i++) {
            int x = in.nextInt();
            int y = in.nextInt();
            int z = in.nextInt();
            vertices[i] = new Vertex(x, y, z);
        }

        // read all directed edges
        for (int i = 0; i < edgesNum; i++) {
            int vertex1 = in.nextInt();
            int vertex2 = in.nextInt();
            vertices[vertex1].adjacentNodes.add(vertex2);
        }
        return vertices;
    }

    private double getDis(Vertex v1, Vertex v2) {
        double dis = (v1.x - v2.x) * (v1.x - v2.x);
        dis += (v1.y - v2.y) * (v1.y - v2.y);
        dis += (v1.z - v2.z) * (v1.z - v2.z);
        if (Double.isNaN(Math.sqrt(dis))) {
            return 0.0;
        }
        return Math.sqrt(dis);
    }
}
