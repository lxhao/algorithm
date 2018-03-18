import java.io.*;
import java.util.*;

public class Main {
    static class Node {
        int vPos;
        int weight;

        Node(int vPos, int weight) {
            this.vPos = vPos;
            this.weight = weight;
        }

    }

    public static void main(String args[]) throws IOException {
        double x  = -910441, y = -510493, z = 230370;
        double x1 = -370033, y1 = 433118, z1 = -249415;
        double dis = (x - x1) * (x - x1);
        dis += (y - y1) * (y - y1);
        dis += (z - z1) * (z - z1);
        System.out.println(Math.sqrt(dis));

    }
}


