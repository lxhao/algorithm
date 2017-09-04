import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class 阿里砖块 {

    static int leastBricks(List<List<Integer>> wall) {
        //行数
        int lineCount = wall.size();
        //每一行最近的位置
        int[] pos = new int[lineCount];
        Arrays.fill(pos, 1);
        //每一行当前的长度
        int[] len = new int[lineCount];
        for (int i = 0; i < lineCount; i++) {
            List<Integer> l = wall.get(i);
            len[i] += l.get(0);
        }
        int maxSize = Integer.MIN_VALUE;
        for (int i = 0; i < wall.size(); i++) {
            maxSize = Math.max(wall.get(i).size(), maxSize);
        }
        int res = Integer.MAX_VALUE;
        while (maxSize-- > 0) {
            int minPos = getMinPos(len);
            int minSum = len[minPos];
            int tempRes = 0;
            for (int i = 0; i < lineCount; i++) {
                //当前行的砖头
                List<Integer> l = wall.get(i);
                if (len[i] != minSum) {
                    tempRes++;
                } else if (len[i] == minSum) {
                    if (pos[i] < l.size()) {
                        len[i] += l.get(pos[i]);
                        if (pos[i] <= l.size()) {
                            pos[i]++;
                        }
                    }
                }
            }
            res = Math.min(res, tempRes);
        }
        return res;
    }

    private static int getMinPos(int[] sum) {
        int res = 0;
        int min = sum[0];
        for (int i = 1; i < sum.length; i++) {
            if (sum[i] < min) {
                res = i;
                min = sum[i];
            }
        }
        return res;
    }

    public static void main(String[] args) {
        List<List<Integer>> vecvecRes = new ArrayList<List<Integer>>();
        List<Integer> list = new ArrayList<Integer>();
        Scanner in = new Scanner(System.in);
        int res = -1;
        int row = 0;
        row = Integer.parseInt(in.nextLine().trim());
        int i = 0;
        while (i < row) {
            int a = Integer.parseInt(in.nextLine().trim());
            if (a == 0) {
                vecvecRes.add(list);
                list = new ArrayList<Integer>();
                i++;
            } else {
                list.add(a);
            }
        }
        res = leastBricks(vecvecRes);
        System.out.print(res);
    }
}