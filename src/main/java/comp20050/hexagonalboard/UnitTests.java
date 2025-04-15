package comp20050.hexagonalboard;

import javafx.scene.paint.Color;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static comp20050.hexagonalboard.Controller.colorGrey;
import static org.junit.Assert.*;

public class UnitTests {

    @Test
    public void testCoordinatesToId() {
        int q = 4, r = -1, s = -3;
        String expected = "q4rm1sm3";

        assertEquals(expected, Hexagon.coordinatesToId(q, r, s));
    }

    @Test
    public void testGetEnemyColor() {
        //Hexagon q,r,s values are trivial
        Hexagon redHex = new Hexagon(0,0,0);
        Hexagon blueHex = new Hexagon(0,0,0);

        redHex.setColor(Color.RED);
        blueHex.setColor(Color.BLUE);

        assertEquals(Color.BLUE, redHex.getEnemyColor());
        assertEquals(Color.RED, blueHex.getEnemyColor());
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
        Board testBoard = new Board(7);

        testBoard.getHexagonById("q0r0s0").setColor(Color.RED);
        testBoard.getHexagonById("qm1r0s1").setColor(Color.RED);
        testBoard.getHexagonById("qm1r1s0").setColor(Color.RED);

        testBoard.getHexagonById("q0r1sm1").setColor(Color.BLUE);

        Hexagon hex = testBoard.getHexagonById("q0r0s0");

        List<Hexagon> island = testBoard.getIsland(hex);
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

    @Test
    public void testGetEnemyHexagon() {
        Board testBoard = new Board(7);

        testBoard.getHexagonById("q0r0s0").setColor(Color.RED);
        testBoard.getHexagonById("qm1r0s1").setColor(Color.RED);

        testBoard.getHexagonById("q0r1sm1").setColor(Color.BLUE);
        testBoard.getHexagonById("q1rm1s0").setColor(Color.BLUE);
        testBoard.getHexagonById("q1r0sm1").setColor(Color.BLUE);

        Hexagon hex = testBoard.getHexagonById("q0r0s0");
        List<Hexagon> island = testBoard.getIsland(hex);
        List<Hexagon> enemyHexagons = testBoard.getEnemyHexagons(island);

        List<String> enemyHexagonIds = enemyHexagons.stream().map(Hexagon::getId).toList();

        List<String> expectedEnemyHexagons = List.of("q0r1sm1", "q1rm1s0", "q1r0sm1");

        assertEquals(expectedEnemyHexagons, enemyHexagonIds);
    }
}
