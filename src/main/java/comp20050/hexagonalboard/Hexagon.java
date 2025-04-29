package comp20050.hexagonalboard;

import javafx.scene.paint.Color;
import static comp20050.hexagonalboard.Controller.colorGrey;

public class Hexagon {

    private final int q, r, s;
    private Color color;
    private final String id;

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

    public boolean isSameColor(Hexagon hex2) {
        return hex2.getColor() == color;
    }

    public Color getEnemyColor() {
        if (color == Color.RED) return Color.BLUE;
        if (color == Color.BLUE) return Color.RED;
        return colorGrey;
    }

    public boolean isEnemyColor(Hexagon hex2) {return getEnemyColor() == hex2.getColor();}

    public static String coordinatesToId(int q, int r, int s) {
        return "q" + ((q < 0) ? "m" + Math.abs(q) : Integer.toString(q)) +
                "r" + ((r < 0) ? "m" + Math.abs(r) : Integer.toString(r)) +
                "s" + ((s < 0) ? "m" + Math.abs(s) : Integer.toString(s));
    }
}
