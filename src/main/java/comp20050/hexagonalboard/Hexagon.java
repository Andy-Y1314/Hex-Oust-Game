package comp20050.hexagonalboard;

import javafx.scene.paint.Color;
import static comp20050.hexagonalboard.Controller.colorGrey;

/**
 * Class for the hexagons in the hexagonal board game.
 * Each Hexagon has a unique ID derived from the cubic coordinates (q, r, s).
 * Each Hexagon also have colours (Red or Blue) that represents which player made a move
 * on that specific hexagon, or if the hexagon is grey, indicates no player's made a move on that hexagon.
 *
 */
public class Hexagon {

    private final int q, r, s;
    private Color color;
    private final String id;

    /**
     * Constructs a Hexagon with given coordinates and assigns it a default colour Grey.
     *
     * @param q The q coordinate.
     * @param r The r coordinate.
     * @param s The s coordinate.
     */
    public Hexagon(int q, int r, int s) {
        this.q = q;
        this.r = r;
        this.s = s;
        this.id = coordinatesToId(q, r, s);
        this.color = colorGrey;
    }


    public int getQ() {return q;}

    public int getR() {return r;}

    public int getS() {return s;}

    public String getId() {return id;}

    public Color getColor() {return color;}

    public void setColor(Color color) {this.color = color;}

    public void setColorGrey() {color = colorGrey;}

    public boolean isEmpty() {return color == colorGrey;}

    public boolean isSameColor(Hexagon hexagon) {
        return hexagon.getColor() == color;
    }

    public Color getEnemyColor() {
        if (color == Color.RED) return Color.BLUE;
        if (color == Color.BLUE) return Color.RED;
        return colorGrey;
    }

    public boolean isEnemyColor(Hexagon hexagon) {return getEnemyColor() == hexagon.getColor();}

    /**
     * Converts coordinates to a unique string ID.
     *
     * @param q The q coordinate.
     * @param r The r coordinate.
     * @param s The s coordinate.
     * @return A string in the format "q{q}r{r}s{s}" where negatives are appended with m in front.
     */
    public static String coordinatesToId(int q, int r, int s) {
        return "q" + ((q < 0) ? "m" + Math.abs(q) : Integer.toString(q)) +
                "r" + ((r < 0) ? "m" + Math.abs(r) : Integer.toString(r)) +
                "s" + ((s < 0) ? "m" + Math.abs(s) : Integer.toString(s));
    }
}
