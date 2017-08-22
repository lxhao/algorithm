package 替换空格;

/**
 * 请实现一个函数，将一个字符串中的空格替换成“%20”。例如，当字符串为We Are Happy.
 * 则经过替换之后的字符串为We%20Are%20Happy。
 */
public class Solution {
    public static void main(String[] args) {
        String res = new Solution().replaceSpace(new StringBuffer("We Are Happy"));
        System.out.println(res);
    }


    /**
     * 空格替换有可能引起字符长度增长,题目并没有说原来的str后面有足够多的内存来保存,
     * 如果后面有足够多的空间,从后往前替换是很好的解决办法,但如果后面没有空间
     * 必须自己重新申请一块空间时,直接从前往后替换就是最好的做法了,用StringBuilder保存新的
     * 字符串时,避免append扩容,应该提前计算出容量,在创建对象的时候就制定内部char数组的大小.
     *
     * @param str
     * @return
     */
    public String replaceSpace(StringBuffer str) {
        //计算出空格数量,得到替换空格后字符串的长度
        //在创建StringBuilder的时候就指定数组大小,避免后面append的时候数组扩容
        int spaceCounter = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ' ') {
                spaceCounter++;
            }
        }
        if (spaceCounter == 0) {
            return str.toString();
        }
        //从前往后遍历,遇到空格则替换
        StringBuilder stringBuilder = new StringBuilder(str.length() + spaceCounter * 2);
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ' ') {
                stringBuilder.append("%20");
            } else {
                stringBuilder.append(str.charAt(i));
            }
        }
        return stringBuilder.toString();
    }
}