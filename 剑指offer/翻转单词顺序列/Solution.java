package 翻转单词顺序列;

import java.util.ArrayList;
import java.util.List;

/**
 * 题目描述
 * 牛客最近来了一个新员工Fish，每天早晨总是会拿着一本英文杂志，写些句子在本子上。
 * 同事Cat对Fish写的内容颇感兴趣，有一天他向Fish借来翻看，但却读不懂它的意思。
 * 例如，“student. a am I”。后来才意识到，这家伙原来把句子单词的顺序翻转了，
 * 正确的句子应该是“I am a student.”。Cat对一一的翻转这些单词顺序可不在行，你能帮助他么？
 * <p>
 * 用api确实可以很简洁的解决问题,真正考试再考虑api吧
 * 每个单词逆置,再逆置整个句子
 */
public class Solution {
    public static void main(String[] args) {
        System.out.println(new Solution().ReverseSentence("hello i am"));
    }

    public String ReverseSentence(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        char[] arr = str.toCharArray();
        int wordStartPos;
        for (int i = 0; i < arr.length; i++) {
            //拆分出单词,并把单词逆置
            if (arr[i] != ' ') {
                wordStartPos = i;
                while (i < arr.length && arr[i] != ' ') {
                    i++;
                }
                reverse(arr, wordStartPos, i - 1);
            }
        }
        //把整个数组逆置
        reverse(arr, 0, arr.length - 1);
        return new String(arr);
    }

    private void reverse(char[] arr, int start, int end) {
        for (int i = start; i <= (start + end) / 2; i++) {
            swap(arr, i, end + start - i);
        }
    }

    private void swap(char[] arr, int p1, int p2) {
        char tmp = arr[p1];
        arr[p1] = arr[p2];
        arr[p2] = tmp;
    }
}
