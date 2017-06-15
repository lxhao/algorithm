import java.util.*;

public class Main {
    public static void main(String args[]) {
        List<String> res = new ArrayList<>();
        String s = "abcdefghij";
        arrange(s.toCharArray(), res, 0);
        System.out.println(res);
    }

    public static void arrange(char[] s, List<String> res, int startPos) {
        if (startPos == s.length - 1) {
            res.add(new String(s));
            return;
        }
        for (int i = startPos; i < s.length; i++) {
            //去重
            if(isDuplicated(s, i)) {
                continue;
            }
            swap(s, startPos, i);
            arrange(s, res, startPos + 1);
            swap(s, startPos, i);
        }
    }

    //前面有没有出现过该字符
    public static boolean isDuplicated(char[] s, int nowPos) {
        for(int i = nowPos + 1; i < s.length; i++) {
            if(s[i] == s[nowPos]) {
                return true;
            }
        }
        return false;
    }

    public static void swap(char[] s, int pos1, int pos2) {
        char tmp = s[pos1];
        s[pos1] = s[pos2];
        s[pos2] = tmp;
    }
}

