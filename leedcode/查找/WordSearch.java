import java.util.*;
public class WordSearch {
    public static void main(String arga[]) {
        char[][] board = {
            {'A', 'B', 'C', 'E'},
            {'S', 'F', 'E', 'S'},
            {'A', 'D', 'E', 'E'},
        };

        String word = "ABCESEEEFS";

        boolean res = new WordSearch().exist(board, word);
        System.out.println(res);
    }

    public boolean exist(char[][] board, String word) {
        if(board == null || word == null) {
            return false;
        }
        boolean flag[][] = new boolean[board.length][board[0].length];
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[0].length; j++) {
                if(board[i][j] == word.charAt(0)) {
                   if(search(board, word, 0, flag, i, j)) {
                       return true;
                   }
                   //每次搜索后把flag复位
                   for(int k = 0; k < flag.length; k++) {
                       Arrays.fill(flag[k], false);
                   }
                }
            }
        }

        return false;
    }

    private boolean search(char[][] board, String word, int nowPos, boolean[][] flag, int x, int y) {
        //比较完了全部的单词，由于只有当前位置匹配成功才继续进行下一步匹配
        //所以，当前位置等于单词的长度则匹配成功
        if(nowPos == word.length()) {
            return true;
        }
        if(x < 0 || y < 0 || x >= board.length || y >= board[0].length) {
            return false;
        }


        if(flag[x][y] || board[x][y] != word.charAt(nowPos)) {
            return false;
        }
        //当前字符相等，上下左右继续搜索
         flag[x][y] = true;
         boolean res =  search(board, word, nowPos + 1, flag, x + 1, y) ||
                 search(board, word, nowPos + 1, flag, x - 1, y) ||
                 search(board, word, nowPos + 1, flag, x, y - 1) ||
                 search(board, word, nowPos + 1, flag, x, y + 1);
         //如果当前字符相等，但没有成功匹配，不能标记当前位置已经走过
         //测试例子中的“SE"能说明这个问题
        if(!res) {
            flag[x][y] = false;
        }
        return res;

    }
}
