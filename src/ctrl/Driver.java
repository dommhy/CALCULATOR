package ctrl;

import model.Expression;
import view.input.*;
import processing.core.*;

/**
 * Class that handles the event when buttons are clicked
 */
public class Driver {

    private static Driver instance;
    private Button[][] buttons;
    private Expression expression, result;

    private Driver(PApplet p) {
        buttons = ButtonGrid.getButtonGrid(p).getButtons();
        expression = new Expression();
        result = new Expression();
    }

    public static synchronized Driver getDriver(PApplet p) {
        if (instance == null) {
            instance = new Driver(p);
        }
        return instance;
    }

    /**
     * Handles the event when buttons are clicked. Returns the result of the calculation, applicable to the button '='
     * @implNote The method is called when the mouse is pressed
     * @return the result of the calculation. If the calculation is not done, return an empty string
     */
    public Expression click() {
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                if (buttons[i][j].isClicked()) {
                    char c = buttons[i][j].getLabel();
                    if (c == 'C') {
                        expression.clear();
                        result.clear();
                    } else if (c == '=') {
                        expression.add("=");
                        Calculation calc = new Calculation(expression);
                        result = formatResult(calc.evaluate());
                    } else {
                        if (Expression.isOperator(c + "") && !result.toString().isEmpty()) {
                            updateExpression();
                        }
                        result.clear();
                        expression.add(c + "");
                    }
                }
            }
        }
        return result;
    }

    /**
     * Handles the event when keys are pressed. Returns the result of the calculation.
     * @param p the PApplet object, to access key and keyCode variables for code to work
     * @implNote The method is called when the key is pressed {@link processing.core.PApplet#keyPressed()}
     * @return the result of the calculation. If the calculation is not done, return an empty string
     */
    public Expression text(PApplet p) {
        char c = p.key;
        if (c == 'C' || c == 'c') {
            expression.clear();
            result.clear();
        } else if (c == '=' || c == PApplet.ENTER || c == PApplet.RETURN) {
            expression.add("=");
            Calculation calc = new Calculation(expression);
            result = formatResult(calc.evaluate());
        } else if (c == PApplet.BACKSPACE) {
            expression.pop();
        } else if (Expression.isOperator(c + "") && !result.toString().isEmpty()) {
            updateExpression();
            expression.add(c + "");
        } else if (Character.isDigit(c) || Expression.isOperator(c + "")) {
            result.clear();
            expression.add(c + "");
        }
        return result;
    }

    /**
     * Resets the exception
     * @param e the exception to reset
     * @param p the PApplet object, for code to work
     * @implNote Not used in the current implementation
     * @return {@code null}
     */
    public static Exception resetException(Exception e, PApplet p) {
        e = null;
        return null;
    }

    /**
     * Rounds the result to 9 decimal places if the result is a decimal number
     * @param d
     * @return the formatted result
     */
    private static Expression formatResult(double d) {
        if (d % 1 == 0) {
            return new Expression((long) d + "");
        }
        if ((d + "").length() > 9) {
            d = Math.round(d * 1000000000.0) / 1000000000.0;
        }
        return new Expression (d + "");
    }

    /**
     * Returns the current expression
     * @return the expression
     */
    public Expression getExpression() {
        return expression;
    }

    /**
     * Returns the result of the calculation
     * @return the result
     */
    public Expression getResult() {
        return result;
    }

    /**
     * Sets the expression to the result of the calculation and resets the result
     * @param expression the expression to set
     * @implNote Used when button is clicked and result is not empty
     * @see #click()
     */
    private void updateExpression() {
        expression = result;
        result = new Expression();
    }
}
