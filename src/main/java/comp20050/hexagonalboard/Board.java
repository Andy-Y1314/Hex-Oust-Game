package comp20050.hexagonalboard;

import java.util.ArrayList;

public class Board {
    final ArrayList<ArrayList<Hexagon>> board;
    final int radius;

    public Board(int radius) {
        this.radius = radius;

        ArrayList<ArrayList<Hexagon>> tempBoard = new ArrayList<ArrayList<Hexagon>>();
        for (int q = -radius; q <= radius; q++) {
            ArrayList<Hexagon> tempList = new ArrayList<>();

            for (int r = -radius; r <= radius; r++) {
                for (int s = -radius; s <= radius; s++) {
                    if (q + r + s == 0) {
                        tempList.add(new Hexagon(q, r, s));
                    }
                }
            }
            tempBoard.add(tempList);
        }
        board = tempBoard;
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

    public boolean neighbourSameColour(Hexagon hex) {
        for (Hexagon hex2 : getNeighbours(hex)) {
            if (hex.sameState(hex2)) {
                return true;
            }
        }
        return false;
    }
}
