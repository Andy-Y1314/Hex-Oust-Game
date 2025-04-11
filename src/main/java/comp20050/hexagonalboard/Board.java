package comp20050.hexagonalboard;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

import static comp20050.hexagonalboard.Controller.colorGrey;

public class Board {
    ArrayList<ArrayList<Hexagon>> board;
    private final int radius;

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
            for (Hexagon hexagon : list) {
                if (hexagon.getId().equals(id)) {
                    return hexagon;
                }
            }
        }
        return null;
    }

    public boolean equalsBoard(Board board) {
        if (this.radius != board.radius) return false;

        for (int i = 0; i < this.board.size(); i++) {
            for (int j = 0; j < this.board.get(i).size(); j++) {
                if (!this.board.get(i).get(j).sameColor(board.board.get(i).get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    public ArrayList<Hexagon> getNeighbours(Hexagon hexagon) {
        ArrayList<Hexagon> neighbours = new ArrayList<Hexagon>();
        for (ArrayList<Hexagon> list : board) {
            for (Hexagon otherHexagon : list) {
                int qDiff = Math.abs(hexagon.getQ() - otherHexagon.getQ());
                int rDiff = Math.abs(hexagon.getR() - otherHexagon.getR());
                int sDiff = Math.abs(hexagon.getS() - otherHexagon.getS());

                if ((qDiff == 0 && rDiff == 1 && sDiff == 1) ||
                        (qDiff == 1 && rDiff == 0 && sDiff == 1) ||
                        (qDiff == 1 && rDiff == 1 && sDiff == 0)) {
                    neighbours.add(otherHexagon);
                }
            }
        }
        return neighbours;
    }

    public boolean sameColorNeighbourExists(Hexagon hexagon) {
        for (Hexagon otherHexagon : getNeighbours(hexagon)) {
            if (otherHexagon.getColor() == hexagon.getColor()) {
                return true;
            }
        }
        return false;
    }

    public List<Hexagon> getIsland(Hexagon hexagon) {
        List<Hexagon> islands = new ArrayList<>();
        List<Hexagon> queue = new ArrayList<>();

        queue.add(hexagon);
        islands.add(hexagon);
        while (!queue.isEmpty()) {
            Hexagon currentHex = queue.removeFirst();
            for (Hexagon otherHexagon : getNeighbours(currentHex)) {
                if (otherHexagon.sameColor(hexagon) && (!islands.contains(otherHexagon))) {
                    islands.add(otherHexagon);
                    queue.add(otherHexagon);
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

    public boolean isValidMove(Hexagon hex, Color currPlayerCol) {
        return validateMove(hex, currPlayerCol) != null;
    }

    public List<Hexagon> validateMove(Hexagon hexagon, Color currPlayerCol) {
        if (!hexagon.isEmpty()) return null;

        hexagon.setColor(currPlayerCol);

        if (!sameColorNeighbourExists(hexagon)) {
            hexagon.setColorGray();
            return new ArrayList<Hexagon>();
        }

        List<Hexagon> island = getIsland(hexagon);
        List<Hexagon> enemyHexagons = getEnemyHexagons(island);

        if (enemyHexagons.isEmpty()) {
            hexagon.setColorGray();
            return null;
        }

        for (Hexagon enemyHex : enemyHexagons) {
            if (island.size() <= getIsland(enemyHex).size()) {
                hexagon.setColorGray();
                return null;
            }
        }

        List<Hexagon> hexToRemove = new ArrayList<>();
        for (Hexagon enemyHex : enemyHexagons) {
            for (Hexagon islands : getIsland(enemyHex)) {
                if (!hexToRemove.contains(islands)) {
                    hexToRemove.add(islands);
                }
            }
        }
        hexagon.setColorGray();
        return hexToRemove;
    }
}
