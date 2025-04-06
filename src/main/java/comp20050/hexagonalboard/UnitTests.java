package comp20050.hexagonalboard;

import javafx.scene.paint.Color;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static comp20050.hexagonalboard.Controller.colorGrey;
import static org.junit.Assert.*;

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
        String inputId = "q0r0s0";
        Color currPlayerColor = Color.RED;

        Board input = new Board(7);

        Board expected = new Board(7);
        expected.getHexagonById("q0r0s0").setColor(Color.RED);


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

    @Test
    public void testGetNeighbours() {
        Board input = new Board(7);

        Hexagon hex = input.getHexagonById("q0r0s0");

        //Creating a list of all the expected neighbours the 'getNeighbours' method should return for hexagon q0r0s0
        List<Hexagon> neighbours = input.getNeighbours(hex);
        List<String> neighbourIds = neighbours.stream().map(Hexagon::getId).toList();

        List<String> expectedNeighbourIds = List.of("qm1r0s1", "qm1r1s0", "q0rm1s1", "q0r1sm1", "q1rm1s0", "q1r0sm1");

        assertEquals(expectedNeighbourIds, neighbourIds);
    }

    @Test
    public void testGetIsland() {
        Board customBoard = new Board(7);

        customBoard.getHexagonById("q0r0s0").setColor(Color.RED);
        customBoard.getHexagonById("qm1r0s1").setColor(Color.RED);
        customBoard.getHexagonById("qm1r1s0").setColor(Color.RED);

        customBoard.getHexagonById("q0r1sm1").setColor(Color.BLUE);

        Hexagon hex = customBoard.getHexagonById("q0r0s0");

        List<Hexagon> island = customBoard.getIsland(hex);
        List<String> islandIds = island.stream().map(Hexagon::getId).toList();

        List<String> expectedIsland = List.of("q0r0s0", "qm1r0s1", "qm1r1s0");

        assertEquals(expectedIsland, islandIds);
    }

    @Test
    public void testSameColorNeighbourExists() {
        Board input = new Board(7);

        Hexagon hex1 = input.getHexagonById("q0r0s0");

        input.getHexagonById("q0r0s0").setColor(Color.RED);
        input.getHexagonById("qm1r0s1").setColor(Color.RED);

        Hexagon hex2 = input.getHexagonById("qm3r3s0");

        input.getHexagonById("qm2r2s0").setColor(Color.RED);
        input.getHexagonById("qm3r3s0").setColor(Color.BLUE);

        assertTrue(input.sameColorNeighbourExists(hex1));
        assertFalse(input.sameColorNeighbourExists(hex2));
    }


}
