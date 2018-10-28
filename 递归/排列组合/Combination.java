package 排列组合;

import java.util.*;

public class Combination {

    public <T> void combination(T[] array, int n) {
        combination(array, new int[n], 0, n);
    }

    public <T> void combination(T[] array, int[] indexes, int start, int n) {
        if (n == 1) {
            String prefix = generatePrefix(array, indexes);
            for (int i = start; i < array.length; i++) {
                System.out.print(prefix);
                System.out.print(array[i]);
                System.out.println(']');
            }
            return;
        }

        for (int i = start; i <= array.length - n; i++) {
            indexes[indexes.length - n] = i;
            combination(array, indexes, i + 1, n - 1);
        }
    }

    private <T> String generatePrefix(T[] array, int[] indexes) {
        StringBuilder prefixBuilder = new StringBuilder("[");
        for (int i = 0; i < indexes.length - 1; i++) {
            prefixBuilder.append(array[indexes[i]]).append(", ");
        }
        return prefixBuilder.toString();
    }

    public static void main(String[] args) {
        Combination c = new Combination();
        c.combination(new Integer[] {1, 2, 3, 4}, 2);
    }

}
