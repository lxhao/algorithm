package 相邻字符;

import java.util.Arrays;

public class Main {
    public static void main(String args[]) {
        char[][] board = {
                {'O', 'O', 'O', 'O', 'X', 'X'},
                {'O', 'O', 'O', 'O', 'O', 'O'},
                {'O', 'X', 'O', 'X', 'O', 'O'},
                {'O', 'X', 'O', 'O', 'X', 'O'},
                {'O', 'X', 'O', 'X', 'O', 'O'},
                {'O', 'X', 'O', 'O', 'O', 'O'},
        };

        new Main().solve(board);
    }

    public void solve(char[][] board) {
        if (board == null || board.length == 0) {
            return;
        }
        //行的元素数量
        int rowLen = board.length;
        //列的元素数量
        int colLen = board[0].length;
        boolean[][] index = new boolean[rowLen][colLen];
        //第一列
        for (int i = 0; i < rowLen; i++) {
            search(board, index, i, 0);
        }
        //最后一列
        for (int i = 0; i < rowLen; i++) {
            search(board, index, i, colLen - 1);
        }
        //第一行
        for (int i = 0; i < colLen; i++) {
            search(board, index, 0, i);
        }
        //最后一行
        for (int i = 0; i < colLen; i++) {
            search(board, index, rowLen - 1, i);
        }

        for (int i = 0; i < rowLen; i++) {
            for (int j = 0; j < colLen; j++) {
                board[i][j] = (board[i][j] == '*' ? 'O' : 'X');
                System.out.printf("%c ", board[i][j]);
            }
            System.out.println("");
        }
    }


    private void search(char[][] board, boolean[][] index, int x, int y) {
        if (x < 0 || y < 0 || x >= board.length || y >= board[0].length) {
            return;
        }
        if (index[x][y] || board[x][y] == 'X') {
            index[x][y] = true;
            return;
        }
        index[x][y] = true;
        board[x][y] = '*';
        search(board, index, x - 1, y);
        search(board, index, x + 1, y);
        search(board, index, x, y - 1);
        search(board, index, x, y + 1);
    }
}
