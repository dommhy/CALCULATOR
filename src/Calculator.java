import processing.core.PApplet;
import view.input.*;
import view.output.*;

public class Calculator extends PApplet {

    public static void main(String[] args) {
        PApplet.main("Calculator");
    }
    
    ButtonGrid input;
    Output output;
    Exception e; //variable to handle arithmetic and syntax errors

    public void settings() {
        size(500, 600);
    }

    public void setup() {
        input = ButtonGrid.getInstance(this);
        output = new Output(this);
        e = null;
    }

    public void draw() {
        input.display();
        if (e != null) {
            output.errorText(e);
        } else {
            output.display();
        }
    }

    public void mousePressed() {
        try {
            output.update();
        } catch (Exception e) {
            this.e = e;
        }
        if (e != null && input.getCancelButton().isClicked()) { //reset exception
            e = null;
        }
    }

    public void keyPressed() {
        try {
            output.updateText();
        } catch (Exception e) {
            this.e = e;
        }
        if (e != null && key == BACKSPACE) {
            e = null;
        }
    }
}