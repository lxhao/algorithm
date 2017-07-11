import java.util.Stack;

public class Solution {
    public static void main(String[] args) {
        String[] tokens1 = {
                "2", "1", "+", "3", "*"
        };

        Solution solution = new Solution();
        System.out.println("test results :");
        System.out.println(solution.evalRPN(tokens1) == 9);

        String[] tokens2 = {
                "4", "13", "5", "/", "+"
        };
        System.out.println(solution.evalRPN(tokens2) == 6);

    }

    public int evalRPN(String[] tokens) {
        Stack<String> expression = new Stack<>();
        for (String operatorStr : tokens) {
            Operator operator = Operator.getOperator(operatorStr);
            if (operator == Operator.OTHER) {
                expression.push(operatorStr);
                continue;
            }

            String num2 = expression.pop();
            String num1 = expression.pop();
            int value = operator.compute(Integer.parseInt(num1), Integer.parseInt(num2));
            expression.push(String.valueOf(value));
        }
        return Integer.parseInt(expression.pop());
    }

    enum Operator {
        PLUS("+") {
            @Override
            public int compute(int num1, int num2) {
                return num1 + num2;
            }
        },
        MINUS("-") {
            @Override
            public int compute(int num1, int num2) {
                return num1 - num2;
            }
        },
        MULTIPLY("*") {
            @Override
            public int compute(int num1, int num2) {
                return num1 * num2;
            }
        },
        DIVIDE("/") {
            @Override
            public int compute(int num1, int num2) {
                return num1 / num2;
            }
        },
        OTHER("") {
            @Override
            public int compute(int num1, int num2) {
                return 0;
            }
        };

        private String value;

        private Operator(String value) {
            this.value = value;
        }

        public static Operator getOperator(String value) {
            for (Operator operator : Operator.values()) {
                if (operator.value.equals(value)) {
                    return operator;
                }
            }
            return OTHER;
        }

        public abstract int compute(int num1, int num2);
    }

}
