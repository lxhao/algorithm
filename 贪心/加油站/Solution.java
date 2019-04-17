package 加油站;

public class Solution {
    public int canCompleteCircuit(int[] gas, int[] cost) {
        // 总缺油量
        int totalLackGas = 0;
        // gas[startPos] > cost[startPos]
        int startPos = 0;
        // 从statPos出发剩余油量
        int restGas = 0;
        for (int i = 0; i < gas.length; i++) {
            restGas += gas[i] - cost[i];
            // 油不够了, 更新新的起始点
            if (restGas < 0) {
                totalLackGas += restGas;
                startPos = i + 1;
                restGas = 0;
            }
        }
        return (restGas + totalLackGas) >= 0 ? startPos : -1;
    }

    public static void main(String[] args) {
        int[] gas = new int[]{4};
        int[] cost = new int[]{5};
        System.out.println(new Solution().canCompleteCircuit(gas, cost));
    }
}