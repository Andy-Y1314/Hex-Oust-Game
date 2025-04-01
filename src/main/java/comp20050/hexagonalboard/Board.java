package comp20050.hexagonalboard;

import javafx.scene.paint.Color;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

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

    public List<Hexagon> getIslands(Hexagon hex) {
        List<Hexagon> islands = new ArrayList<>();
        List<Hexagon> queue = new ArrayList<>();

        queue.add(hex);
        islands.add(hex);
        while (!queue.isEmpty()) {
            Hexagon currentHex = queue.removeFirst();
            for (Hexagon hex2 : getNeighbours(currentHex)) {
                if (hex2.getColor().equals(hex.getColor()) && (!islands.contains(hex2))) {
                    islands.add(hex2);
                    queue.add(hex2);
                }
            }
        }
        return islands;
    }

    public List<Hexagon> getEnemyHexagons(List<Hexagon> island) {
        List<Hexagon> output = new ArrayList<>();
        for (Hexagon currentHex : island) {
            for (Hexagon neighbour : getNeighbours(currentHex)) {
                if (currentHex.getEnemyColor() == neighbour.getColor() && !(output.contains(neighbour))) {
                    output.add(neighbour);
                }
            }
        }
        return output;
    }


}
