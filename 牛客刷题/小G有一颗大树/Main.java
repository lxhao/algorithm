package 小G有一颗大树;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * https://www.nowcoder.com/acm/contest/74/E
 */

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        System.setIn(new FileInputStream("input.txt"));
        Scanner in = new Scanner(System.in);
        Main main = new Main();
        int n = in.nextInt();
        List[] tree = new List[n + 1];
        for (int i = 1; i <= n; i++) {
            tree[i] = new ArrayList<>();
        }
        while (in.hasNext()) {
            int parent = in.nextInt();
            int child = in.nextInt();
            tree[parent].add(child);
        }
        int[] childrenNumber = new int[n + 1];
        for (int node = 1; node < tree.length; node++) {
            childrenNumber[node] = main.getChildNumber(tree, node, childrenNumber);
        }
        main.findBalanceNode(tree, childrenNumber);
        in.close();
    }

    private void findBalanceNode(List[] tree, int[] childrenNumber) {
        int balanceNode = 0;
        int balanceValue = Integer.MAX_VALUE;
        for (int i = 1; i < tree.length; i++) {
            List<Integer> children = tree[i];
            List<Integer> tmp = new ArrayList<>();
            int sum = 0;
            for (int child : children) {
                tmp.add(childrenNumber[child]);
                sum += childrenNumber[child];
            }
            tmp.add(tree.length - 2 - sum);
            int max = Collections.max(tmp);
            if (max < balanceValue) {
                balanceValue = max;
                balanceNode = i;
            }
        }
        System.out.printf("%d %d\n", balanceNode, balanceValue);
    }

    private int getChildNumber(List[] tree, int node, int[] childrenNumbers) {
        int res = 0;
        if (childrenNumbers[node] > 0) {
            return childrenNumbers[node];
        }
        List<Integer> children = tree[node];
        for (Integer child : children) {
            res += getChildNumber(tree, child, childrenNumbers);
        }
        return res + 1;
    }
}

