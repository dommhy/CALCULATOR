package ctrl;

import model.Expression;
import view.input.*;
import processing.core.*;
import static model.Expression.*;

/**
 * Class that handles the event when buttons are clicked
 */
public class Driver {

    private static Driver instance;
    private Button[][] buttons;
    private Expression expression, result;

    private Driver(PApplet p) {
        buttons = ButtonGrid.getInstance(p).getButtons();
        expression = new Expression();
        result = new Expression();
    }

    /**
     * Returns the instance of the Driver class. If the instance is null, creates a new instance
     * @param p - the PApplet object, to access the buttons
     * @return the instance of the Driver class {@link #Driver(PApplet)}
     */
    public static synchronized Driver getDriver(PApplet p) {
        if (instance == null) {
            instance = new Driver(p);
        }
        return instance;
    }

    /**
     * Handles the event when buttons are clicked. Returns the result of the calculation, applicable to the button '='
     * @return the result of the calculation. If the calculation is not done, return an empty string
     * @implNote The method is called when the mouse is pressed
     * @see 
     */
    public Expression click(PApplet p, Exception e) {
        if (ButtonGrid.getInstance(p).getCancelButton().isClicked()) {
            expression.clear();
            result.clear();
        }
        if (e == null) {
            for (int i = 0; i < buttons.length; i++) {
                for (int j = 0; j < buttons[i].length; j++) {
                    if (buttons[i][j].isClicked()) {
                        char c = buttons[i][j].getLabel();
                        if (c == '=') {
                            Calculation calc = new Calculation(expression);
                            result = formatResult(calc.evaluate());
                        } else {
                            if (Expression.isOperator(c) && !result.toString().isEmpty()) {
                                updateExpression();
                            }
                            result.clear();
                            expression.add(c);
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * Handles the event when keys are pressed. Returns the result of the calculation.
     * @param p - the PApplet object, to access key and keyCode variables for code to work 
     * @return the result of the calculation. If the calculation is not done, return an empty string
     * @implNote The method is called when the key is pressed {@link processing.core.PApplet#keyPressed()}
     */
    public Expression text(PApplet p, Exception e) {
        char c = p.key;
        if (c == 'C' || c == 'c') {
            expression.clear();
            result.clear();
        }
        if (c == PApplet.BACKSPACE) {
            result.clear();
            expression.pop();
        }
        if (e == null) {
            if (c == '=' || c == PApplet.ENTER || c == PApplet.RETURN) {
                expression.add("=");
                Calculation calc = new Calculation(expression);
                result = formatResult(calc.evaluate());
            }
            
            if (Expression.isOperator(c) && !result.toString().isEmpty()) {
                updateExpression();
                expression.add(c);
            } else if (validChar(c)) {
                result.clear();
                expression.add(c);
            }
        }
        return result;
    }

    /**
     * Rounds the result to 9 decimal places if the result is a decimal number
     * @param d - the double value to be formatted
     * @return the formatted result as an Expression object
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
     * Gets the current expression
     * @return the expression
     * @implNote Used in the Output class to display the expression
     * @see view.output.Output#display()
     */
    public Expression getExpression() {
        return expression;
    }

    /**
     * Gets the result of the calculation
     * @return the result
     * @implNote Used in the Output class to display the result
     * @see view.output.Output#display()
     */
    public Expression getResult() {
        return result;
    }

    /**
     * Sets the expression to the result of the calculation and resets the result
     * @param expression the expression to set
     * @implNote Used when button is clicked and result is not empty
     * @see #click()
     * @see #text(PApplet)
     */
    private void updateExpression() {
        expression = result;
        result = new Expression();
    }
}
