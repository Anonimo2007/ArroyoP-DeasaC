import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * @author  Gian Franco Arroyo Perez, Nicolas Deaza Casasbuenas
 */
public class SlotMachine{

    private List<Wheel> wheels;
    private List<Symbol> catalog;
    private Rectangle body;
    private Random random;
    private boolean visible;
    private boolean everVisible;
    private boolean ok;

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

    public void addWheel(int pos){
        int p = clamp(pos, wheels.size() + 1);
        Wheel w = new Wheel(p - 1);
        wheels.add(p - 1, w);
        relayout();
        if(visible) w.makeVisible();
        body.changeSize(200, bodyWidth());
        ok = true;
    }

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

    public void addSymbol(int pos, String color){
        if(!CssColors.isValid(color)){
            fail("\"" + color + "\" no es un nombre de color CSS valido.");
            return;
        }
        int p = clamp(pos, catalog.size() + 1);
        catalog.add(p - 1, new Symbol(color));
        ok = true;
    }

    public void delSymbol(String symbol){
        int idx = indexOfColor(symbol);
        if(idx < 0){
            fail("El simbolo \"" + symbol + "\" no existe en el catalogo.");
            return;
        }
        catalog.remove(idx);
        ok = true;
    }

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

    public String[] symbols(){
        String[] result = new String[catalog.size()];
        for(int i = 0; i < catalog.size(); i++){
            result[i] = catalog.get(i).getColor();
        }
        ok = true;
        return result;
    }

    public int distinctSymbols(){
        Set<String> distinct = new HashSet<String>();
        for(Symbol s : catalog){
            distinct.add(s.getColor());
        }
        ok = true;
        return distinct.size();
    }

    public String[] configuration(){
        String[] result = new String[wheels.size()];
        for(int i = 0; i < wheels.size(); i++){
            result[i] = wheels.get(i).currentColor();
        }
        ok = true;
        return result;
    }

    public boolean isJackpot(){
        if(wheels.isEmpty()) return false;
        String first = wheels.get(0).currentColor();
        if(first == null) return false;
        for(Wheel w : wheels){
            if(!first.equals(w.currentColor())) return false;
        }
        return true;
    }

    public void makeVisible(){
        visible = true;
        everVisible = true;
        body.makeVisible();
        for(Wheel w : wheels) w.makeVisible();
        ok = true;
    }

    public void makeInvisible(){
        visible = false;
        body.makeInvisible();
        for(Wheel w : wheels) w.makeInvisible();
        ok = true;
    }

    public void exit(){
        makeInvisible();
        if(everVisible){
            Canvas.getCanvas().setVisible(false);
        }
        ok = true;
    }

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
