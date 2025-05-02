package comp20050.hexagonalboard;

import javafx.scene.paint.Color;

public class Player {
    private final Color color;

    public Player(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    /**
     * Gets the color of the enemy player
     * @return red if this player has color blue,
     * blue if this player has color red
     */
    public Color getEnemyColor() {
        return (color == Color.RED) ? Color.BLUE : Color.RED;
    }

    @Override
    public String toString() {
        return (color == Color.BLUE) ? "Blue Player" : "Red Player";
    }
}
