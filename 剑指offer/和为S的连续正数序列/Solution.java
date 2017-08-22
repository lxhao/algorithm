package 和为S的连续正数序列;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 题目描述
 * 小明很喜欢数学,有一天他在做数学作业时,要求计算出9~16的和,他马上就写出了正确答案是100。
 * 但是他并不满足于此,他在想究竟有多少种连续的正数序列的和为100(至少包括两个数)。没多久,
 * 他就得到另一组连续正数和为100的序列:18,19,20,21,22。现在把问题交给你,
 * 你能不能也很快的找出所有和为S的连续正数序列? Good Luck!
 * <p>
 * 输出描述:
 * 输出所有和为S的连续正数序列。序列内按照从小至大的顺序，序列间按照开始数字从小到大的顺序
 * <p>
 * 用等差数列的公式求解
 */
public class Solution {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.FindContinuousSequence(100));
    }

    public ArrayList<ArrayList<Integer>> FindContinuousSequence(int sum) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        //i代表有几个连续正数,如果存在i个连续正数的和为sum,只可能有一种情况
        for (int i = 2; i < sum; i++) {
            //根据等差数列求和公式得到第一项和最后一项的和,因为公差为1,最后一项减去第一项的和为n-1
            //可以得到最后一项的值,再把第一项到最后一项依次加起来验证是否正确
            float a1Plusan = sum * 2 / i * 1.0F;
            int an = (int) ((a1Plusan + i - 1) / 2);
            ArrayList<Integer> arrayList = new ArrayList<>();
            int sumTmp = 0;
            int a1 = an - i + 1;
            for (int j = a1; j <= an; j++) {
                arrayList.add(j);
                sumTmp += j;
            }
            //第一项出现负数或0的时候终止
            if (arrayList.get(0) <= 0) {
                break;
            }
            if (sumTmp == sum) {
                res.add(arrayList);
            }
        }
        Collections.reverse(res);
        return res;
    }
}