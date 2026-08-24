import java.awt.*;

/**
 * A triangle that can be manipulated and that draws itself on a canvas.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 1.0  (15 July 2000)
 */

public class Triangle{
    
    public static int VERTICES=3;
    
    private int height;
    private int width;
    private int xPosition;
    private int yPosition;
    private String color;
    private boolean isVisible;


    public Triangle(){
        height = 30;
        width = 40;
        xPosition = 140;
        yPosition = 15;
        color = "green";
        isVisible = false;
    }


    public void makeVisible(){
        isVisible = true;
        draw();
    }
    

    public void makeInvisible(){
        erase();
        isVisible = false;
    }
    

    public void moveRight(){
        moveHorizontal(20);
    }


    public void moveLeft(){
        moveHorizontal(-20);
    }


    public void moveUp(){
        moveVertical(-20);
    }


    public void moveDown(){
        moveVertical(20);
    }


    public void moveHorizontal(int distance){
        erase();
        xPosition += distance;
        draw();
    }


    public void moveVertical(int distance){
        erase();
        yPosition += distance;
        draw();
    }


    public void slowMoveHorizontal(int distance){
        int delta;

        if(distance < 0) {
            delta = -1;
            distance = -distance;
        } else {
            delta = 1;
        }

        for(int i = 0; i < distance; i++){
            xPosition += delta;
            draw();
        }
    }


    public void slowMoveVertical(int distance){
        int delta;

        if(distance < 0) {
            delta = -1;
            distance = -distance;
        } else {
            delta = 1;
        }

        for(int i = 0; i < distance; i++){
            yPosition += delta;
            draw();
        }
    }


    public void changeSize(int newHeight, int newWidth) {
        erase();
        height = newHeight;
        width = newWidth;
        draw();
    }
    

    public void changeColor(String newColor){
        color = newColor;
        draw();
    }


    private void draw(){
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            int[] xpoints = { xPosition, xPosition + (width/2), xPosition - (width/2) };
            int[] ypoints = { yPosition, yPosition + height, yPosition + height };
            canvas.draw(this, color, new Polygon(xpoints, ypoints, 3));
            canvas.wait(10);
        }
    }


    private void erase(){
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.erase(this);
        }
    }
}
