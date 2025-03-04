/**
 * Sample Skeleton for 'hello-view.fxml' Controller Class
 */

package comp20050.hexagonalboard;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.paint.Color;

public class HelloController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML
    private Circle circle;

    @FXML
    private Label label;

    // Create player instances
    private Player redPlayer;
    private Player bluePlayer;
    private Player currentPlayer;
    private Board board;

    @FXML
    void getHexID(MouseEvent event) {
        Polygon polygon = (Polygon) event.getSource();

        Parent parent = polygon.getParent();
        Hexagon hex = board.getHexagonById(polygon.getId());

        if (!board.sameColorNeighbourExists(hex, currentPlayer.getColor())) {
            hex.setColor(currentPlayer.getColor());
            polygonSetColor(parent, hex.getId(), hex.getColor());
            switchTurn();
        }
    }

    public void polygonSetColor(Parent parent, String id, Color color) {
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

            circle.setFill(Color.RED);
            label.setText("Red Player's move now!");
        }
    }

    @FXML // function for quit button functionality
    void quitApp() {
        System.exit(0);
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        redPlayer = new Player(Color.RED);
        bluePlayer = new Player(Color.BLUE);
        board = new Board(HelloApplication.BOARD_RADIUS);

        redPlayer.setTurn(true);
        currentPlayer = redPlayer;

        assert circle != null;
        assert label != null;
    }
}