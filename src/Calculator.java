import processing.core.PApplet;
import view.input.*;
import view.output.*;

public class Calculator extends PApplet {

    public static void main(String[] args) {
        PApplet.main("Calculator");
    }
    
    ButtonGrid inputDisplay;
    Output outputDisplay;
    Exception e = null; //variable to handle arithmetic and syntax errors

    public void settings() {
        size(400, 600);
    }

    public void setup() {
        inputDisplay = ButtonGrid.getButtonGrid(this);
        outputDisplay = new Output(this);
    }

    public void draw() {
        background(80);
        inputDisplay.display();
        if (e != null) {
            outputDisplay.errorText(e);
        } else {
            outputDisplay.display();
        }
    }

    public void mousePressed() {
        try {
            outputDisplay.update();
        } catch (Exception e) {
            this.e = e;
        }
        if (inputDisplay.getCancelButton().isClicked()) { //reset exception
            e = null;
        }
    }
}