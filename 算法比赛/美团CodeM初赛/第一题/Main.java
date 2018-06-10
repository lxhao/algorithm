package 美团CodeM初赛.第一题;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    static class Position {
        int x;
        int y;

        Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private static int getDis(Position p1, Position p2) {
        return Math.abs(p1.x - p2.x) + Math.abs(p2.y - p1.y);
    }

    private static Position getPosition(char c) {
        int x, y;
        if (c <= 'F') {
            x = 1;
            y = ((c - 'A' + 3) % 9);
            y = y / 3 + 1;
        } else if (c <= 'O') {
            x = 2;
            y = ((c - 'G') % 9);
            y = y / 3 + 1;
        } else if (c <= 'S') {
            x = 3;
            y = 1;
        } else if (c <= 'V') {
            x = 3;
            y = 2;
        } else {
            x = 3;
            y = 3;
        }
        return new Position(x, y);
    }

    public static void main(String[] args) {
        try {
            System.setIn(new FileInputStream("input.txt"));
        } catch (FileNotFoundException ignored) {
        }
        Scanner in = new Scanner(System.in);
        int T = in.nextInt();
        for (int i = 0; i < T; i++) {
            String fileName = in.next();
            Position curPos = new Position(1, 1);
            int ans = 0;
            for (char c : fileName.toCharArray()) {
                Position nextPos = getPosition(c);
                ans += getDis(curPos, nextPos);
                curPos = nextPos;
            }
            System.out.println(ans);
        }
    }
}
