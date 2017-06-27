import java.util.*;

/**
 * 用数组表示图
 *
 * dfs的退出条件为：时间超出
 * 记得加上等待取餐的时间
 */
public class Main {
   public static void main(String args[]) {

   }

   /**
    * @Param graph 图
    * @Param flag 是否走过这个点
    * @Param spentTime 已经花费的时间
    * @nowPos 当前位置点
    *
    * @Return 完成任务数量
    */
   private static int dfs(int[][] graph, int[] flag, int spentTime, int nowPos ) {
       for(int i = nowPos; i < graph[nowPos].length; i++ ) {
           //此路可通
           if(graph[nowPos][i] > 0) {

           }
       }
   }
}


