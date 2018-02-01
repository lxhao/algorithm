package 树遍历;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        TreeIter<Integer> treeIter = new TreeIter<>();
        Scanner in = new Scanner(new FileInputStream("input.txt"));
        TreeNode<Integer> root = new TreeBuilder().createTree(in);

        List<Integer> preOrder = treeIter.preOrder(root);
        System.out.println(preOrder);

        List<Integer> midOrder = treeIter.midOrder(root);
        System.out.println(midOrder);

        List<Integer> postOrder = treeIter.postOrder(root);
        System.out.println(postOrder);

        List<Integer> postOrder2 = treeIter.postOrder2(root);
        System.out.println(postOrder2);
    }
}
