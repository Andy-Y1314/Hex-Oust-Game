package comp20050.hexagonalboard;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Board {
    ArrayList<ArrayList<Hexagon>> board;

    public Board(int radius) {
        board = new ArrayList<>();
        for (int i = -radius; i <= radius; i++) {
            board.add(new ArrayList<>());
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
        for (ArrayList<Hexagon> column : board) {
            for (Hexagon hexagon : column) {
                if (hexagon.getId().equals(id)) {
                    return hexagon;
                }
            }
        }
        return null;
    }

    public ArrayList<Hexagon> getNeighbours(Hexagon hexagon) {
        ArrayList<Hexagon> neighbours = new ArrayList<>();
        for (ArrayList<Hexagon> column : board) {
            for (Hexagon otherHexagon : column) {
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
            if (hexagon.isSameColor(otherHexagon)) {
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
            for (Hexagon otherHexagon : getNeighbours(queue.removeFirst())) {
                if (otherHexagon.isSameColor(hexagon) && !islands.contains(otherHexagon)) {
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
                if (currentHex.isEnemyColor(neighbour) && !(output.contains(neighbour))) {
                    output.add(neighbour);
                }
            }
        }
        return output;
    }

    public List<Hexagon> validateMove(Hexagon hexagon, Color currPlayerCol) {
        if (!hexagon.isEmpty()) return null;

        hexagon.setColor(currPlayerCol);

        if (!sameColorNeighbourExists(hexagon)) {
            hexagon.setColorGrey();
            return new ArrayList<>();
        }

        List<Hexagon> island = getIsland(hexagon);
        List<Hexagon> enemyHexagons = getEnemyHexagons(island);

        if (enemyHexagons.isEmpty()) {
            hexagon.setColorGrey();
            return null;
        }

        for (Hexagon enemyHex : enemyHexagons) {
            if (island.size() <= getIsland(enemyHex).size()) {
                hexagon.setColorGrey();
                return null;
            }
        }

        List<Hexagon> hexToRemove = new ArrayList<>();
        for (Hexagon enemyHex : enemyHexagons) {
            for (Hexagon hex : getIsland(enemyHex)) {
                if (!hexToRemove.contains(hex)) {
                    hexToRemove.add(hex);
                }
            }
        }
        hexagon.setColorGrey();
        return hexToRemove;
    }
}
