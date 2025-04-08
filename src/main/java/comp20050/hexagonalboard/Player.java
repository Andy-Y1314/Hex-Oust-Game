package comp20050.hexagonalboard;

import javafx.scene.paint.Color;

public class Player {
    private final Color color;
    private boolean isTurn;

    public Player(Color color) {
        this.color = color;
        this.isTurn = false;
    }

    public Color getColor() {
        return color;
    }

    public Color getEnemyColor() {
        return (color == Color.RED) ? Color.BLUE : Color.RED;
    }

    public boolean isTurn() {
        return isTurn;
    }

    public void setTurn(boolean isTurn) {
        this.isTurn = isTurn;
    }

    @Override
    public String toString() {
        if (this.getColor() == Color.BLUE) {
            return "Blue Player";
        } else {
            return "Red Player";
        }
    }

}
