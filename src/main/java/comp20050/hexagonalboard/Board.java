package comp20050.hexagonalboard;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Board {
    ArrayList<ArrayList<Hexagon>> board;
    final int radius;

    public Board(int radius) {
        this.radius = radius;
        board = new ArrayList<ArrayList<Hexagon>>();
        for (int i = -radius; i <= radius; i++) {
            board.add(new ArrayList<Hexagon>());
        }
        for (int q = -radius; q <= radius; q++) {
            for (int r = -radius; r <= radius; r++) {
                for (int s = -radius; s <= radius; s++) {
                    if (q + r + s == 0) {
                        board.get(q + radius).add(new Hexagon(q, r, s));
                    }
                }
            }
        }
    }

    public Hexagon getHexagonById(String id) {
        for (ArrayList<Hexagon> list : board) {
            for (Hexagon hex : list) {
                if (hex.getId().equals(id)) {
                    return hex;
                }
            }
        }
        return null;
    }

    public ArrayList<Hexagon> getNeighbours(Hexagon hex) {
        ArrayList<Hexagon> neighbours = new ArrayList<Hexagon>();
        for (ArrayList<Hexagon> list : board) {
            for (Hexagon hex2 : list) {
                int qDiff = Math.abs(hex.getQ() - hex2.getQ());
                int rDiff = Math.abs(hex.getR() - hex2.getR());
                int sDiff = Math.abs(hex.getS() - hex2.getS());

                if ((qDiff == 0 && rDiff == 1 && sDiff == 1) ||
                        (qDiff == 1 && rDiff == 0 && sDiff == 1) ||
                        (qDiff == 1 && rDiff == 1 && sDiff == 0)) {
                    neighbours.add(hex2);
                }
            }
        }
        return neighbours;
    }

    public boolean sameColorNeighbourExists(Hexagon hex, Color color) {
        for (Hexagon hex2 : getNeighbours(hex)) {
            if (hex2.getColor() == color) {
                return true;
            }
        }
        return false;
    }
}
