import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

public class TreeTest {
    public static void main(String[] args) {
        BinarySortTree<Integer> tree = new BinarySortTree<>();
        //input.txt的内容为:
        //15 5 3 12 16 20 23 13 18 10 6 7
        Scanner in = getScanner("input.txt");
        //测试插入元素
        while (in.hasNext()) {
            tree.insert(in.nextInt());
        }
        //测试删除元素
        testDelete(tree);

    }

    private static void testDelete(BinarySortTree<Integer> tree) {
        //层次遍历元素
        List<List<Integer>> elements = tree.levelTraverse();
        System.out.println(elements);

        //测试删除根节点
//        tree.delNode(tree.getRoot().val);
//        elements = tree.levelTraverse();
//        System.out.println(elements);

        //测试删除带两个孩子节点的节点
//        tree.delNode(5);
//        elements = tree.levelTraverse();
//        System.out.println(elements);

//        //测试删除带左孩子节点的节点
//        tree.delNode(10);
//        elements = tree.levelTraverse();
//        System.out.println(elements);

        //测试删除带右孩子节点的节点
//        tree.delNode(16);
//        elements = tree.levelTraverse();
//        System.out.println(elements);

        //测试删除没有孩子子节点的节点
//        tree.delNode(18);
//        elements = tree.levelTraverse();
//        System.out.println(elements);

        //测试删除不存在的节点
//        tree.delNode(181);
//        elements = tree.levelTraverse();
//        System.out.println(elements);
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
