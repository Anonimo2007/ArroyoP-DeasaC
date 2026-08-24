import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author  Gian Franco Arroyo Perez, Nicolas Deaza Casasbuenas
 * @version 1.0
 */
public class Wheel{

    private static final int X_BASE = 30;
    private static final int Y_BASE = 60;
    private static final int SPACING = 90;
    private static final int FRAME_W = 70;
    private static final int FRAME_H = 150;
    private static final int CIRCLE_D = 50;

    private List<Symbol> strip;
    private Symbol current;
    private Rectangle frame;
    private Circle display;
    private int index;


    public Wheel(int index){
        strip = new ArrayList<Symbol>();
        this.index = index;
        frame = new Rectangle();
        frame.changeSize(FRAME_H, FRAME_W);
        frame.moveHorizontal(targetX(index) - 70);
        frame.moveVertical(Y_BASE - 15);
        frame.changeColor("lightgray");
        display = new Circle();
        display.changeSize(CIRCLE_D);
        display.moveHorizontal(targetX(index) + (FRAME_W - CIRCLE_D) / 2 - 20);
        display.moveVertical(Y_BASE + (FRAME_H - CIRCLE_D) / 2 - 15);
        display.changeColor("white");
    }


    public void placeSymbol(Symbol symbol){
        strip.add(symbol);
    }


    public boolean isEmpty(){
        return strip.isEmpty();
    }


    public void spin(Random random){
        if(!strip.isEmpty()){
            current = strip.get(random.nextInt(strip.size()));
            display.changeColor(current.getColor());
        }
    }


    public String currentColor(){
        return (current == null) ? null : current.getColor();
    }


    public void reposition(int newIndex){
        int dx = (newIndex - index) * SPACING;
        frame.moveHorizontal(dx);
        display.moveHorizontal(dx);
        index = newIndex;
    }


    public void makeVisible(){
        frame.makeVisible();
        display.makeVisible();
    }


    public void makeInvisible(){
        frame.makeInvisible();
        display.makeInvisible();
    }


    public void highlight(boolean winning){
        frame.changeColor(winning ? "gold" : "lightgray");
    }

    private int targetX(int i){
        return X_BASE + i * SPACING;
    }
}
