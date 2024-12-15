package ctrl;

import model.Expression;
import view.input.*;
import processing.core.*;

/**
 * Class that handles the event when buttons are clicked
 */
public class ButtonDriver {

    private static ButtonDriver instance;
    private Button[][] buttons;
    private Expression expression;

    private ButtonDriver(PApplet p) {
        buttons = ButtonGrid.getButtonGrid(p).getButtons();
        expression = new Expression();
    }

    public static synchronized ButtonDriver getDriver(PApplet p) {
        if (instance == null) {
            instance = new ButtonDriver(p);
        }
        return instance;
    }

    /**
     * Handles the event when buttons are clicked. Returns the result of the calculation, applicable to the button '='
     * @return the result of the calculation. If the calculation is not done, return an empty string
     */
    public String click() {
        String result = "";
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                if (buttons[i][j].isClicked()) {
                    char c = buttons[i][j].getLabel();
                    if (c == 'C') {
                        expression.clear();
                    } else if (c == '=') {
                        expression.add("=");
                        Calculation calc = new Calculation(expression);
                        result = formatResult(calc.evaluate());
                    } else {
                        expression.add(c + "");
                    }
                }
            }
        }
        return result;
    }

    /**
     * Resets the exception when the cancel button is clicked
     * @param e the exception to reset
     * @param p the PApplet object, for code to work
     * @return {@code null}
     */
    public Exception resetException(Exception e, PApplet p) {
        if (ButtonGrid.getButtonGrid(p).getCancelButton().isClicked()) { //reset exception
            e = null;
        }
        return null;
    }

    /**
     * Formats the result to be displayed on the screen
     * @param d
     * @return
     */
    static String formatResult(double d) {
        if (d % 1 == 0) {
            return (int) d + "";
        }
        if ((d + "").length() > 9) {
            d = Math.round(d * 1000000000.0) / 1000000000.0;
        }
        return d + "";
    }

    public Expression getExpression() {
        return expression;
    }
}
