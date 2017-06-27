import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private static Map<String, Integer> cardValue = new HashMap<>();

    static {
        int i = 0;
        cardValue.put("3", i++);
        cardValue.put("4", i++);
        cardValue.put("5", i++);
        cardValue.put("6", i++);
        cardValue.put("7", i++);
        cardValue.put("8", i++);
        cardValue.put("9", i++);
        cardValue.put("10", i++);
        cardValue.put("J", i++);
        cardValue.put("Q", i++);
        cardValue.put("K", i++);
        cardValue.put("A", i++);
        cardValue.put("2", i++);
        cardValue.put("joker", i++);
        cardValue.put("JOKER", i++);
    }

    public static void main(String[] args) {
        Scanner in = getScanner(System.in);
//        Scanner in = getScanner("input.txt");
        while (in.hasNext()) {
            String line = in.nextLine();
            String[] pokers = line.split("-");
            String[] poker1 = pokers[0].split("\\s");
            String[] poker2 = pokers[1].split("\\s");

            CARD_TYPE poker1Type = CARD_TYPE.getCardType(poker1);
            CARD_TYPE poker2Type = CARD_TYPE.getCardType(poker2);
            if (poker1Type == CARD_TYPE.JOKER) {
                print(poker1);
            } else if (poker2Type == CARD_TYPE.JOKER) {
                print(poker2);
            } else if (poker1Type == CARD_TYPE.FOUR) {
                print(poker1);
            } else if (poker2Type == CARD_TYPE.FOUR) {
                print(poker2);
            } else if (poker1Type != poker2Type) {
                System.out.println("ERROR");
            } else if (cardValue.get(poker1[0]) > cardValue.get(poker2[0])) {
                print(poker1);
            } else {
                print(poker2);
            }
        }
    }

    private static void print(String[] cards) {
        for (int i = 0; i < cards.length; i++) {
            System.out.print(cards[i]);
            if (i < cards.length - 1) {
                System.out.print(" ");
            }
        }
        System.out.println("");
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

enum CARD_TYPE {
    //个子
    SINGLE,
    //对子
    DOUBLE,
    //三个
    THREE,
    //四个
    FOUR,
    //顺子
    STRAIGHT,
    //其余类型
    OTHER,
    //王炸
    JOKER;


    public static CARD_TYPE getCardType(String[] cards) {
        if (cards.length == 1) {
            return SINGLE;
        }
        if (cards.length == 2) {
            if (cards[0].toLowerCase().equals("joker")) {
                return JOKER;
            }
            return DOUBLE;
        }
        if (cards.length == 3) {
            return THREE;
        }
        if (cards.length == 4) {
            return FOUR;
        }
        if (cards.length == 5) {
            return STRAIGHT;
        }
        return OTHER;
    }
}

