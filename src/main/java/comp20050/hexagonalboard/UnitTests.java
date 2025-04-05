package comp20050.hexagonalboard;

import javafx.scene.paint.Color;
import org.junit.Test;

import java.util.List;

import static comp20050.hexagonalboard.Controller.colorGrey;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UnitTests {

    @Test
    public void confirmEqualsBoardWorks() {
        //All hexagons are gray by default
        Board input = new Board(7);
        Board expected = new Board(7);
        assertTrue(input.equalsBoard(expected));

        input.getHexagonById("q0r0s0").setColor(Color.RED);
        expected.getHexagonById("q1rm1s0").setColor(Color.BLUE);
        assertFalse(input.equalsBoard(expected));

        input.getHexagonById("q1rm1s0").setColor(Color.BLUE);
        expected.getHexagonById("q0r0s0").setColor(Color.RED);
        assertTrue(input.equalsBoard(expected));
    }

    @Test
    public void placingStoneNonCapturing() {
        Board input = new Board(7);
        Board expected = new Board(7);
        expected.getHexagonById("q0r0s0").setColor(Color.RED);
        Color currPlayerColor = Color.RED;
        String inputId = "q0r0s0";

        Hexagon hex = input.getHexagonById(inputId);

        List<Hexagon> hexToDelete = input.validateMove(hex, currPlayerColor);

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

        assertTrue(input.equalsBoard(expected));
    }
}
