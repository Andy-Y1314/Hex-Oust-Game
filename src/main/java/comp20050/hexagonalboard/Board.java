package comp20050.hexagonalboard;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Board class represents a hexagonal game board composed of hexagons.
 * Provides methods for game logic such as capturing moves.
 */
public class Board {
    ArrayList<Hexagon> board = new ArrayList<>();;

    /**
     * Constructs a hexagonal board with the specified radius.
     * Each hexagon is initialized such that q + r + s = 0,
     *
     * @param radius the radius of the hexagonal board
     */
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

    /**
     * Returns the hexagon object with the specified hexagon ID.
     *
     * @param id the unique identifier of the hexagon
     * @return the matching hexagon, or null if not found
     */
    public Hexagon getHexagonById(String id) {
        for (Hexagon hexagon : board) {
            if (hexagon.getId().equals(id)) {
                return hexagon;
            }
        }
        return null;
    }

    /**
     * Returns the neighboring hexagons of the specified hexagon.
     * Neighbors are hexagons that border/surround the given hexagon.
     *
     * @param hexagon the hexagon used as the centre for checking its neighbours
     * @return a list of neighboring hexagons
     */
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

    /**
     * Checks whether there is at least one neighboring hexagon of the same colour.
     *
     * @param hexagon the hexagon to check
     * @return true if a same-colored neighbor exists, false otherwise
     */
    public boolean sameColorNeighbourExists(Hexagon hexagon) {
        for (Hexagon otherHexagon : getNeighbours(hexagon)) {
            if (hexagon.isSameColor(otherHexagon)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a list of all hexagons connected to a given hexagon that shares the same colour.
     *
     * @param hexagon the input hexagon
     * @return a list of hexagons forming an island (connected group of same coloured hexagons)
     */
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

    /**
     * Returns a list of all adjacent enemy hexagons from a given island.
     * Enemy hexagons are hexagons with different colour to the input hexagons(island) colour.
     *
     * @param island a group of connected hexagons with the same colour
     * @return a list of neighboring enemy hexagons
     */
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

    /**
     * Determines which enemy hexagons would be removed if a move is played on the specified hexagon.
     *
     * @param hexagon       the target hexagon to play on
     * @param currPlayerCol the colour of the current player
     * @return list of hexagons to remove if the move results in a capture, otherwise null
     */
    public List<Hexagon> HexagonsToRemove(Hexagon hexagon, Color currPlayerCol) {
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

    /**
     * Validates whether a group of enemy hexagons is capturable based on the island that surrounds them.
     * Capturing occurs if the player's island is larger than each adjacent enemy island.
     *
     * @param island         the island of the current player's colour
     * @param enemyHexagons  the list of adjacent enemy hexagons
     * @return a list of hexagons to be removed if the capture is valid, otherwise null
     */
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