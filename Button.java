/* Button Class - Used to add a button into a Game
 * Author: Joel Bianchi
 * Last Edit: 5/8/25
 * Updated to Java version
 * TODO: Add default colors for all constructed Buttons
 */

import processing.core.PApplet;
import processing.core.PFont;


public class Button {

    PApplet p;

    //------------------ BUTTON FIELDS --------------------//
    private String shape;
    private float shapeX, shapeY;     //coordinates of CENTER of button shape
    private float shapeW, shapeH;     //size of shape in pixels
    private float shapeRounding;
    private String text;
    private int textColor;
    private String fontStyle;           //file path to system or saved font file
    private PFont font;                 //font object
    private float fontFactor;
    private float fontSize;
    private int baseColor;
    private int hoverColor;
    private int clickColor;
    private int currentColor;
    private int outlineColor;
    private boolean isVisible;
    private boolean doesHoverHighlight;
    private boolean doesClickHighlight;


    //------------------ BUTTON CONSTRUCTORS --------------------//

    //Button Constructor #1
    public Button(PApplet p, String shape, float x, float y, float w, float h, String txt) {

        this.p = p;
        //super(null,1.0, x, y,false);  //if Button is Sprite
        this.shape = shape;
        this.shapeW = w;
        this.shapeH = h;
        this.shapeX = x + (shapeW/2);
        this.shapeY = y + (shapeH/2);
        this.shapeRounding = 24;
        this.text = txt;
        this.textColor = Color.BLACK;
        this.fontFactor = 0.9f;
        this.fontSize = shapeH/2 * fontFactor;
        this.baseColor = Color.YELLOW;
        this.doesHoverHighlight = true;
        this.hoverColor = Color.BLUE;
        this.doesClickHighlight = true;
        this.clickColor = Color.RED;
        this.currentColor = baseColor;
        this.outlineColor = Color.BLACK;
        this.isVisible = true;
        this.font = p.createFont("Helvetica", fontSize); //"Helvetica", "Georgia"
    }


    //------------------ BUTTON METHODS --------------------//

    //Button method to be called each cycle -- ie. inside draw() or updateScreen() 
    public void show() {
        
        //Sets outline stroke around button (3 pixels, BLACK)
        p.strokeWeight(2);
        p.stroke(outlineColor);

        //Sets color of button based on Mouse hover
        if (doesClickHighlight && isClicked()) {
            currentColor = clickColor;
        } else if (doesHoverHighlight && isMouseOverButton()){
            currentColor = hoverColor;
        } else {
            currentColor = baseColor;
        }

        //Set color inside Button
        p.fill(currentColor);

        //Only show the button if visible
        if(isVisible){

            //Draws particular Button Shape
            if(shape.equals("circle")){
                p.ellipseMode(p.CENTER);
                p.ellipse(shapeX, shapeY, shapeW, shapeH);
            //     System.out.println("circle shape");
            } else if(shape.equals("rect")){
                p.rectMode(p.CENTER);
                p.rect(shapeX, shapeY, shapeW, shapeH, shapeRounding, shapeRounding, shapeRounding, shapeRounding);
                // System.out.println("rect shape");
            } else {
                System.out.println("Wrong shape String.  Type \"rect\" or \"circle\"");
                return;
            }

            //Set Text inside Button
            p.textAlign(p.CENTER, p.CENTER);
            p.fill(textColor);
            float fontSize = shapeH/2 * fontFactor;
            p.textSize(fontSize);
            p.textFont(font);
            p.text(text, shapeX, shapeY);

        }
    }


    //------------------ BUTTON HOVERING METHODS --------------------//

    public boolean isClicked(){
        if (isMouseOverButton() && p.mousePressed) {
            System.out.println("Button Clicked");
            return true;
        } else{
            return false;
        }
    }
    
    public boolean isMouseOverButton(){ //move to Sprite class eventually
        if(shape.equals("rect")){
            return isOverRect();
        } else if(shape.equals("circle")){
            return isOverCircle();
        } else {
            return false;
        }
    }
    
    private boolean isOverRect(){
        if(p.mouseX >= shapeX-shapeW/2 && p.mouseX <= shapeX+shapeW/2
            && p.mouseY >= shapeY-shapeH/2 && p.mouseY <= shapeY+shapeH/2){
            return true;
        } else {
            return false;
        }
    }

    private boolean isOverCircle(){
        float diameter = shapeH;
        float disX = shapeX - p.mouseX;
        float disY = shapeY - p.mouseY;
        if (p.sqrt(p.sq(disX) + p.sq(disY)) < diameter/2 ) {
            return true;
        } else {
            return false;
        }
    }

//------------------ BUTTON MUTATOR METHODS --------------------//

    public void setText(String text){
        this.text = text;
    }
    public void setTextColor(int c){
        this.textColor = c;
    }
    public void setFontStyle(String fontStyleFile){
        this.fontStyle = fontStyleFile;
        this.font = p.createFont(fontStyleFile, fontSize);
    }
    public void setFontFactor(float ff){
        this.fontFactor = ff;
    }
    public void setButtonColor(int c){
        this.baseColor = c;
    }
    public void setOutlineColor(int c){
        this.outlineColor = c;
    }
    public void setHoverHighlight(boolean b){
        this.doesHoverHighlight = b;
    }

    //Method to pass in a Processing color or null
    //ie. color(0,0,255) for blue
    public void setHoverColor(Integer hoverColor){
        if(hoverColor != null){
            setHoverHighlight(true);
            this.hoverColor = hoverColor;
        } else {
            setHoverHighlight(false);
        }
    }

    public void setClickHighlight(boolean b){
        this.doesClickHighlight = b;
    }

    //Method to pass in a Processing color or null
    //ie. color(0,0,255) for blue
    public void setClickColor(Integer clickColor){
        if(clickColor != null){
            setClickHighlight(true);
            this.clickColor = clickColor;
        } else {
            setClickHighlight(false);
        }
    }
    public void setVisible(boolean b){
        this.isVisible = b;
    }


    public void setShapeRounding(float shapeRounding){
        this.shapeRounding =  shapeRounding;
    }

    public String toString(){
        return "Button shape " + this.shape + " with text \"" + this.text + "\" @loc " + this.shapeX +","+this.shapeY + " w:"+this.shapeW+" h:"+this.shapeH;
    }



} //end Button class
