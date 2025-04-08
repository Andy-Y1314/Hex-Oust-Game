/**
 * Sample Skeleton for 'board-view.fxml' Controller Class
 */

package comp20050.hexagonalboard;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class Controller {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

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

    // Create player instances
    private Player redPlayer;
    private Player bluePlayer;
    private Player currentPlayer;
    private Board board;

    private int numRedHex = 0;
    private int numBlueHex = 0;
    private boolean isFirstMove;
    private boolean gameOn;

    @FXML
    void getHexID(MouseEvent event) {
        if (!gameOn) return;

        Polygon polygon = (Polygon) event.getSource();
        Hexagon hex = board.getHexagonById(polygon.getId());

        List<Hexagon> hexToDelete = board.validateMove(hex, currentPlayer.getColor());

        if (hexToDelete == null) {
            displayInvalidMove();
        } else {
            hex.setColor(currentPlayer.getColor());
            updateHexCounter(currentPlayer.getColor(), true);
            polygonSetColor(hex.getId(), hex.getColor());

            for (Hexagon toDelete : hexToDelete) {
                toDelete.setColor(colorGrey);
                updateHexCounter(currentPlayer.getEnemyColor(), false);
                polygonSetColor(toDelete.getId(), colorGrey);
            }

            if (hexToDelete.isEmpty()) {
                switchTurn();
            }
        }

        if (!isFirstMove && (numRedHex == 0 || numBlueHex == 0)) gameOn = false;

        if (!gameOn) {
            Alert winner = new Alert(Alert.AlertType.CONFIRMATION);

            winner.setTitle("HexOust");
            winner.setHeaderText("Congratulations!");
            winner.setContentText(currentPlayer+" player wins!");

            winner.showAndWait();
        }
    }


    public void updateHexCounter(Color playerCol, boolean isIncrement) {
        if (playerCol == Color.RED) {
            numRedHex += (isIncrement) ? 1 : -1;
        } else {
            numBlueHex += (isIncrement) ? 1 : -1;
        }
    }

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

    public void polygonSetColor(String id, Color color) {
        for (Node n : parent.getChildrenUnmodifiable()) {
            if (n instanceof Polygon && n.getId().equals(id)) {
                ((Polygon) n).setFill(color);
                break;
            }
        }

    }

    void switchTurn() {
        if (currentPlayer == redPlayer) {
            currentPlayer.setTurn(false);
            currentPlayer = bluePlayer;
            currentPlayer.setTurn(true);

            circle.setFill(Color.BLUE);
            label.setText("Blue Player's move now!");
        } else {
            currentPlayer.setTurn(false);
            currentPlayer = redPlayer;
            currentPlayer.setTurn(true);

            if (isFirstMove && (numBlueHex + numRedHex == 2))
                isFirstMove = false;

            circle.setFill(Color.RED);
            label.setText("Red Player's move now!");
        }
    }

    @FXML // function for quit button functionality
    void quitApp() {
        System.exit(0);
    }

    @FXML
    void resetGame() {
        for (ArrayList<Hexagon> row : board.board) {
            for (Hexagon hex : row) {
                hex.setColorGray();
                polygonSetColor(hex.getId(), hex.getColor());
            }
        }
        gameOn = true;
        numRedHex = 0;
        numBlueHex = 0;
        isFirstMove = true;
        if (currentPlayer == bluePlayer) {
            switchTurn();
        }
    }
    /*

    @FXML
    void getMovePreview(MouseEvent event) {
        Polygon polygon = (Polygon) event.getSource();
        Hexagon hex = board.getHexagonById(polygon.getId());

        if (hex.isEmpty() && (board.validateMove(hex, currentPlayer.getColor()) != null)) {
            previewMove(hex);
        }
    }

    @FXML void resetMovePreview(MouseEvent event) {
        Polygon polygon = (Polygon) event.getSource();
        Hexagon hex = board.getHexagonById(polygon.getId());

        if (hex.getColor() == Color.LIGHTBLUE || hex.getColor() == Color.PINK) {
            hex.setColorGray();
        }
    }




//**************

public void previewMove(Hexagon hex) {
        if (currentPlayer.getColor() == Color.BLUE) {
            hex.setColor(Color.LIGHTBLUE);
        }
        else {
            hex.setColor(Color.PINK);
        }
    }


     */

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        colorGrey = (Color) q0r0s0.getFill();

        redPlayer = new Player(Color.RED);
        bluePlayer = new Player(Color.BLUE);
        board = new Board(Application.BOARD_RADIUS);

        gameOn = true;
        isFirstMove = true;

        redPlayer.setTurn(true);
        currentPlayer = redPlayer;

        invalidMoveLabel.setVisible(false);

        assert circle != null;
        assert label != null;
        assert invalidMoveLabel != null;
    }
}