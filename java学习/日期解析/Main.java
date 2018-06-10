package 日期解析;

import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class Main {
    public static void main(String args[]) {
        String s = "170505191801";
        System.out.println(parseDate(s));
    }

    private static Date parseDate(String serverTime) {
        SimpleDateFormat f = new SimpleDateFormat("yyMMddHHmmss");
        Date date = null;
        try {
            date = f.parse(serverTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}


