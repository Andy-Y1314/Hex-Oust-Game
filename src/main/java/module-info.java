module comp20050.hexagonalboard {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;


    opens comp20050.hexagonalboard to javafx.fxml;
    exports comp20050.hexagonalboard;
}