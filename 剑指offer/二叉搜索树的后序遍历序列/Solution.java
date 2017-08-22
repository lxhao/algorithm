package 二叉搜索树的后序遍历序列;

/**
 * 题目描述
 * 输入一个整数数组，判断该数组是不是某二叉搜索树的后序遍历的结果。如果是则输出Yes,否则输出No。
 * 假设输入的数组的任意两个数字都互不相同。
 * <p>
 * 搜索树的规律是右边的节点小于中间节点,右边的节点大于中间节点
 * 后续遍历的时候树最后访问的元素为中间节点,从中间节点前面第一个小于中间节点的位置开始,左边是左子树,右边是右子数
 */
public class Solution {

    public static void main(String[] args) {
        int[] sequence = {};
        System.out.println(new Solution().VerifySquenceOfBST(sequence));
    }

    public boolean VerifySquenceOfBST(int[] sequence) {
        if (sequence == null || sequence.length == 0) {
            return false;
        }
        return VerifySquenceOfBST(sequence, 0, sequence.length - 1);

    }

    public boolean VerifySquenceOfBST(int[] sequence, int startPos, int endPos) {
        if (startPos >= endPos) {
            return true;
        }
        //最后一个元素为中间节点
        int midVal = sequence[endPos];
        //找到第一个小于中间节点的点
        int firstLeftPos = startPos;
        for (int i = endPos; i >= startPos; i--) {
            if (sequence[i] < midVal) {
                firstLeftPos = i;
                break;
            }
        }

        for (int i = firstLeftPos - 1; i >= startPos; i--) {
            if (sequence[i] > sequence[endPos]) {
                return false;
            }
        }
        return VerifySquenceOfBST(sequence, startPos, firstLeftPos) &&
                VerifySquenceOfBST(sequence, firstLeftPos + 1, endPos - 1);
    }
}