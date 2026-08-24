import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A Wheel is one reel of the slot machine. It holds the strip of symbols
 * that were placed on it and, after spinning, the symbol currently showing.
 * Its visual representation reuses shapes.Rectangle (the reel frame) and
 * shapes.Circle (the currently visible symbol) from the shapes project.
 *
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

    /**
     * Create a wheel that will be laid out at the given 0-based position.
     * @param index the wheel's position among the machine's wheels (0-based)
     */
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

    /**
     * Place a symbol on this wheel's strip (it may later be selected by spin).
     * @param symbol the symbol to add to the strip
     */
    public void placeSymbol(Symbol symbol){
        strip.add(symbol);
    }

    /**
     * Indicate whether this wheel has no symbols placed on it.
     * @return true if the strip is empty
     */
    public boolean isEmpty(){
        return strip.isEmpty();
    }

    /**
     * Spin this wheel: randomly choose one of its placed symbols to display.
     * Does nothing if the strip is empty.
     * @param random the shared random generator used to pick the symbol
     */
    public void spin(Random random){
        if(!strip.isEmpty()){
            current = strip.get(random.nextInt(strip.size()));
            display.changeColor(current.getColor());
        }
    }

    /**
     * Return the color of the symbol currently showing on this wheel.
     * @return the current color, or null if the wheel has never been spun
     */
    public String currentColor(){
        return (current == null) ? null : current.getColor();
    }

    /**
     * Move this wheel to a new 0-based position (used when other wheels
     * are added to or removed from the machine).
     * @param newIndex the new 0-based position
     */
    public void reposition(int newIndex){
        int dx = (newIndex - index) * SPACING;
        frame.moveHorizontal(dx);
        display.moveHorizontal(dx);
        index = newIndex;
    }

    /**
     * Make this wheel's shapes visible on the canvas.
     */
    public void makeVisible(){
        frame.makeVisible();
        display.makeVisible();
    }

    /**
     * Make this wheel's shapes invisible on the canvas.
     */
    public void makeInvisible(){
        frame.makeInvisible();
        display.makeInvisible();
    }

    /**
     * Change the frame's look to indicate whether it is part of a winning
     * configuration.
     * @param winning true to highlight this wheel as a winner
     */
    public void highlight(boolean winning){
        frame.changeColor(winning ? "gold" : "lightgray");
    }

    private int targetX(int i){
        return X_BASE + i * SPACING;
    }
}
