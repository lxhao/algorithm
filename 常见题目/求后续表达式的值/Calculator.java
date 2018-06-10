package 求后续表达式的值;

import java.util.ArrayDeque;
import java.util.Scanner;

public class Calculator {

    /**
     * @throws IllegalArgumentException
     */
    public void runCalculator() throws IllegalArgumentException {
        Scanner input = new Scanner(System.in);
        ArrayDeque<Double> deque = new ArrayDeque<>();
        System.out.println("please input a ostfix expression : ");
        while (input.hasNext()) {
            //read line each times
            String line = input.nextLine();
            if (isQuit(line)) {
                break;
            }
            //split a line input by space
            String[] expression = line.split("\\s");
            try {
                calculator(expression, deque);
            } catch (IllegalArgumentException e) {
                throw e;
            }
            if (deque.size() != 1) {
                throw new IllegalArgumentException("too many numbers in the expression");
            }
            deque.clear();
            System.out.println("please input a ostfix expression : ");
        }
    }

    /**
     * compute result of provided expression
     *
     * @param postFixExpression numbers and operators
     * @param deque             save expression
     * @throws IllegalArgumentException
     */
    private void calculator(String[] postFixExpression, ArrayDeque<Double> deque) throws IllegalArgumentException {
        double number1, number2, tmpResult;
        Operator operator;
        for (String item : postFixExpression) {
            operator = Operator.getOperator(item);
            if (operator != Operator.OTHER) {
                if (deque.size() < 2) {
                    throw new IllegalArgumentException("not enuf numbers in the expression");
                }
                number2 = deque.pop();
                number1 = deque.pop();
                tmpResult = operator.compute(number1, number2);
                deque.push(tmpResult);
                System.out.printf("%s %s %s = %s\n", number1, item, number2, tmpResult);
                continue;
            }
            try {
                Double number = Double.valueOf(item);
                deque.push(number);
            } catch (Exception e) {
                throw new IllegalArgumentException("Error: Unknown operator: " + item);
            }
        }
    }

    private boolean isQuit(String input) {
        return input.toLowerCase().equals("quit");
    }

}
