import java.util.*;

public class Main {
    public static void main(String args[]) {
        String str = "helloo";
        System.out.println(revertStr(str));
        str = "hello";
        System.out.println(revertStr(str));
    }

    /**
     * 得到字符串的char数组,交换位置
     */
    private static String revertStr(String str) {
        int len = str.length();
        char[] chars = new char[len];
        str.getChars(0, len, chars, 0);
        char temp;
        int flag = len / 2;
        for(int i = 0; i <= flag; i++) {
            temp = chars[i];
            chars[i] = chars[len -  i - 1];
            chars[len - i - 1] = temp;
        }
        return new String(chars);
    }
}

