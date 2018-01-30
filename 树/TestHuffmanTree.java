import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TestHuffmanTree {
    public static void main(String[] args) throws IOException {
        //统计每个字符出现的次数
        System.setIn(new FileInputStream("input.txt"));
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            String text = in.nextLine();
            System.out.println(countHuffmanCode(text));
        }
    }

    private static String countHuffmanCode(String text) {
        Map<Integer, Integer> counter = new HashMap<>();
        for (int i = 0; i < text.length(); i++) {
            int k = text.charAt(i);
            counter.putIfAbsent(k, 0);
            counter.put(k, counter.get(k) + 1);
        }
        System.out.println("字符频率为:");
        System.out.println(counter);
        HuffmanTree huffmanTree = new HuffmanTree();
        Map<Integer, String> huffmanCodes = huffmanTree.getHuffmanCode(counter);
        System.out.println("哈弗曼编码为:");
        System.out.println(huffmanCodes);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            int k = text.charAt(i);
            sb.append(huffmanCodes.get(k));
        }
        return sb.toString();
    }

}

