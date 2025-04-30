package comp20050.hexagonalboard;

import javafx.scene.paint.Color;
import org.junit.Test;

import java.util.List;

import static comp20050.hexagonalboard.Controller.colorGrey;

public class ControllerTest {
    @Test
    public void placingStoneNonCapturing() {
        String inputId = "q0r0s0";
        Color currPlayerColor = Color.RED;

        Board input = new Board(7);

        Board expected = new Board(7);
        expected.getHexagonById("q0r0s0").setColor(Color.RED);


        Hexagon hex = input.getHexagonById(inputId);

        List<Hexagon> hexToDelete = input.validateMove(hex, currPlayerColor);

        /*
        - Copied directly from Controller class logic (getHexId)
        - Frontend / UI functionality has been commented out
         */

        if (hexToDelete == null) {
            //displayInvalidMove();
        } else {
            hex.setColor(Color.RED);
            //updateHexCounter(currentPlayer.getColor(), true);
            //polygonSetColor(hex.getId(), hex.getColor());

            for (Hexagon toDelete : hexToDelete) {
                toDelete.setColor(colorGrey);
                //updateHexCounter(currentPlayer.getEnemyColor(), false);
                //polygonSetColor(toDelete.getId(), colorGrey);
            }
        }

        /*
        for (int i = 0; i < this.board.size(); i++) {
            for (int j = 0; j < this.board.get(i).size(); j++) {
                if (!this.board.get(i).get(j).sameColor(board.board.get(i).get(j))) {
                    return false;
                }
            }
        }
         */
    }
}
