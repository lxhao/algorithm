import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

public class 单调栈求最优区间 {
    public static void main(String[] args) {
//        Scanner in = getScanner(System.in);
        Scanner in = getScanner("input.txt");
        while (in.hasNext()) {
            int n = in.nextInt();
            int[] numbers = new int[n];
            for (int i = 0; i < n; i++) {
                numbers[i] = in.nextInt();
            }
            System.out.println(solver(numbers));
        }
        in.close();
    }

    //栈元素
    static class Node {
        int val;
        int leftIndex;

        Node(int val, int leftIndex) {
            this.val = val;
            this.leftIndex = leftIndex;
        }
    }

    /**
     * 参考链接:http://blog.csdn.net/hopeztm/article/details/7868581
     * https://zhuanlan.zhihu.com/p/26465701
     * <p>
     * 维持一个从前往后递增的单调栈,把每个元素入栈,如果当前元素大于栈顶,直接入栈,
     * 否则出栈,直到当前元素大于栈顶,再把当前元素入栈,这里对于出栈的每个元素计算以它
     * 为最小值能扩展的最大区间.
     * <p>
     * 既然要计算最大区间,我们还需要保存一个索引,具体而言就是每个元素在区间内做为最小元素往左能扩展的最大位置,
     * 这个位置怎么记录呢,对于直接能入栈的元素,它的索引就是他的位置,因为他前面的值都小于它,
     * 对于不能直接入栈的元素,入栈前需要移除前面所有大于它的元素,那他的索引就是最后一个大于它的索引.
     * <p>
     * 这样,在每个元素出栈时就能计算以它为最小值能扩展的最大区间,往左扩展的最大限度为为它保存的索引,
     * 往右扩展为当前栈顶的索引,因为出栈的条件是下一个要入栈的元素小于栈顶元素,也小于当前出栈的元素.
     *
     * @param numbers
     * @return
     */
    private static int solver(int[] numbers) {
        Stack<Node> stack = new Stack<>();
        //先放一个val为负数的节点,避免循环时需要判空
        stack.push(new Node(-1, 0));
        int maxVal = Integer.MIN_VALUE;
        for (int i = 0; i <= numbers.length; i++) {
            //到达最后一个元素,把当前值设为0,让所有元素出栈并计算区间值
            int curVal = (i == numbers.length) ? 0 : numbers[i];
            Node p = new Node(curVal, i);
            while (curVal <= stack.lastElement().val) {
                p = stack.pop();
                maxVal = Math.max(maxVal, getInterValue(numbers, p.val, p.leftIndex, i - 1));
            }
            stack.push(new Node(curVal, p.leftIndex));
        }
        return maxVal;
    }

    /**
     * 获取区间值,方便代码复用,稍微换一下区间值的计算方式又是一个好题目啊
     *
     * @return
     */
    private static int getInterValue(int[] numbers, int minVal, int left, int right) {
        int sum = 0;
        for (int i = left; i <= right; i++) {
            sum += numbers[i];
        }
        return sum * minVal;
    }

    //从输入流读取输入数据
    public static Scanner getScanner(InputStream is) {
        return new Scanner(is);
    }

    //从文件读取输入数据
    public static Scanner getScanner(String fileName) {
        try {
            return getScanner(new FileInputStream(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}

