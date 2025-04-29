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

    public void setTurn(boolean isTurn) {this.isTurn = isTurn;}

    @Override
    public String toString() {
        return (color == Color.BLUE) ? "Blue Player" : "Red Player";
    }

}
