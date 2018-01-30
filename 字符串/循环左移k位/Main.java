package 循环左移k位;

import java.util.*;

//把字符串循环左移k位
public class Main {
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        System.out.println("请输入要移位的字符串和移动距离（用空格隔开）");
        while (in.hasNext()) {
            String str = in.next();
            char[] charArr = str.toCharArray();
            char tmp = charArr[0];
            int k = in.nextInt();
            k = k % charArr.length;

            //三次反转之后就实现了字符串右移
            reverse(charArr, 0, k - 1);
            reverse(charArr, k, charArr.length - 1);
            reverse(charArr, 0, charArr.length - 1);

            System.out.println(new String(charArr));
            System.out.println("");
            System.out.println("请输入要移位的字符串和移动距离（用空格隔开）");
        }
    }

    public static void swap(char[] charArr, int pos1, int pos2) {
        if (charArr == null || pos1 < 0 || pos2 < 0 || pos1 >= charArr.length || pos2 >= charArr.length) {
            return;
        }

        char tmp = charArr[pos1];
        charArr[pos1] = charArr[pos2];
        charArr[pos2] = tmp;
    }

    //反转数组
    public static void reverse(char[] charArr, int startPos, int endPos) {
        if(charArr == null || startPos < 0 || endPos < 0 || startPos > charArr.length || endPos > charArr.length) {
            return;
        }

        for(; startPos < endPos; startPos++, endPos--) {
            swap(charArr, startPos, endPos);
        }
    }
}
