package ctrl;

import java.util.*;
import model.Expression;

public class Calculation {
    private Expression exp;

    public Calculation(Expression expression) {
        this.exp = expression;
    }

    /**
     * Converts the infix expression to postfix in a form of a list. Cited from GeeksforGeeks.
     * @return the postfix expression
     * @throws IllegalArgumentException if the expression is invalid
     * @see https://www.geeksforgeeks.org/convert-infix-expression-to-postfix-expression/
     */
    public List<String> postfix() throws IllegalArgumentException {
        List<String> infix = exp.getSegments();
        Stack<String> operatorStack = new Stack<>();
        List<String> result = new ArrayList<>();

        for (int i = 0; i < infix.size(); i++) {
            String seg = infix.get(i);
            if (seg.chars().allMatch(Character::isDigit)) {
                result.add(seg);
            } else { //no brackets for this calculator
                while (!operatorStack.isEmpty() && precedence(seg) <= precedence(operatorStack.peek())) {
                    result.add(operatorStack.pop());
                }
                operatorStack.push(seg);
            }
        }

        while (!operatorStack.isEmpty()) {
            result.add(operatorStack.pop());
        }

        return result;
    }

    /**
     * Returns the precedence of the operator. Cited from GeeksforGeeks.
     * @param operator the operator
     * @return the precedence of the operator
     * @see #postfix()
     * @see https://www.geeksforgeeks.org/convert-infix-expression-to-postfix-expression/
     */
    private int precedence(String operator) {
        if (operator.equals("+") || operator.equals("-")) {
            return 1;
        }
        if (operator.equals("*") || operator.equals("/")) {
            return 2;
        }
        return 0;
    }

    /**
     * Evaluates the postfix expression. Cited from GeeksforGeeks.
     * @param postfix the postfix expression
     * @return the result of the expression
     * @throws ArithmeticException if there is division by zero
     * @see https://www.geeksforgeeks.org/evaluation-of-postfix-expression/
     */
    private double evaluate(List<String> postfix) throws ArithmeticException {
        Stack<Double> operandStack = new Stack<>();
        for (int i = 0; i < postfix.size(); i++) {
            String str = postfix.get(i);
            if (str.chars().allMatch(Character::isDigit)) {
                operandStack.push(Double.parseDouble(str));
            } else {
                double operand2 = operandStack.pop();
                double operand1 = operandStack.pop();
                operandStack.push(operate(operand1, operand2, str));
            }
        }
        return operandStack.pop();
    }

    /**
     * Evaluates the infix expression
     * @return the result of the expression
     * @throws IllegalArgumentException if the expression is invalid
     * @throws ArithmeticException if there is division by zero
     * @see #postfix()
     * @see #evaluate(List)
     */
    public double evaluate() throws IllegalArgumentException, ArithmeticException {
        return evaluate(postfix());
    }

    /**
     * Performs the operation
     * @param operand1 the first operand
     * @param operand2 the second operand
     * @param operator the operator
     * @return the result of the operation
     * @throws ArithmeticException if there is division by zero
     */
    private double operate (double operand1, double operand2, String operator) throws ArithmeticException {
        if (operator.equals("+")) {
            return operand1 + operand2;
        } else if (operator.equals("-")) {
            return operand1 - operand2;
        } else if (operator.equals("*")) {
            return operand1 * operand2;
        } else if (operand2 == 0) { //guaranteed division
            throw new ArithmeticException("Division by zero");
        }
        return operand1 / operand2;
    }
    
}
