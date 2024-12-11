package view.output;

import ctrl.Driver;
import model.Expression;
import processing.core.PApplet;

/**
 * Handles the design of the output section of the design.
 */
public class Output {

    private PApplet p;
    private Expression expression;
    private String result;
    private float textXpos;
    
    /**
     * Creates the output section of the design
     * @param p the PApplet
     */
    public Output(PApplet p) {
        this.p = p;
        expression = Driver.getDriver(p).getExpression();
        result = Driver.getDriver(p).click();
        textXpos = p.width - p.width/40;
    }

    /**
     * Displays the expression and result on the screen
     */
    public void display() {
        p.fill(255);
        p.noStroke();
        p.textAlign(PApplet.RIGHT);
        if (result.isEmpty()) {
            p.textSize(p.height/10);
            p.text(expression.toString(), textXpos, p.height/4);
        } else {
            p.textSize(p.height/20);
            p.text(expression.toString(), textXpos, p.height/8);
            p.textSize(p.height/10);
            p.text(result, textXpos, p.height/4);
        }
            
    }

    /**
     * Updates the expression and result from the Driver class
     * @see ctrl.Driver
     */
    public void update() {
        result = Driver.getDriver(p).click();
        expression = Driver.getDriver(p).getExpression();
    }

    /**
     * Handles exceptions thrown by the Driver class
     * @param e the exception that was thrown
     * @see ctrl.Driver
     */
    public void errorText(Exception e) {
        p.fill(255);
        p.noStroke();
        p.textAlign(PApplet.RIGHT);
        p.textSize(p.height/10);
        if (e instanceof ArithmeticException) {
            p.text("Math Error", textXpos, p.height/4);
        } else if (e instanceof IllegalArgumentException) {
            p.text("Syntax Error", textXpos, p.height/4);
        } else {
            e.printStackTrace();
        }
    }

}
