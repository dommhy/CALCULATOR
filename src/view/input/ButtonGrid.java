package view.input;

import processing.core.*;

/**
 * Handles the design of the input section of the design.
 */
public class ButtonGrid {

    private static ButtonGrid instance;
    private PApplet p;
    private float dist;
    private int bgColor = 100;
    private Button[][] buttons;
    private static final char[][] buttonLabels = {
        {'1', '2', '3', '+', '^'},
        {'4', '5', '6', '-', '('},
        {'7', '8', '9', '*', ')'},
        {'C', '0', '=', '/', '.'}
    };
    
    /**
     * Constructor for ButtonGrid.
     * @param p - the PApplet object
     */
    private ButtonGrid(PApplet p) {
        this.p = p;
        buttons = new Button[4][5];
        dist = p.width/5;
        float dia = dist - p.width/20;
        float x = dist/2;
        float y = getY();
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                buttons[i][j] = new Button(p, x, y + dist/2, dia, buttonLabels[i][j]);
                x += dist;
            }
            x %= p.width;
            y += dist;
        }
    }

    /**
     * Creates the grid if it does not exist, otherwise returns the existing grid. 
     * @param p - the PApplet object
     * @return the ButtonGrid instance
     */
    public static synchronized ButtonGrid getInstance(PApplet p) {
        if (instance == null) {
            instance = new ButtonGrid(p);
        }
        return instance;
    }

    /**
     * Initializes the background of the input section.
     */
    private void initBackground() {
        p.fill(p.color(bgColor));
        p.noStroke();
        p.rectMode(PApplet.CORNER);
        p.rect(0, getY(), p.width, p.height);
    }

    /**
     * Displays the design of the input section.
     */
    public void display() {
        initBackground();
        for (Button[] row : buttons) {
            for (Button button : row) {
                button.display();
            }
        }
    }

    /**
     * Allows for the buttons to be accessed.
     * @return the buttons in the grid as a 2D array
     */
    public Button[][] getButtons() {
        return buttons;
    }

    /**
     * Gets the top left y-coordinate of the grid.
     * @return the y-coordinate of the grid
     */
    public float getY() {
        return p.height - 4*dist;
    }

    /**
     * Gets the cancel button.
     * @return the cancel button
     */
    public Button getCancelButton() {
        return buttons[3][0];
    }
}
