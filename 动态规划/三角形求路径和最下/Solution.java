package 三角形求路径和最下;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Solution {
    public static void main(String args[]) throws FileNotFoundException {
        ArrayList<ArrayList<Integer>> data = new ArrayList<>();
        Scanner in = new Scanner(new FileInputStream("input.txt"));
        while (in.hasNext()) {
            String line = in.nextLine();
            String[] numbers = line.split("\\s");
            ArrayList<Integer> list = new ArrayList<>();
            for (String s : numbers) {
                list.add(Integer.valueOf(s));
            }
            data.add(list);
        }
        System.out.println(new Solution().minimumTotal(data));
    }

    public int minimumTotal(ArrayList<ArrayList<Integer>> triangle) {
        if (triangle == null || triangle.size() == 0) {
            return 0;
        }
        int min = triangle.get(0).get(0);
        int totalLevel = triangle.size();
        int[][] minOfLevel = new int[2][totalLevel];
        int flag = 0;
        minOfLevel[flag][0] = triangle.get(0).get(0);
        for (int level = 1; level < totalLevel; level++) {
            min = Integer.MAX_VALUE;
            for (int pos = 0; pos <= level; pos++) {
                int minTmp = Integer.MAX_VALUE;
                if (pos <= level - 1) {
                    minTmp = minOfLevel[flag][pos];
                }
                if (pos - 1 >= 0 && minOfLevel[flag][pos - 1] < minTmp) {
                    minTmp = minOfLevel[flag][pos - 1];
                }
                minTmp += triangle.get(level).get(pos);
                minOfLevel[1 - flag][pos] = minTmp;
                min = Math.min(min, minTmp);
            }
            flag = (flag == 0 ? 1 : 0);
        }
        return min;
    }
}

