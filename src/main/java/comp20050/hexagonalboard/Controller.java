package comp20050.hexagonalboard;

import java.util.List;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Controller class for managing the UI logic and game flow of a two-player
 * hexagonal board game using JavaFX and SceneBuilder.
 *
 * This class handles front end, updates the game board, tracks player turns,
 */
public class Controller {

    @FXML
    private AnchorPane parent;

    @FXML
    private Circle circle;

    @FXML
    private Label label;

    @FXML
    private Label invalidMoveLabel;

    @FXML
    private Polygon q0r0s0;

    public static Color colorGrey;

    private Player redPlayer;
    private Player bluePlayer;
    private Player currentPlayer;
    private Board board;

    private boolean isFirstMove;
    private boolean gameOn;

    public int numRedHex = 0;
    public int numBlueHex = 0;

    /**
     * Handles what happens when a player places a move.
     * If the move is valid, the board is updated and the turn may switch.
     * Ends the game if it is not the firstMove and when a player has no more
     * hexagons of their own colour
     *
     * @param event Mouse event triggered by clicking a hexagon.
     */
    @FXML
    void placeHex(MouseEvent event) {
        if (!gameOn) return;

        Polygon pol = ((Polygon) event.getSource());
        Hexagon hex = board.getHexagonById(pol.getId());

        makeValidMove(hex);

        if (!isFirstMove && (numRedHex == 0 || numBlueHex == 0)) {
            gameOn = false;
            gameWinDisplay();
        }
    }

    /**
     * Applies a valid move to the board and updates any affected hexagons.
     * If no hexagons are captured (Non Capturing Move), the turn switches.
     *
     * @param hex The hexagon where the move is attempted.
     */
    void makeValidMove(Hexagon hex) {
        List<Hexagon> hexToDelete = board.HexagonsToRemove(hex, currentPlayer.getColor());

        if (hexToDelete == null) {
            displayInvalidMove();
            return;
        }

        hex.setColor(currentPlayer.getColor());
        updateHexCounter(currentPlayer.getColor(), true);
        polygonSetColor(hex.getId(), hex.getColor());

        for (Hexagon toDelete : hexToDelete) {
            toDelete.setColor(colorGrey);
            updateHexCounter(currentPlayer.getEnemyColor(), false);
            polygonSetColor(toDelete.getId(), colorGrey);
        }

        if (hexToDelete.isEmpty()) switchTurn();
    }

    /**
     * Displays a pop-up window showing the game winner and provides options
     * to restart or quit.
     */
    public void gameWinDisplay() {
        Stage window = new Stage();
        VBox layout = new VBox(10);

        Label winnersMessage = new Label(currentPlayer + " wins!");

        Button restartButton = new Button("Reset");
        Button exitButton = new Button("Quit");

        restartButton.setOnAction(e -> {
            resetGame();
            window.close();
        });

        exitButton.setOnAction(e -> {
            quitApp();
        });

        layout.getChildren().addAll(winnersMessage, restartButton, exitButton);

        Scene scene = new Scene(layout, 200, 150);
        window.setScene(scene);
        window.show();
    }

    /**
     * Updates the number of hexagons that a player occupies.
     *
     * @param playerCol The player's colour.
     * @param isIncrement Whether to increment (true) or decrement (false) the count.
     */
    public void updateHexCounter(Color playerCol, boolean isIncrement) {
        if (playerCol == Color.RED) {
            numRedHex += (isIncrement) ? 1 : -1;
        } else {
            numBlueHex += (isIncrement) ? 1 : -1;
        }
    }

    /**
     * Temporarily displays a label indicating an invalid move was attempted.
     */
    public void displayInvalidMove() {
        invalidMoveLabel.setVisible(true);
        circle.setVisible(false);
        label.setVisible(false);

        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(event2 -> {
            invalidMoveLabel.setVisible(false);
            circle.setVisible(true);
            label.setVisible(true);
        });
        pause.play();
    }

    /**
     * Changes the fill colour of a polygon(hexagon) based on its ID.
     *
     * @param id The ID of the polygon(hexagon).
     * @param color The colour to apply.
     */
    public void polygonSetColor(String id, Color color) {
        for (Node n : parent.getChildrenUnmodifiable()) {
            if (n instanceof Polygon && n.getId().equals(id)) {
                ((Polygon) n).setFill(color);
                break;
            }
        }

    }

    /**
     * Switches the active player and updates the UI accordingly.
     */
    void switchTurn() {
        if (currentPlayer == redPlayer) {
            currentPlayer = bluePlayer;

            circle.setFill(Color.BLUE);
            label.setText("Blue Player's move now!");
        } else {
            currentPlayer = redPlayer;

            if (isFirstMove && (numBlueHex + numRedHex == 2))
                isFirstMove = false;

            circle.setFill(Color.RED);
            label.setText("Red Player's move now!");
        }
    }

    /**
     * Exits the application.
     */
    @FXML
    void quitApp() {
        System.exit(0);
    }

    /**
     * Resets the game board and counters to their initial state.
     */
    @FXML
    void resetGame() {
        for (Hexagon hex : board.board) {
            hex.setColorGrey();
            polygonSetColor(hex.getId(), hex.getColor());
        }
        gameOn = true;
        numRedHex = 0;
        numBlueHex = 0;
        isFirstMove = true;
        if (currentPlayer == bluePlayer) {
            switchTurn();
        }
    }

    /**
     * Enables the highlighting of a hexagon when hovered, if it's a valid move.
     *
     * @param event The mouse event triggering the preview.
     */
    @FXML
    void getMovePreview(MouseEvent event) {
        Polygon polygon = (Polygon) event.getSource();
        Hexagon hex = board.getHexagonById(polygon.getId());

        if (gameOn && board.HexagonsToRemove(hex, currentPlayer.getColor()) != null) {
            previewMove(hex.getId());
        }
    }

    /**
     * Resets the colour of a hexagon to grey after the hover
     *
     * @param event The mouse event that ends the preview.
     */
    @FXML
    void resetMovePreview(MouseEvent event) {
        Polygon polygon = (Polygon) event.getSource();

        if (polygon.getFill() == Color.LIGHTBLUE || polygon.getFill() == Color.PINK) {
            polygonSetColor(polygon.getId(), colorGrey);
        }
    }

    /**
     * Highlights a hexagon for preview using a light shade of the current player's colour.
     *
     * @param polygonId The ID of the polygon to highlight.
     */
    @FXML
    public void previewMove(String polygonId) {
        if (currentPlayer.getColor() == Color.BLUE) {
            polygonSetColor(polygonId, Color.LIGHTBLUE);
        } else {
            polygonSetColor(polygonId, Color.PINK);
        }
    }

    /**
     * Initialises the controller and game state when the scene is first loaded.
     */
    @FXML
    void initialize() {
        colorGrey = (Color) q0r0s0.getFill();

        redPlayer = new Player(Color.RED);
        bluePlayer = new Player(Color.BLUE);
        board = new Board(Application.BOARD_RADIUS);

        gameOn = true;
        isFirstMove = true;

        currentPlayer = redPlayer;

        invalidMoveLabel.setVisible(false);
    }
}