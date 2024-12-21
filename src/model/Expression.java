package model;

import java.util.*;
import java.util.function.*;

/**
 * Represents the expression that the user is inputting. Stores the expression as a string.
 */
public class Expression {
    private StringBuilder exp;
    
    /**
     * Constructs an expression with an empty string
     */
    public Expression() {
        exp = new StringBuilder();
    }

    /**
     * Constructs an expression with the given string
     * @param exp
     */
    public Expression(String exp) {
        this.exp = new StringBuilder(exp);
    }

    /**
     * Adds a segment to the expression if it is a digit or an operator
     * Adds a zero if the expression starts with {@code '+'} or {@code '-'}.
     * @param seg the segment to add
     * @return {@code true} if the operation is successful
     */
    public boolean add(String seg) {
        if (!seg.chars().allMatch(Character::isDigit) && !isOperator(seg)) {
            return false;
        }
        exp.append(seg);
        if (exp.toString().startsWith("+") || exp.toString().startsWith("-")) {
            exp.insert(0, "0"); //add zero to handle negative numbers being the first number
        
        }
        return true;
    }

    /**
     * Deletes the last character of the expression
     * @return {@code true} if the operation is successful
     */
    public boolean pop() {
        try {
            exp.deleteCharAt(exp.length()-1);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Clears the expression. Sets the expression to an empty string.
     */
    public void clear() {
        exp.setLength(0);
    }

    /**
     * Returns the expression as a string
     * @return string representation of the expression
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

        Predicate<String> validNonDigit = s -> (isOperator(s) || s.equals("."));

        for (int i = 0; i < exp.length(); i++) {
            String str = exp.charAt(i) + "";
            if (!Character.isDigit(exp.charAt(i)) && !validNonDigit.test(str)) {
                return false;
            }
            if (i == exp.length() - 1 && validNonDigit.test(str)) {
                return false;
            }
            if (i == 0 && (str.equals("*") || str.equals("/"))) { //check for operator at beginning
                return false;
            }
            if (i > 0) { //check for two operators next to each other
                String prev = exp.charAt(i - 1) + "";
                if (validNonDigit.test(str) && validNonDigit.test(prev)) {
                    return false;
                }
            }
            if (str.equals(".")) { //check for multiple decimal points
                for (int j = i + 1; j < exp.length() && !isOperator(exp.charAt(j) + ""); j++) {
                    if (exp.charAt(j) == '.') {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    /**
     * Splits the expression into segments that can be used to convert to postfix
     * @return the segments of the expression as a list
     * @throws IllegalArgumentException if the expression is invalid
     * @see #isValid()
     * @implNote Method is used in the {@code Driver} class before converting the expression to postfix
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
            if (isOperator(exp.charAt(i))) {
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
     * Checks if the character is an operator
     * @param segment - the string to check
     * @return {@code true} if the character is an operator
     * @see #isValid()
     * @see #add(char)
     */
    public static boolean isOperator(String segment) {
        return segment.equals("+") || segment.equals("-") ||
               segment.equals("*") || segment.equals("/");
    }

    /**
     * Checks if the character is an operator, helper method
     * @param c - the character to check
     * @return {@code true} if the character is an operator 
     * @see #isOperator(String)
     */
    private static boolean isOperator(char c) {
        return isOperator(c + "");
    }
}