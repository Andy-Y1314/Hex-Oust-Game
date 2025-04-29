package comp20050.hexagonalboard;

import javafx.scene.paint.Color;

public record Player(Color color) {

    public Color getEnemyColor() {
        return (color == Color.RED) ? Color.BLUE : Color.RED;
    }

    @Override
    public String toString() {
        return (color == Color.BLUE) ? "Blue Player" : "Red Player";
    }
}
