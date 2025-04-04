package comp20050.hexagonalboard;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {

    public final static int BOARD_RADIUS = 6;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("board-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("The Ultimate Businessmen Hex Oust");
        stage.setScene(scene);
        stage.setMaximized(true); // app will always launch at maximum window size
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}