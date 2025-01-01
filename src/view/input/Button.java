package view.input;

import processing.core.*;

/**
 * Handles the design of buttons
 */
public class Button {
    private PApplet p;
    private float x, y, dia;
    private char label;
    private int color, hoverColor;
    private int textColor, textSize;

    /**
     * Constructor for Button.
     * Automatically changes detects the color of the button based on the text.
     * @param p - the PApplet object
     * @param x - x-coordinate of the button
     * @param y - y-coordinate of the button
     * @param dia - diameter of the button
     * @param text - character to be displayed on the button
     */
    public Button(PApplet p, float x, float y, float dia, char text) {
        this.p = p;
        this.x = x;
        this.y = y;
        this.dia = dia;
        this.label = text;
        if (text == 'C') {
            this.color = p.color(255, 0, 0);
            this.hoverColor = p.color(200, 0, 0);
        } else if (Character.isDigit(text)) {
            this.color = p.color(255);
            this.hoverColor = p.color(200);
        } else {
            this.color = p.color(96, 185, 223);
            this.hoverColor = p.color(105, 204, 246);
        }
        this.textColor = p.color(0);
        this.textSize = (int)dia/2;
    }

    /**
     * Fetches the text of the button.
     * @return the text of the button
     */
    public char getLabel() {
        return label;
    }

    /**
     * Displays the button.
     */
    public void display() {
        if (isHovered()) {
            p.fill(hoverColor);
        } else {
            p.fill(color);
        }
        p.strokeWeight(1);
        p.stroke(0);
        p.ellipseMode(PApplet.CENTER);
        p.ellipse(x, y, dia, dia);
        p.fill(textColor);
        p.textSize(textSize);
        p.textAlign(PApplet.CENTER);
        p.text(label, x, y + textSize/3);
    }

    /**
     * Checks if the mouse is hovering over the button.
     * @return true if the mouse is hovering over the button, false otherwise
     */
    public boolean isHovered() {
        return PApplet.dist(p.mouseX, p.mouseY, x, y) < dia / 2;
    }

    /**
     * Checks if the button is clicked.
     * @return true if the button is clicked, false otherwise
     */
    public boolean isClicked() {
        return isHovered() && p.mousePressed;
    }
}
