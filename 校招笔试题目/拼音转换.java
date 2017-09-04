import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class 拼音转换 {
    private static final String ERROR = "ERROR";
    static Map<String, String> englishmap = new HashMap<>();
    static Map<String, String> pinyinmap = new HashMap<>();

    static {
        englishmap.put("Zero", "Ling");
        englishmap.put("One", "Yi");
        englishmap.put("Two", "Er");
        englishmap.put("Three", "San");
        englishmap.put("Four", "Si");
        englishmap.put("Five", "Wu");
        englishmap.put("Six", "Liu");
        englishmap.put("Seven", "Qi");
        englishmap.put("Eight", "Ba");
        englishmap.put("Nine", "Jiu");

        pinyinmap.put("Ling", "Zero");
        pinyinmap.put("Yi", "One");
        pinyinmap.put("Er", "Two");
        pinyinmap.put("San", "Three");
        pinyinmap.put("Si", "Four");
        pinyinmap.put("Wu", "Five");
        pinyinmap.put("Liu", "Six");
        pinyinmap.put("Qi", "Seven");
        pinyinmap.put("Ba", "Eight");
        pinyinmap.put("Jiu", "Nine");
        pinyinmap.put("Double", "Double");
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            String line = in.nextLine().trim();
            line += "A";
            String res = getValue(line, englishmap);
            if (res.equals(ERROR)) {
                System.out.println(getValue(line, englishmap));
            } else {
                System.out.println(res);
            }
        }
        in.close();
    }

    private static String getValue(String line, Map<String, String> englishmap) {
        StringBuilder sb = new StringBuilder();
        StringBuilder res = new StringBuilder();
        boolean isDouble = false;
        for (int i = 0; i < line.length() - 1; i++) {
            sb.append(line.charAt(i));
            //大写字母
            if (line.charAt(i + 1) <= 'Z') {
                String word = englishmap.get(sb.toString());
                if (word == null) {
                    res.setLength(0);
                    res.append(ERROR);
                    break;
                }
                if (isDouble) {
                    res.append(word);
                    res.append(word);
                } else {
                    isDouble = word.equals("Double");
                    if (!isDouble) {
                        res.append(word);
                    }
                }
                sb.setLength(0);
            }
        }
        return (res.toString());
    }
}


