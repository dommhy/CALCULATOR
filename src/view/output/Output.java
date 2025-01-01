package view.output;

import ctrl.Driver;
import model.Expression;
import processing.core.PApplet;
import view.input.*;

/**
 * Handles the design of the output section of the design.
 */
public class Output {

    private PApplet p;
    private Driver driver;
    private Expression exp;
    private float textXpos;
    private float w, h;
    private static Output instance;
    
    /**
     * Creates the output section of the design
     * @param p the PApplet
     */
    private Output(PApplet p) {
        this.p = p;
        driver = Driver.getDriver(p);
        exp = driver.getExpression();
        textXpos = p.width - p.width/40;
        w = p.width;
        h = ButtonGrid.getInstance(p).getY();
    }

    /**
     * Creates the output section of the design if it does not exist, otherwise returns the existing instance
     * @param p - the PApplet object
     * @return the Output instance
     */
    public static synchronized Output getInstance(PApplet p) {
        if (instance == null) {
            instance = new Output(p);
        }
        return instance;
    }

    /**
     * Draws the background of the output section
     */
    private void drawBackground() {
        p.fill(80);
        p.noStroke();
        p.rectMode(PApplet.CORNER);
        p.rect(0, 0, w, h);
    }

    /**
     * Displays the expression and result on the screen
     */
    public void display() {
        Runnable drawText = () -> {
            Expression result = driver.getResult();
            p.fill(255);
            p.noStroke();
            p.textAlign(PApplet.RIGHT, PApplet.CENTER);
            if (result.toString().isEmpty()) {
                p.textSize(h/3);
                p.text(exp.toString(), textXpos, h/2);
            } else {
                p.textSize(h/6);
                p.text(exp.toString(), textXpos, h/3);
                p.textSize(h/3);
                p.text(result.toString(), textXpos, h/1.5f);
            }
        };
        
        drawBackground();
        drawText.run();
    }

    /**
     * Updates the expression and result from the Driver class
     * @see ctrl.Driver called in mousePressed()
     */
    public void update(Exception e) {
        driver.click(p, e);
        exp = driver.getExpression();
    }

    /**
     * Updates the expression and result from the Driver class.
     * @see ctrl.Driver
     * @implNote Called in keyPressed() in the PApplet class
     */
    public void updateText(Exception e) {
        driver.text(p, e);
        exp = driver.getExpression();
    }

    /**
     * Handles exceptions thrown by the Driver class
     * @param e the exception that was thrown
     * @see ctrl.Driver
     */
    public void errorText(Exception e) {
        Runnable drawText = () -> {
            p.fill(255);
            p.noStroke();
            p.textAlign(PApplet.RIGHT, PApplet.CENTER);
            p.textSize(h/3);
            if (e instanceof ArithmeticException) {
                p.text("Math Error", textXpos, h/2);
            } else if (e instanceof IllegalArgumentException) {
                p.text("Syntax Error", textXpos, h/2);
            } else {
                e.printStackTrace();
            }
        };

        drawBackground();
        drawText.run();
    }

}
