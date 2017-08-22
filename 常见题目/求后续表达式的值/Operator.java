public enum Operator {
    PLUS("+") {
        @Override
        public double compute(double num1, double num2) {
            return num1 + num2;
        }
    },
    MINUS("-") {
        @Override
        public double compute(double num1, double num2) {
            return num1 - num2;
        }
    },
    MULTIPLY("*") {
        @Override
        public double compute(double num1, double num2) {
            return num1 * num2;
        }
    },
    DIVIDE("/") {
        @Override
        public double compute(double num1, double num2) {
            return num1 / num2;
        }
    },
    REMAINDER("%") {
        @Override
        public double compute(double num1, double num2) {
            return num1 % num2;
        }
    },
    EXPONENTIATION("^") {
        @Override
        public double compute(double num1, double num2) {
            return Math.pow(num1, num2);
        }
    },
    INTEGER_DIVISION("//") {
        @Override
        public double compute(double num1, double num2) {
            return Math.floor(num1 / num2);
        }
    },
    OTHER("") {
        @Override
        public double compute(double num1, double num2) {
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

    public abstract double compute(double num1, double num2);
}