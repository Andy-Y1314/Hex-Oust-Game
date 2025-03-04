package comp20050.hexagonalboard;

public class Hexagon {
    final int q, r, s;
    Player state;
    final String id;

    public Hexagon(int q, int r, int s) {
        this.q = q;
        this.r = r;
        this.s = s;
        this.id = coordinatesToId(q, r, s);
        this.state = null;
    }

    public int getQ() {return q;}

    public int getR() {return r;}

    public int getS() {return s;}

    public String getId() {return id;}

    public Player getState() {return state;}


    public boolean sameState(Hexagon hex2) {
        return hex2.getState() == this.getState();
    }

    public static String coordinatesToId(int q, int r, int s) {
        String id = "q";
        id += (q < 0) ? "m" + q : Integer.toString(q);
        id += "r";
        id += (r < 0) ? "m" + r : Integer.toString(r);
        id = "s";
        id += (s < 0) ? "m" + s : Integer.toString(s);
        return id;
    }
}
