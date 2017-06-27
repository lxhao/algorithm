import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.Stack;

/**
 * Created by lxhao on 17-6-24.
 */

public class Test {
    public static void main(String args[]) {
        Scanner in = getScanner("input.txt");
        while (in.hasNext()) {
            String[] expression = in.nextLine().split("\\s");
            System.out.println(Claculator.compute(expression));
        }
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

/**
 * 表达式求值
 * <p>
 * 栈的规则是先进后出。利用压栈的思想来计算四则运算表达式是这样的：我们给定两个栈，
 * 一个用来存放数字、一个用来存放对应的操作符。假定我们有一个给定的四则运算表达式a+b+c/d*(e+f)-d*a，
 * 那我们先把这个表达式拆分成一个个的数字或者是运算符、或者就是括号了。
 * 然后我们从左至右遍历每一个元素，遍历过程中遵循步骤和原则如下：
 * （1）遇到数字则直接压到数字栈顶。
 * （2）遇到运算符（+-/*）时，若操作符栈为空，则直接放到操作符栈顶，否则，见（3）。
 * （3）若操作符栈顶元素的优先级比当前运算符的优先级小，则直接压入栈顶，否则执行步骤（4）。
 * （4）弹出数字栈顶的两个数字并弹出操作符栈顶的运算符进行运算，把运算结果压入数字栈顶，重复（2）和（3）直到当前运算符被压入操作符栈顶。
 * （5）遇到左括号“（”时则直接压入操作符栈顶。
 * （6）遇到右括号“）”时则依次弹出操作符栈顶的运算符运算数字栈的最顶上两个数字，直到弹出的操作符为左括号。
 * <p>
 * <p>
 * http://elim.iteye.com/blog/1981197
 */

class Claculator {

    public static double compute(String[] expression) {
        //操作数栈
        Stack<Double> numberStack = new Stack<>();
        //表达式栈
        Stack<Operator> operatorStack = new Stack<>();
        for (String s : expression) {
            //数字直接入栈
            if (Operator.getOperator(s) == Operator.OTHER) {
                numberStack.push(Double.parseDouble(s));
                continue;
            }
            Operator operator = Operator.getOperator(s);
            //栈为空或者遇到左括号直接入栈
            if (operatorStack.isEmpty() || operator == Operator.LEFT_BRACKET) {
                operatorStack.push(operator);
                continue;
            }
            //遇到右括号则一直计算到左括号
            if (operator == Operator.RIGHT_BRACKET) {
                while (operatorStack.peek() != Operator.LEFT_BRACKET && numberStack.size() >= 2) {
                    //因为后面的数会先弹出来，除法和减法是区分先后顺序的(比如1 / 2不能算成2 / 1)
                    double n1 = numberStack.pop();
                    double resTmp = operatorStack.pop().compute(numberStack.pop(), n1);
                    numberStack.push(resTmp);
                }
                //不合法的表达式
                if (operatorStack.pop() != Operator.LEFT_BRACKET) {
                    throw new IllegalArgumentException();
                }
                continue;
            }
            if (operator.getPriority() <= operatorStack.peek().getPriority()) {
                //不合法的表达式
                if (numberStack.size() < 2) {
                    throw new IllegalArgumentException();
                }
                while (operatorStack.size() > 0 && numberStack.size() >= 2 && operator.getPriority() <= operatorStack.peek().getPriority()) {
                    //弹出运算符和两个数组进行计算
                    double n1 = numberStack.pop();
                    double resTmp = operatorStack.pop().compute(numberStack.pop(), n1);
                    numberStack.push(resTmp);
                }
                operatorStack.push(operator);
            } else {
                //当前运算符的优先级比较高
                operatorStack.push(operator);
            }
        }
        while (operatorStack.size() > 0 && numberStack.size() >= 2) {
            double n1 = numberStack.pop();
            double resTmp = operatorStack.pop().compute(numberStack.pop(), n1);
            numberStack.push(resTmp);
        }
        if (numberStack.size() == 1 && operatorStack.isEmpty()) {
            return numberStack.pop();
        }
        //不合法的表达式
        throw new IllegalArgumentException();
    }
}

/**
 * 运算符
 */

enum Operator {
    PLUS("+", 1) {
        @Override
        public double compute(double num1, double num2) {
            return num1 + num2;
        }
    },
    MINUS("-", 1) {
        @Override
        public double compute(double num1, double num2) {
            return num1 - num2;
        }
    },
    MULTIPLY("*", 2) {
        @Override
        public double compute(double num1, double num2) {
            return num1 * num2;
        }
    },
    DIVIDE("/", 2) {
        @Override
        public double compute(double num1, double num2) {
            return num1 / num2;
        }
    },
    REMAINDER("%", 2) {
        @Override
        public double compute(double num1, double num2) {
            return num1 % num2;
        }
    },
    EXPONENTIATION("^", 3) {
        @Override
        public double compute(double num1, double num2) {
            return Math.pow(num1, num2);
        }
    },
    INTEGER_DIVISION("//", 2) {
        @Override
        public double compute(double num1, double num2) {
            return Math.floor(num1 / num2);
        }
    },
    RIGHT_BRACKET(")", 0) {
        @Override
        public double compute(double num1, double num2) {
            throw new UnsupportedOperationException();
        }
    },
    LEFT_BRACKET("(", 0) {
        @Override
        public double compute(double num1, double num2) {
            throw new UnsupportedOperationException();
        }
    },
    OTHER("", 0) {
        @Override
        public double compute(double num1, double num2) {
            return 0;
        }
    };

    private String value;
    private int priority;

    private Operator(String value, int priority) {
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

    public abstract double compute(double num1, double num2);
}


