import processing.core.PApplet;
import view.input.*;
import view.output.*;

public class Calculator extends PApplet {

    public static void main(String[] args) {
        PApplet.main("Calculator");
    }
    
    ButtonGrid input;
    Output output;
    Exception e = null; //variable to handle arithmetic and syntax errors

    public void settings() {
        size(400, 600);
    }

    public void setup() {
        input = ButtonGrid.getButtonGrid(this);
        output = new Output(this);
    }

    public void draw() {
        background(80);
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
        if (input.getCancelButton().isClicked()) { //reset exception
            e = null;
        }
    }

    public void keyPressed() {
        output.updateText();
    }
}