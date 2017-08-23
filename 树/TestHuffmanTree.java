import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class TestHuffmanTree {
    public static void main(String[] args) throws IOException {
        //统计每个字符出现的次数
        BufferedReader bis = null;
        try {
            bis = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Map<Integer, Integer> counter = new HashMap<>();
        while (true) {
            int k = bis.read();
            if (k == -1) {
                break;
            }
            counter.putIfAbsent(k, 0);
            counter.put(k, counter.get(k) + 1);
        }
        System.out.println("字符频率为:");
        System.out.println(counter);
        HuffmanTree huffmanTree = new HuffmanTree();
        Map<Integer, String> huffmanCodes = huffmanTree.getHuffmanCode(counter);
        System.out.println("哈弗曼编码为:");
        System.out.println(huffmanCodes);
    }
}

