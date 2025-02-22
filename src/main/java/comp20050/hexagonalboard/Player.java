package comp20050.hexagonalboard;

import javafx.scene.paint.Color;

public class Player {
    private Color color;
    private boolean isTurn;

    public Player(Color color) {
        this.color = color;
        this.isTurn = false;
    }

    public Color getColor() {
        return color;
    }

    public boolean isTurn() {
        return isTurn;
    }

    public void setTurn(boolean isTurn) {
        this.isTurn = isTurn;
    }
}
