package 树状数组;

import java.util.Arrays;

public class BinaryIndexTree {
    static int[] c;
    int n;

    public BinaryIndexTree(int n) {
        this.n = n;
        c = new int[n + 1];
    }

    int lowbit(int x) {
        return x & (x ^ (x - 1));
    }

    /**
     * 更新一个元素，初始数组c都是0，所以可以用这个方法初始化c，这时候增加与更新是等价的
     *
     * @param p 更新第p个元素，索引从1开始
     * @param d 增加的值，不是更新后的值
     */
    void update(int p, int d) {
        while (p <= n) {
            c[p] += d;
            p += lowbit(p);
        }
    }

    /**
     * 计算从第一个元素到第p个元素的和
     *
     * @param p
     * @return
     */
    int sum(int p) {
        int ret = 0;
        while (p > 0) {
            ret += c[p];
            p -= lowbit(p);
        }
        return ret;
    }

    /**
     * 计算从第s个元素到第e个元素的和
     *
     * @param s
     * @param e
     * @return
     */
    int sum(int s, int e) {
        if (s > n || e < 1 || s > e || s < 1 || e > n) {
            throw new IllegalArgumentException();
        }
        return sum(e) - sum(s - 1);
    }

    public static void main(String[] args) {
        int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        BinaryIndexTree bit = new BinaryIndexTree(numbers.length);
        for (int i = 0; i < numbers.length; i++) {
            bit.update(i + 1, numbers[i]);
        }
        System.out.println(Arrays.toString(c));
        System.out.println("1-6的和为：" + bit.sum(6));
        //第三个元素 +4后  
        bit.update(3, 4);
        System.out.println("第三个元素 +4后:" + Arrays.toString(c));
        System.out.println("第三个元素 +4后.2-6的和为：" + bit.sum(2, 6));
    }
}  