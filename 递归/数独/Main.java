package 数独;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
//        Scanner in = getScanner(System.in);
        Scanner in = getScanner("input.txt");
        int[][] numbers = new int[9][9];
        while (in.hasNext()) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    numbers[i][j] = in.nextInt();
                }
            }
            if (compute(numbers, 0, 0)) {
                print(numbers);
            }
        }
    }

    /**
     * 从上往下扫描
     *
     * @param numbers 数独数据
     * @param nowRow  当前行
     * @param nowCol  当前列
     * @return
     */
    private static boolean compute(int[][] numbers, int nowRow, int nowCol) {
        if (nowRow >= numbers.length) {
            return true;
        }
        if (nowCol >= numbers.length) {
            if (nowRow < numbers.length) {
                return compute(numbers, nowRow + 1, 0);
            } else {
                return true;
            }
        }
        if (numbers[nowRow][nowCol] != 0) {
            return compute(numbers, nowRow, nowCol + 1);
        }

        for (int i = 1; i <= 9; i++) {
            numbers[nowRow][nowCol] = i;
            if (isValid(numbers, nowRow, nowCol)) {
                if (compute(numbers, nowRow, nowCol + 1)) {
                    return true;
                }
            }
        }
        numbers[nowRow][nowCol] = 0;
        return false;
    }

    /**
     * 打印结果
     *
     * @param numbers 九宫格数字
     */
    private static void print(int[][] numbers) {
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < numbers[0].length; j++) {
                System.out.print(numbers[i][j]);
                if (j < numbers[0].length - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println("");
        }
    }

    /**
     * 判断当前位置是否合理
     *
     * @param numbers 九宫格
     * @param nowRow  当前行
     * @param nowCol  当前列
     * @return　当前位置填的数字是否合理
     */
    private static boolean isValid(int[][] numbers, int nowRow, int nowCol) {
        //检查此行有没有和当前位置相同的元素
        for (int i = 0; i < numbers[nowRow].length; i++) {
            if (i == nowCol) {
                continue;
            }

            if (numbers[nowRow][i] == numbers[nowRow][nowCol]) {
                return false;
            }
        }

        //检查此列有没有和当前位置相同的元素
        for (int i = 0; i < numbers.length; i++) {
            if (i == nowRow) {
                continue;
            }
            if (numbers[i][nowCol] == numbers[nowRow][nowCol]) {
                return false;
            }
        }

        //检查所在九宫格
        int startRowPos = (nowRow / 3) * 3;
        int startColPos = (nowCol / 3) * 3;
        for (int i = startRowPos; i < startRowPos + 3; i++) {
            for (int j = startColPos; j < startColPos + 3; j++) {
                if (i == nowRow && j == nowCol) {
                    continue;
                }
                if (numbers[i][j] == numbers[nowRow][nowCol]) {
                    return false;
                }
            }
        }

        return true;
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
