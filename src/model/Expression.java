package model;

import java.util.*;
import java.util.function.*;

/**
 * Represents the expression that the user is inputting. Stores the expression as a string.
 */
public class Expression {
    private StringBuilder exp;
    
    public Expression() {
        exp = new StringBuilder();
    }

    public Expression(String expression) {
        this.exp = new StringBuilder(expression);
    }

    /**
     * Adds a segment to the expression if it is a digit or an operator
     * @param seg the segment to add
     */
    public boolean add(String seg) {
        if (seg.chars().allMatch(Character::isDigit) || isOperator(seg)) {
            exp.append(seg);
            return true;
        }
        return false;
    }

    /**
     * Removes the last character from the expression
     */
    public void clear() {
        exp.setLength(0);
    }

    /**
     * Returns the expression as a string
     * @return the expression
     */
    public String toString() {
        return exp.toString();
    }

    /**
     * Checks if the expression is valid.
     * Operators cannot be at the beginning or end of the expression, and two operators cannot be next to each other.
     * @see #isOperator(String)
     * @return {@code true} if the expression is valid
     */
    public boolean isValid() {
        if (exp.length() == 0) {
            return false;
        }

        for (int i = 0; i < exp.length(); i++) {
            String str = exp.charAt(i) + "";
            if (!Character.isDigit(exp.charAt(i)) && !isOperator(str)) {
                return false;
            }
            if (i == exp.length() - 1 && isOperator(str)) {
                return false;
            }
            if (i == 0 && (str.equals("*") || str.equals("/"))) {
                return false;
            }
            if (i > 0) {
                String prev = exp.charAt(i - 1) + "";
                if (isOperator(str) && isOperator(prev)) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Splits the expression into segments that can be used to convert to postfix
     * @return the segments of the expression
     * @throws IllegalArgumentException if the expression is invalid
     * @see #isValid()
     */
    public List<String> getSegments() {
        if (!isValid()) {
            throw new IllegalArgumentException("Invalid expression");
        }

        List<String> result = new ArrayList<>();
        BiConsumer<List<String>, String> addItem = new BiConsumer<>() {
            @Override
            public void accept(List<String> list, String s2) {
                if (!s2.isEmpty()) {
                    list.add(s2);
                }
            }
        };

        int start = 0;
        for (int i = 0; i < exp.length(); i++) {
            if (isOperator(exp.charAt(i) + "")) {
                addItem.accept(result, exp.substring(start, i)); //adds numbers
                addItem.accept(result, exp.substring(i, i+1)); //adds operators
                start = i+1;
            }
            if (i == exp.length()-1) {
                result.add(exp.substring(start));
            }
        }
        return result;
    }

    /**
     * Checks if the character is an operator, helper method
     * @param segment
     * @return {@code true} if the character is an operator
     * @see #isValid()
     * @see #add(char)
     */
    public static boolean isOperator(String segment) {
        return segment.equals("+") || segment.equals("-") || segment.equals("*") || segment.equals("/");
    }
}