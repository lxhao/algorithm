import java.util.*;

public class KmpMatcher {
    private static final int TEST_SIZE = (int)Math.pow(10, 8);

    public static void main(String[] args) {
        KmpMatcher main = new KmpMatcher();
        main.testSpeed();
    }

    private void testSpeed() {
        StringBuilder text = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < TEST_SIZE; i++) {
            text.append(random.nextInt(27) + 'a');
        }
        String pattern = text.substring(TEST_SIZE / 100, TEST_SIZE / 99);
        long startTime = System.currentTimeMillis();
        System.out.println(kmpMatch(text.toString(), pattern));
        long endTime = System.currentTimeMillis();
        System.out.println("kmp耗时:" + (endTime - startTime));


        startTime = System.currentTimeMillis();
        System.out.println(violentMatch(text.toString(), pattern));
        endTime = System.currentTimeMillis();
        System.out.println("朴素方法耗时:" + (endTime - startTime));
    }

    private int kmpMatch(String source, String pattern) {
        int sourceLen = source.length();
        int patternLen = pattern.length();
        int sourcePos = 0;
        int patternPos = 0;
        int[] next = getNext(pattern);
        for (; sourcePos < sourceLen && patternPos < patternLen; ) {
            if (patternPos == -1 || source.charAt(sourcePos) == pattern.charAt(patternPos)) {
                patternPos++;
                sourcePos++;
            } else {
                patternPos = next[patternPos];
            }
        }
        return patternPos == patternLen ? sourcePos - patternLen : -1;
    }

    private int[] getNext(String s) {
        int[] next = new int[s.length()];
        int len = s.length();
        next[0] = -1;
        for (int i = 0, k = -1; i < len - 1; ) {
            if (k == -1 || s.charAt(i) == s.charAt(k)) {
                i++;
                k++;
                next[i] = k;
            } else {
                k = next[k];
            }
        }
        return next;
    }


    private int violentMatch(String s1, String s2) {
        if (s1 == null || s2 == null) {
            return -1;
        }
        int s1Pos = 0;
        int s2Pos = 0;
        int s1Len = s1.length();
        int s2Len = s2.length();

        while (s1Pos < s1Len && s2Pos < s2Len) {
            if (s1.charAt(s1Pos) == s2.charAt(s2Pos)) {
                s1Pos++;
                s2Pos++;
            } else {
                s2Pos = 0;
                s1Pos = s1Pos - s2Pos + 1;
            }
        }

        return s2Pos == s2Len ? s1Pos - s2Len : -1;
    }
}


