import java.util.*;
class Calculator {

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
