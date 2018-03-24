package 头条.第三题;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    static final char[][][] NUMBERS = {
            {
                    {'6', '6', '6', '6', '6'},
                    {'6', '.', '.', '.', '6'},
                    {'6', '.', '.', '.', '6'},
                    {'6', '.', '.', '.', '6'},
                    {'6', '6', '6', '6', '6'},
            }, {
            {'.', '.', '.', '.', '6'},
            {'.', '.', '.', '.', '6'},
            {'.', '.', '.', '.', '6'},
            {'.', '.', '.', '.', '6'},
            {'.', '.', '.', '.', '6'},
    }, {
            {'6', '6', '6', '6', '6'},
            {'.', '.', '.', '.', '6'},
            {'6', '6', '6', '6', '6'},
            {'6', '.', '.', '.', '.'},
            {'6', '6', '6', '6', '6'},
    }, {
            {'6', '6', '6', '6', '6'},
            {'.', '.', '.', '.', '6'},
            {'6', '6', '6', '6', '6'},
            {'.', '.', '.', '.', '6'},
            {'6', '6', '6', '6', '6'},
    }, {
            {'6', '.', '.', '.', '6'},
            {'6', '.', '.', '.', '6'},
            {'6', '6', '6', '6', '6'},
            {'.', '.', '.', '.', '6'},
            {'.', '.', '.', '.', '6'},
    }, {
            {'6', '6', '6', '6', '6'},
            {'6', '.', '.', '.', '.'},
            {'6', '6', '6', '6', '6'},
            {'.', '.', '.', '.', '6'},
            {'6', '6', '6', '6', '6'},
    }, {
            {'6', '6', '6', '6', '6'},
            {'6', '.', '.', '.', '.'},
            {'6', '6', '6', '6', '6'},
            {'6', '.', '.', '.', '6'},
            {'6', '6', '6', '6', '6'},
    }, {
            {'6', '6', '6', '6', '6'},
            {'.', '.', '.', '.', '6'},
            {'.', '.', '.', '.', '6'},
            {'.', '.', '.', '.', '6'},
            {'.', '.', '.', '.', '6'},
    }, {
            {'6', '6', '6', '6', '6'},
            {'6', '.', '.', '.', '6'},
            {'6', '6', '6', '6', '6'},
            {'6', '.', '.', '.', '6'},
            {'6', '6', '6', '6', '6'},
    }, {
            {'6', '6', '6', '6', '6'},
            {'6', '.', '.', '.', '6'},
            {'6', '6', '6', '6', '6'},
            {'.', '.', '.', '.', '6'},
            {'6', '6', '6', '6', '6'},
    },
    };

    public static void main(String[] args) throws IOException {
        try {
            System.setIn(new FileInputStream("input.txt"));
        } catch (FileNotFoundException ignored) {
        }
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        while (n-- > 0) {
            List<String> expression = getStringList(in.next());
            long result = Claculator.compute(expression);
            printNunber(result);
        }
    }

    private static void printNunber(long number) {
        String numberStr = String.valueOf(number);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < numberStr.length(); j++) {
                int n = numberStr.charAt(j) - '0';
                char[][] numChars = NUMBERS[n];
                for (int k = 0; k < 5; k++) {
                    System.out.print(numChars[i][k]);
                }
                if (j < numberStr.length() - 1) {
                    System.out.print("..");
                }
            }
            System.out.println("");
        }
    }

    private static List<String> getStringList(String str) {
        List<String> result = new ArrayList<>();
        StringBuilder num = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                num.append(str.charAt(i));
            } else {
                if (num.length() != 0) {
                    result.add(num.toString());
                }
                result.add(str.charAt(i) + "");
                num = new StringBuilder();
            }
        }
        if (num.length() != 0) {
            result.add(num.toString());
        }
        return result;
    }
}

class Claculator {

    public static long compute(List<String> expression) {
        //操作数栈
        Stack<Long> numberStack = new Stack<>();
        //表达式栈
        Stack<Operator> operatorStack = new Stack<>();
        for (String s : expression) {
            //数字直接入栈
            if (Operator.getOperator(s) == Operator.OTHER) {
                numberStack.push(Long.parseLong(s));
                continue;
            }
            Operator operator = Operator.getOperator(s);
            if (operatorStack.isEmpty()) {
                operatorStack.push(operator);
                continue;
            }
            if (operator.getPriority() <= operatorStack.peek().getPriority()) {
                while (operatorStack.size() > 0 && numberStack.size() >= 2 && operator.getPriority() <= operatorStack.peek().getPriority()) {
                    //弹出运算符和两个数组进行计算
                    long n1 = numberStack.pop();
                    long resTmp = operatorStack.pop().compute(numberStack.pop(), n1);
                    numberStack.push(resTmp);
                }
                operatorStack.push(operator);
            } else {
                //当前运算符的优先级比较高
                operatorStack.push(operator);
            }
        }
        while (operatorStack.size() > 0 && numberStack.size() >= 2) {
            long n1 = numberStack.pop();
            long resTmp = operatorStack.pop().compute(numberStack.pop(), n1);
            numberStack.push(resTmp);
        }
        return numberStack.pop();
    }
}

enum Operator {
    PLUS("+", 1) {
        @Override
        public long compute(long num1, long num2) {
            return num1 + num2;
        }
    },
    MINUS("-", 1) {
        @Override
        public long compute(long num1, long num2) {
            return num1 - num2;
        }
    },
    MULTIPLY("*", 2) {
        @Override
        public long compute(long num1, long num2) {
            return num1 * num2;
        }
    },
    DIVIDE("/", 2) {
        @Override
        public long compute(long num1, long num2) {
            return num1 / num2;
        }
    },
    OTHER("", 0) {
        @Override
        public long compute(long num1, long num2) {
            return 0;
        }
    };

    private String value;
    private int priority;

    Operator(String value, int priority) {
        this.value = value;
        this.priority = priority;
    }

    public static Operator getOperator(String value) {
        for (Operator operator : Operator.values()) {
            if (operator.value.equals(value)) {
                return operator;
            }
        }
        return OTHER;
    }

    public int getPriority() {
        return priority;
    }

    public abstract long compute(long num1, long num2);
}

