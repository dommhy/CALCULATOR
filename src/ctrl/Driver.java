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
    private Expression expression;

    private Driver(PApplet p) {
        buttons = ButtonGrid.getButtonGrid(p).getButtons();
        expression = new Expression();
    }

    public static synchronized Driver getDriver(PApplet p) {
        if (instance == null) {
            instance = new Driver(p);
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

    public Exception resetException(Exception e) {
        return null;
    }

    /**
     * Formats the result to be displayed on the screen
     * @param d
     * @return
     */
    private String formatResult(double d) {
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
