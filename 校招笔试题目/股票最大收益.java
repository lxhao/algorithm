/**
 * 在股市的交易日中，假设最多可进行两次买卖(即买和卖的次数均小于等于2)，规则是必须一笔成交后进行另一笔
 * (即买-卖-买-卖的顺序进行)。给出一天中的股票变化序列，请写一个程序计算一天可以获得的最大收益。
 * 请采用实践复杂度低的方法实现。给定价格序列prices及它的长度n，请返回最大收益。保证长度小于等于500。
 * 测试样例：
 * [10,22,5,75,65,80],6
 */
public class 股票最大收益 {
    public static void main(String[] args) {
        int[] prices = {10, 22, 5, 75, 65, 80};
        股票最大收益 stock = new 股票最大收益();
        System.out.println(stock.maxProfit(prices, prices.length));
    }

    public int maxProfit(int[] prices, int n) {
        return getMostProfit(prices, 2);
    }

    /**
     * @Param 股票价格
     * @Param 最大交易次数
     * @Return 最大收益
     */
    public static int getMostProfit(int[] prices, int times) {
        int[][] mostProfit = new int[times + 1][prices.length];
        for (int i = 1; i <= times; i++) {
            int max = mostProfit[i - 1][0] - prices[0];
            for (int j = 1; j < prices.length; j++) {
                mostProfit[i][j] = Math.max(mostProfit[i][j - 1], max + prices[j]);
                max = Math.max(max, mostProfit[i - 1][j] - prices[j]);
            }
        }
        return mostProfit[times][prices.length - 1];
    }
}