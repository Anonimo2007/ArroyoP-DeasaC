import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * SlotMachine simulates a slot machine (inspired by "Problem I - Slot
 * Machine", ICPC 2025) that can have any number of wheels, each holding
 * any number of colored symbols. This class only simulates the machine;
 * it does not solve the marathon problem.
 *
 * @author  Gian Franco Arroyo Perez, Nicolas Deaza Casasbuenas
 * @version 1.0
 */
public class SlotMachine{

    private List<Wheel> wheels;
    private List<Symbol> catalog;
    private Rectangle body;
    private Random random;
    private boolean visible;
    private boolean everVisible;
    private boolean ok;

    /**
     * Create a slot machine with no wheels and no symbols, invisible.
     */
    public SlotMachine(){
        wheels = new ArrayList<Wheel>();
        catalog = new ArrayList<Symbol>();
        random = new Random();
        visible = false;
        ok = true;
        body = new Rectangle();
        body.moveHorizontal(10 - 70);
        body.moveVertical(30 - 15);
        body.changeColor("lightsteelblue");
        body.changeSize(200, bodyWidth());
    }

    /**
     * Add a new (empty) wheel at the given position.
     * @param pos 1-based position; clamped to [1, number of wheels + 1]
     */
    public void addWheel(int pos){
        int p = clamp(pos, wheels.size() + 1);
        Wheel w = new Wheel(p - 1);
        wheels.add(p - 1, w);
        relayout();
        if(visible) w.makeVisible();
        body.changeSize(200, bodyWidth());
        ok = true;
    }

    /**
     * Remove the wheel at the given position.
     * @param pos 1-based position; clamped to [1, number of wheels]
     */
    public void delWheel(int pos){
        if(wheels.isEmpty()){
            fail("No hay ruedas para eliminar.");
            return;
        }
        int p = clamp(pos, wheels.size());
        Wheel w = wheels.remove(p - 1);
        w.makeInvisible();
        relayout();
        body.changeSize(200, bodyWidth());
        ok = true;
    }

    /**
     * Add a new symbol (identified by a CSS color name) to the machine's
     * catalog of available symbols.
     * @param pos   1-based position; clamped to [1, catalog size + 1]
     * @param color a standard CSS3 color name
     */
    public void addSymbol(int pos, String color){
        if(!CssColors.isValid(color)){
            fail("\"" + color + "\" no es un nombre de color CSS valido.");
            return;
        }
        int p = clamp(pos, catalog.size() + 1);
        catalog.add(p - 1, new Symbol(color));
        ok = true;
    }

    /**
     * Remove a symbol from the catalog by its color.
     * @param symbol the CSS color name identifying the symbol to remove
     */
    public void delSymbol(String symbol){
        int idx = indexOfColor(symbol);
        if(idx < 0){
            fail("El simbolo \"" + symbol + "\" no existe en el catalogo.");
            return;
        }
        catalog.remove(idx);
        ok = true;
    }

    /**
     * Place a catalog symbol onto a wheel's strip.
     * @param wheel  1-based wheel position; clamped to [1, number of wheels]
     * @param symbol the CSS color name of a symbol already in the catalog
     */
    public void placeSymbol(int wheel, String symbol){
        if(wheels.isEmpty()){
            fail("La maquina no tiene ruedas.");
            return;
        }
        if(indexOfColor(symbol) < 0){
            fail("El simbolo \"" + symbol + "\" no esta en el catalogo.");
            return;
        }
        int w = clamp(wheel, wheels.size());
        wheels.get(w - 1).placeSymbol(new Symbol(symbol));
        ok = true;
    }

    /**
     * Spin a single wheel, randomly choosing one of its placed symbols.
     * @param wheel 1-based wheel position; clamped to [1, number of wheels]
     */
    public void spin(int wheel){
        if(wheels.isEmpty()){
            fail("La maquina no tiene ruedas.");
            return;
        }
        int w = clamp(wheel, wheels.size());
        Wheel wh = wheels.get(w - 1);
        if(wh.isEmpty()){
            fail("La rueda " + w + " no tiene simbolos.");
            return;
        }
        wh.spin(random);
        ok = true;
        updateJackpotLook();
    }

    /**
     * Spin every wheel that currently has at least one symbol placed.
     */
    public void spin(){
        if(wheels.isEmpty()){
            fail("La maquina no tiene ruedas.");
            return;
        }
        boolean any = false;
        for(Wheel w : wheels){
            if(!w.isEmpty()){
                w.spin(random);
                any = true;
            }
        }
        if(!any){
            fail("Ninguna rueda tiene simbolos para girar.");
            return;
        }
        ok = true;
        updateJackpotLook();
    }

    /**
     * Return the colors of the symbols in the catalog, in position order.
     * @return an array with one color per catalog symbol, starting at position 1
     */
    public String[] symbols(){
        String[] result = new String[catalog.size()];
        for(int i = 0; i < catalog.size(); i++){
            result[i] = catalog.get(i).getColor();
        }
        ok = true;
        return result;
    }

    /**
     * Count how many distinct colors exist in the symbol catalog.
     * @return the number of different colors among the catalog symbols
     */
    public int distinctSymbols(){
        Set<String> distinct = new HashSet<String>();
        for(Symbol s : catalog){
            distinct.add(s.getColor());
        }
        ok = true;
        return distinct.size();
    }

    /**
     * Return the colors currently visible on each wheel, left to right.
     * @return an array with one entry per wheel (null if that wheel has
     *         never been spun)
     */
    public String[] configuration(){
        String[] result = new String[wheels.size()];
        for(int i = 0; i < wheels.size(); i++){
            result[i] = wheels.get(i).currentColor();
        }
        ok = true;
        return result;
    }

    /**
     * Indicate whether the current configuration is a winning one, i.e.
     * every wheel has been spun and all of them show the same color.
     * @return true if the current configuration is the jackpot
     */
    public boolean isJackpot(){
        if(wheels.isEmpty()) return false;
        String first = wheels.get(0).currentColor();
        if(first == null) return false;
        for(Wheel w : wheels){
            if(!first.equals(w.currentColor())) return false;
        }
        return true;
    }

    /**
     * Make the whole simulator (machine body and wheels) visible.
     */
    public void makeVisible(){
        visible = true;
        everVisible = true;
        body.makeVisible();
        for(Wheel w : wheels) w.makeVisible();
        ok = true;
    }

    /**
     * Make the whole simulator invisible. The machine keeps working (all
     * other operations remain valid) while invisible.
     */
    public void makeInvisible(){
        visible = false;
        body.makeInvisible();
        for(Wheel w : wheels) w.makeInvisible();
        ok = true;
    }

    /**
     * Terminate the simulator, hiding its window (if it was ever shown).
     */
    public void exit(){
        makeInvisible();
        if(everVisible){
            Canvas.getCanvas().setVisible(false);
        }
        ok = true;
    }

    /**
     * Indicate whether the last operation performed on this machine
     * completed successfully.
     * @return true if the last operation succeeded
     */
    public boolean ok(){
        return ok;
    }

    private void relayout(){
        for(int i = 0; i < wheels.size(); i++){
            wheels.get(i).reposition(i);
        }
    }

    private void updateJackpotLook(){
        boolean win = isJackpot();
        body.changeColor(win ? "gold" : "lightsteelblue");
        for(Wheel w : wheels) w.highlight(win);
    }

    private int indexOfColor(String color){
        for(int i = 0; i < catalog.size(); i++){
            if(catalog.get(i).getColor().equals(color)) return i;
        }
        return -1;
    }

    private int bodyWidth(){
        return 20 + Math.max(1, wheels.size()) * 90;
    }

    private int clamp(int pos, int max){
        if(pos < 1) return 1;
        if(pos > max) return max;
        return pos;
    }

    private void fail(String message){
        ok = false;
        if(visible){
            JOptionPane.showMessageDialog(null, message, "slotMachine",
                JOptionPane.WARNING_MESSAGE);
        }
    }
}
