public class Solution {
    public static void main(String[] args) {
        System.out.println(new Solution().replaceSpace(new StringBuffer("we are happy")));
    }

    public String replaceSpace(StringBuffer str) {
        int spaceNum = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ' ') {
                spaceNum++;
            }
        }
        int len = str.length();
        str.setLength(len + spaceNum * 2);
        int index = len - 1;
        int pos = str.length() - 1;
        for (; index >= 0; index--) {
            if (str.charAt(index) == ' ') {
                str.setCharAt(pos--, '0');
                str.setCharAt(pos--, '2');
                str.setCharAt(pos--, '%');
            } else {
                str.setCharAt(pos--, str.charAt(index));
            }
        }
        return str.toString();
    }
}