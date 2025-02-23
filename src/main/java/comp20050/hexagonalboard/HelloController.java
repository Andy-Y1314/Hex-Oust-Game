/**
 * Sample Skeleton for 'hello-view.fxml' Controller Class
 */

package comp20050.hexagonalboard;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.paint.Color;

public class HelloController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="hex1"
    private Polygon hex1; // Value injected by FXMLLoader

    @FXML
    private Circle circle;

    @FXML
    private Label label;

    // Create player instances
    private Player redPlayer;
    private Player bluePlayer;
    private Player currentPlayer;

    @FXML
    void getHexID(MouseEvent event) {
        Polygon hexagon = (Polygon) event.getSource();
        hexagon.setFill(currentPlayer.getColor());

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

        redPlayer.setTurn(true);
        currentPlayer = redPlayer;

        assert hex1 != null : "fx:id=\"hex1\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert circle != null;
        assert label != null;
    }

}
