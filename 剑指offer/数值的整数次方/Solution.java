package 数值的整数次方;

public class Solution {
    public static void main(String[] args) {
        System.out.println(new Solution().Power(0, 1));
        System.out.println(new Solution().Power(2, -1));
        System.out.println(new Solution().Power(2, 1));
        System.out.println(new Solution().Power(2, 10));
    }

    /**
     * 注意负数和指数为0的情况,这里因为返回结果是double类型,已经限制了数值范围,
     * 如果是面试,记得跟面试官确认结果是否需要用大数表示
     *
     * @param base
     * @param exponent
     * @return
     */
    public double Power(double base, int exponent) {

        //底数为0判断
        if (Double.compare(base, 0) == 0) {
            return 0;
        }

        //负数判断
        boolean negative = false;
        if (exponent < 0) {
            exponent = exponent * -1;
            negative = true;
        }

        double result = computePower(base, exponent);
        if (negative) {
            result = 1 / result;
        }
        return result;
    }

    /**
     * 递归计算指数
     *
     * @param base
     * @param exponent
     * @return
     */
    private double computePower(double base, int exponent) {
        if (exponent == 0) {
            return 1;
        }
        if (exponent == 1) {
            return base;
        }
        if ((exponent & 1) == 0) {
            return computePower(base, exponent / 2) * computePower(base, exponent / 2);
        } else {
            return computePower(base, 1 + exponent / 2) * computePower(base, exponent / 2);
        }
    }

}