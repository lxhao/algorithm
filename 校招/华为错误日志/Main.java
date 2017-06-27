import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class Log implements Comparable {
    String filename;
    int count = 1;
    int lineNumber;

    Log(String filename, int lineNumber) {
        this.filename = filename;
        this.lineNumber = lineNumber;
    }

    @Override
    public boolean equals(Object obj) {
        return this.filename.equals(((Log) obj).filename) && this.lineNumber == (((Log) obj).lineNumber);
    }

    @Override
    public int compareTo(Object o) {
        return ((Log) o).count - this.count;
    }
}

public class Main {
    public static void main(String[] args) {
//        Scanner in = getScanner(System.in);
        List<Log> logList = new ArrayList<>();
        int count = 0;
        int lineNumber;
        Scanner in = getScanner("input.txt");
        while (in.hasNext()) {
            String file = in.next();
            lineNumber = in.nextInt();
            String filename = getFilename(file);
            count++;
            Log log = new Log(filename, lineNumber);
            int indexPos = logList.indexOf(log);
            if (indexPos == -1) {
                logList.add(log);
            } else {
                logList.get(indexPos).count = logList.get(indexPos).count + 1;
            }
        }
        Collections.sort(logList);
        for (int i = 0; i < 8; i++) {
            Log e = logList.get(i);
            if (e.filename.length() > 16) {
                e.filename = e.filename.substring(e.filename.length() - 16, e.filename.length());
            }
            System.out.printf("%s %s %s ", e.filename, e.lineNumber, e.count);
        }
    }

    private static String getFilename(String file) {
        int index = file.lastIndexOf('\\');
        if (index == -1) {
            return file;
        }

        return file.substring(index + 1, file.length());
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

