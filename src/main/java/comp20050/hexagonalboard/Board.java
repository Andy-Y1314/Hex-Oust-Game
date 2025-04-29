package comp20050.hexagonalboard;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Board {
    ArrayList<Hexagon> board = new ArrayList<>();;

    public Board(int radius) {
        for (int q = -radius; q <= radius; q++) {
            for (int r = -radius; r <= radius; r++) {
                for (int s = -radius; s <= radius; s++) {
                    if (q + r + s == 0) {
                        board.add(new Hexagon(q, r, s));
                    }
                }
            }
        }
    }

    public Hexagon getHexagonById(String id) {
        for (Hexagon hexagon : board) {
            if (hexagon.getId().equals(id)) {
                return hexagon;
            }
        }
        return null;
    }

    public ArrayList<Hexagon> getNeighbours(Hexagon hexagon) {
        ArrayList<Hexagon> neighbours = new ArrayList<>();
        for (Hexagon otherHexagon : board) {
            int qDiff = Math.abs(hexagon.getQ() - otherHexagon.getQ());
            int rDiff = Math.abs(hexagon.getR() - otherHexagon.getR());
            int sDiff = Math.abs(hexagon.getS() - otherHexagon.getS());

            if ((qDiff == 0 && rDiff == 1 && sDiff == 1) ||
                    (qDiff == 1 && rDiff == 0 && sDiff == 1) ||
                    (qDiff == 1 && rDiff == 1 && sDiff == 0)) {
                neighbours.add(otherHexagon);
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
        List<Hexagon> hexToRemove = new ArrayList<>();

        if (sameColorNeighbourExists(hexagon)) {
            List<Hexagon> island = getIsland(hexagon);
            hexToRemove = validateCaptureMove(island, getEnemyHexagons(island));
        }
        hexagon.setColorGrey();
        return hexToRemove;
    }

    private List<Hexagon> validateCaptureMove(List<Hexagon> island, List<Hexagon> enemyHexagons) {
        if (enemyHexagons.isEmpty()) return null;

        for (Hexagon enemyHex : enemyHexagons) {
            if (island.size() <= getIsland(enemyHex).size()) return null;
        }

        List<Hexagon> hexToRemove = new ArrayList<>();
        for (Hexagon enemyHex : enemyHexagons) {
            for (Hexagon hex : getIsland(enemyHex)) {
                if (!hexToRemove.contains(hex)) {
                    hexToRemove.add(hex);
                }
            }
        }
        return hexToRemove;
    }
}
