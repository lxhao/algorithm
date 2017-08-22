package 数组中只出现一次的数字;

import java.util.ArrayList;
import java.util.List;


/**
 * 题目描述
 * 一个整型数组里除了两个数字之外，其他的数字都出现了两次。请写程序找出这两个只出现一次的数字。
 * <p>
 * 两个相同的数异或后为0,如果数组中只有一个数出现的次数是奇数次,数组中所有元素异或后剩下的就是出现奇数次的数字,
 * 这个题目中有两个数字出现了奇数次,所以要想办法先把数据分成两部分,使每部分包含一个出现奇数次的数字,
 * 所有数字异或以后得到t,根据异或的结果1所在的最低位,把数组的数据分成两部分,
 * 这里所有数字以后的结果其实就是两个奇数次数字异或的结果,所以根据二进制的1可以把数据分成两部分
 */
public class Solution {

    public static void main(String[] args) {
        int[] array = {1, 2, 2, 3};
        int[] n1 = new int[1];
        int[] n2 = new int[1];
        new Solution().FindNumsAppearOnce(array, n1, n2);
        System.out.println(n1[0]);
        System.out.println(n2[0]);
    }

    //num1,num2分别为长度为1的数组。传出参数
    //将num1[0],num2[0]设置为返回结果
    public void FindNumsAppearOnce(int[] array, int num1[], int num2[]) {
        int sumOr = 0;
        for (int e : array) {
            sumOr ^= e;
        }
        //找到第一个为1的二进制位
        int shift = 1;
        while ((shift & sumOr) != shift) {
            shift = shift << 1;
        }

        //根据shift把数据分成两部分
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        for (int e : array) {
            if ((e & shift) == shift) {
                list1.add(e);
            } else {
                list2.add(e);
            }
        }

        for (int e : list1) {
            num1[0] ^= e;
        }
        for (int e : list2) {
            num2[0] ^= e;
        }
    }
}