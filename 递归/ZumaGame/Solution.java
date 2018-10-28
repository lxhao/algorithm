package ZumaGame;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * https://leetcode.com/problems/decode-ways-ii/description/
 */
public class Solution {
    private static final int M = 1000000007;

    public static void main(String[] args) throws IOException {
        try {
            System.setIn(new FileInputStream("input.txt"));
        } catch (FileNotFoundException ignored) {
        }
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            String board = in.next();
            String hand = in.next();
            System.out.println(new Solution().findMinStep(board, hand));
        }
    }

    private String eliminate(String board, int insertPos) {
        String nextBoard = board.substring(0, insertPos) + board.substring(insertPos + 2);
        int cnt = 1;
        for (int i = 1; i < nextBoard.length(); i++) {
            if (nextBoard.charAt(i) == nextBoard.charAt(i - 1)) {
                cnt++;
            } else {
                if (cnt >= 3) {
                    nextBoard = board.substring(0, i - cnt) + board.substring(i);
                    break;
                }
                cnt = 1;
            }
        }
        if (cnt >= 3) {
            nextBoard = nextBoard.substring(0, nextBoard.length() - cnt) + nextBoard.substring(nextBoard.length());
        }
        return nextBoard;
    }

    public int findMinStep(String board, List<Character> handChars) {
        if (board.length() == 0) {
            return 0;
        }
        int res = Integer.MAX_VALUE;
        for (int i = 0; i < board.length() - 1; i++) {
            if (board.charAt(i) == board.charAt(i + 1) && handChars.contains(board.charAt(i))) {
                handChars.remove((Character) board.charAt(i));
                String nextBoard = eliminate(board, i);
                int t = findMinStep(nextBoard, handChars) + 1;
                if (t > 0) {
                    res = Math.min(res, t);
                }
                handChars.add(board.charAt(i));
            }
        }
        return res == Integer.MAX_VALUE ? -1 : res;
    }

    public int findMinStep(String board, String hand) {
        List<Character> handChars = new ArrayList<>();
        for (char c : hand.toCharArray()) {
            handChars.add(c);
        }
        return findMinStep(board, handChars);
    }
}


