package 美团CodeM初赛.第二题;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        try {
            System.setIn(new FileInputStream("input.txt"));
        } catch (FileNotFoundException ignored) {
        }
        StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
        in.nextToken();
        int n = (int) in.nval;
        long[] initPos = new long[n + 1];
        for (int i = 1; i <= n; i++) {
            in.nextToken();
            initPos[i] = (long) in.nval;
        }

        long[] targetPos = new long[n + 1];
        for (int i = 1; i <= n; i++) {
            in.nextToken();
            targetPos[i] = (long) in.nval;
        }

        int tailOfInitPos = n;
        int tailOfTargetPos = n;
        long ans = 0;
        while (tailOfInitPos >= 1) {
            while (tailOfInitPos >= 1 && initPos[tailOfInitPos] == 0) {
                tailOfInitPos--;
            }
            while (tailOfTargetPos >= 1 && targetPos[tailOfTargetPos] == 0) {
                tailOfTargetPos--;
            }
            if (tailOfInitPos < 1) {
                break;
            }
            if (tailOfTargetPos < 1) {
                ans += initPos[tailOfInitPos] * (tailOfInitPos - 1);
                initPos[tailOfInitPos] = 0;
                continue;
            }
            long min = Math.min(initPos[tailOfInitPos], targetPos[tailOfTargetPos]);
            if (tailOfInitPos >= tailOfTargetPos) {
                ans += min * (tailOfInitPos - tailOfTargetPos);
                initPos[tailOfInitPos] -= min;
                targetPos[tailOfTargetPos] -= min;
            } else {
                ans += targetPos[tailOfTargetPos] * (tailOfTargetPos - 1);
                targetPos[tailOfTargetPos] = 0;
            }
        }
        System.out.println(ans);
    }

}
