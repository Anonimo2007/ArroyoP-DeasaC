import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * Extension of the "shapes" project: provides lookup of the standard
 * CSS3 named colors (https://www.w3.org/TR/css-color-3/#svg-color).
 * The original Canvas class only understood six hard-coded color names,
 * but this simulator requires symbols to be identified by any standard
 * CSS color name, so this class supplies that missing capability.
 *
 * @author  Gian Franco Arroyo Perez, Nicolas Deaza Casasbuenas
 * @version 1.0
 */
public class CssColors{

    private static final Map<String, Color> NAMES = build();

    /**
     * Indicate whether the given text is a valid, recognized CSS color name.
     * @param name the candidate color name (case-insensitive)
     * @return true if it is a known CSS3 color name
     */
    public static boolean isValid(String name){
        return name != null && NAMES.containsKey(name.trim().toLowerCase());
    }

    /**
     * Translate a CSS color name into its corresponding AWT color.
     * @param name a valid CSS color name (see isValid)
     * @return the matching java.awt.Color, or black if unknown
     */
    public static Color toAwt(String name){
        Color c = (name == null) ? null : NAMES.get(name.trim().toLowerCase());
        return (c == null) ? Color.black : c;
    }

    /*
     * Build the CSS3 extended color keyword table.
     */
    private static Map<String, Color> build(){
        Map<String, Color> m = new HashMap<String, Color>();
        m.put("black", new Color(0,0,0));         m.put("white", new Color(255,255,255));
        m.put("red", new Color(255,0,0));          m.put("lime", new Color(0,255,0));
        m.put("blue", new Color(0,0,255));         m.put("yellow", new Color(255,255,0));
        m.put("cyan", new Color(0,255,255));       m.put("magenta", new Color(255,0,255));
        m.put("silver", new Color(192,192,192));   m.put("gray", new Color(128,128,128));
        m.put("grey", new Color(128,128,128));     m.put("maroon", new Color(128,0,0));
        m.put("olive", new Color(128,128,0));      m.put("green", new Color(0,128,0));
        m.put("purple", new Color(128,0,128));     m.put("teal", new Color(0,128,128));
        m.put("navy", new Color(0,0,128));         m.put("orange", new Color(255,165,0));
        m.put("gold", new Color(255,215,0));       m.put("pink", new Color(255,192,203));
        m.put("brown", new Color(165,42,42));      m.put("coral", new Color(255,127,80));
        m.put("salmon", new Color(250,128,114));   m.put("khaki", new Color(240,230,140));
        m.put("orchid", new Color(218,112,214));   m.put("plum", new Color(221,160,221));
        m.put("violet", new Color(238,130,238));   m.put("indigo", new Color(75,0,130));
        m.put("turquoise", new Color(64,224,208)); m.put("tomato", new Color(255,99,71));
        m.put("crimson", new Color(220,20,60));    m.put("chocolate", new Color(210,105,30));
        m.put("tan", new Color(210,180,140));      m.put("beige", new Color(245,245,220));
        m.put("ivory", new Color(255,255,240));    m.put("lavender", new Color(230,230,250));
        m.put("skyblue", new Color(135,206,235));  m.put("steelblue", new Color(70,130,180));
        m.put("royalblue", new Color(65,105,225)); m.put("slateblue", new Color(106,90,205));
        m.put("darkblue", new Color(0,0,139));     m.put("darkred", new Color(139,0,0));
        m.put("darkgreen", new Color(0,100,0));    m.put("darkorange", new Color(255,140,0));
        m.put("darkviolet", new Color(148,0,211)); m.put("darkgray", new Color(169,169,169));
        m.put("darkgrey", new Color(169,169,169)); m.put("lightblue", new Color(173,216,230));
        m.put("lightgreen", new Color(144,238,144)); m.put("lightgray", new Color(211,211,211));
        m.put("lightgrey", new Color(211,211,211));  m.put("lightyellow", new Color(255,255,224));
        m.put("lightpink", new Color(255,182,193));  m.put("hotpink", new Color(255,105,180));
        m.put("deeppink", new Color(255,20,147));    m.put("firebrick", new Color(178,34,34));
        m.put("forestgreen", new Color(34,139,34)); m.put("seagreen", new Color(46,139,87));
        m.put("springgreen", new Color(0,255,127)); m.put("chartreuse", new Color(127,255,0));
        m.put("aquamarine", new Color(127,255,212)); m.put("cadetblue", new Color(95,158,160));
        m.put("cornflowerblue", new Color(100,149,237)); m.put("dodgerblue", new Color(30,144,255));
        m.put("midnightblue", new Color(25,25,112)); m.put("slategray", new Color(112,128,144));
        m.put("goldenrod", new Color(218,165,32)); m.put("peru", new Color(205,133,63));
        m.put("sienna", new Color(160,82,45));     m.put("indianred", new Color(205,92,92));
        m.put("mediumpurple", new Color(147,112,219)); m.put("darkkhaki", new Color(189,183,107));
        m.put("darkcyan", new Color(0,139,139));   m.put("darkmagenta", new Color(139,0,139));
        m.put("mistyrose", new Color(255,228,225)); m.put("moccasin", new Color(255,228,181));
        m.put("peachpuff", new Color(255,218,185)); m.put("powderblue", new Color(176,224,230));
        m.put("thistle", new Color(216,191,216));  m.put("wheat", new Color(245,222,179));
        m.put("yellowgreen", new Color(154,205,50)); m.put("rosybrown", new Color(188,143,143));
        return m;
    }
}
