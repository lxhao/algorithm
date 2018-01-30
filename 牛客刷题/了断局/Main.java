package 了断局;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        System.setIn(new FileInputStream("input.txt"));
        Scanner in = new Scanner(System.in);
        long[] arr = getArr();
        while (in.hasNext()) {
            int pos = in.nextInt();
            System.out.println(arr[pos - 1]);
        }
        in.close();
    }

    private static long[] getArr() {
        long[] arr = new long[50];
        arr[1] = 1;
        arr[2] = 1;
        for (int i = 3; i < arr.length; i++) {
            arr[i] = arr[i - 1] + arr[i - 2] + arr[i - 3];
        }
        return arr;
    }
}

