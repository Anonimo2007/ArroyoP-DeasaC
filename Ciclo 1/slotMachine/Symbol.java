/**
 * A Symbol is a token identified only by its color (a CSS3 color name).
 * The same catalog of symbols can be placed on any wheel of the machine.
 *
 * @author  Gian Franco Arroyo Perez, Nicolas Deaza Casasbuenas
 * @version 1.0
 */
public class Symbol{

    private String color;

    /**
     * Create a symbol with the given (already validated) CSS color name.
     * @param color a valid CSS3 color name
     */
    public Symbol(String color){
        this.color = color;
    }

    /**
     * Return this symbol's color name.
     * @return the CSS3 color name identifying this symbol
     */
    public String getColor(){
        return color;
    }
}
