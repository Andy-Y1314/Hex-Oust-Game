package comp20050.hexagonalboard;

import javafx.scene.paint.Color;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static comp20050.hexagonalboard.Controller.colorGrey;
import static org.junit.Assert.*;

public class UnitTests {

    /**
     * Hexagon Class tests
     */
    @Test
    public void testCoordinatesToId1() {
        int q = 4, r = -1, s = -3;
        String expected = "q4rm1sm3";

        assertEquals(expected, Hexagon.coordinatesToId(q, r, s));
    }

    @Test
    public void testCoordinatesToId2() {
        int q = -2, r = 2, s = 0;
        String expected = "qm2r2s0";

        assertEquals(expected, Hexagon.coordinatesToId(q, r, s));
    }

    @Test
    public void testHexagonGetEnemyColor() {
        //Hexagon q,r,s values are trivial
        Hexagon redHex = new Hexagon(0,0,0);
        Hexagon blueHex = new Hexagon(0,0,0);

        redHex.setColor(Color.RED);
        blueHex.setColor(Color.BLUE);

        assertEquals(Color.BLUE, redHex.getEnemyColor());
    }

    @Test
    public void testHexagonIsSameColor() {
        //Hexagon q,r,s values are trivial
        Hexagon redHex = new Hexagon(2,3,4);
        Hexagon redHex2 = new Hexagon(3,4,5);

        redHex.setColor(Color.RED);
        redHex2.setColor(Color.RED);

        assertTrue(redHex.isSameColor(redHex2));
    }

    @Test
    public void testHexagonIsSameColorFalse() {
        //Hexagon q,r,s values are trivial
        Hexagon redHex = new Hexagon(2,3,4);
        Hexagon blueHex = new Hexagon(1,0,1);

        redHex.setColor(Color.RED);
        blueHex.setColor(Color.BLUE);

        assertFalse(redHex.isSameColor(blueHex));
    }

    @Test
    public void testHexagonIsEnemyColor() {
        //Hexagon q,r,s values are trivial
        Hexagon redHex = new Hexagon(0,6,9);
        Hexagon blueHex = new Hexagon(4,2,0);

        redHex.setColor(Color.RED);
        blueHex.setColor(Color.BLUE);

        assertTrue(blueHex.isEnemyColor(redHex));
    }

    @Test
    public void testHexagonIsEnemyColorFalse() {
        //Hexagon q,r,s values are trivial
        Hexagon blueHex = new Hexagon(4,3,0);
        Hexagon blueHex2 = new Hexagon(0,6,8);

        blueHex.setColor(Color.BLUE);
        blueHex2.setColor(Color.BLUE);

        assertFalse(blueHex.isEnemyColor(blueHex2));
    }


    /**
     * Board Class Tests
     */

    @Test
    public void testGetHexagonById() {
        Board input = new Board(7);
        Hexagon hex = input.getHexagonById("q4r2sm6");

        assertTrue(hex.getQ() == 4 && hex.getR() == 2 && hex.getS() == -6);
    }

    @Test
    public void testGetHexagonByIdWrong() {
        Board input = new Board(7);
        Hexagon hex = input.getHexagonById("q3r3sm6");

        assertFalse(hex.getQ() == 5 && hex.getR() == 1 && hex.getS() == -6);
    }

    @Test
    public void testGetHexagonByIdInvalidHex() {
        Board input = new Board(7);
        Hexagon hex = input.getHexagonById("q3rm3s1");

        assertNull(hex);
    }

    @Test
    public void testGetHexagonByIdOutOfBounds() {
        Board input = new Board(7);
        Hexagon hex = input.getHexagonById("q10rm5sm5");

        assertNull(hex);
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
    public void testGetWrongNeighbours() {
        Board input = new Board(7);

        Hexagon hex = input.getHexagonById("q2rm2s0");

        List<Hexagon> actualNeighbours = input.getNeighbours(hex);
        List<String> actualNeighbourIds = actualNeighbours.stream().map(Hexagon::getId).toList();

        List<String> wrongNeighbourIds = List.of("q0r0s0", "q1rm1s0", "q2rm3s1", "q2rm1sm1", "q3rm3s0", "q3rm2sm1");

        assertNotEquals(actualNeighbourIds, wrongNeighbourIds);
    }

    @Test
    public void testGetNeighboursEdge() {
        Board input = new Board(6);
        Hexagon hex = input.getHexagonById("q6rm6s0");

        //Creating a list of all the expected neighbours the 'getNeighbours' method should return for hexagon q0r0s0
        List<Hexagon> neighbours = input.getNeighbours(hex);
        List<String> neighbourIds = neighbours.stream().map(Hexagon::getId).toList();

        List<String> expectedNeighbourIds = List.of("q5rm6s1", "q5rm5s0", "q6rm5sm1");

        assertEquals(expectedNeighbourIds, neighbourIds);
    }

    @Test
    public void testGetNeighboursEdgeWrong() {
        Board input = new Board(6);
        Hexagon hex = input.getHexagonById("q6rm6s0");

        //Creating a list of all the expected neighbours the 'getNeighbours' method should return for hexagon q0r0s0
        List<Hexagon> neighbours = input.getNeighbours(hex);
        List<String> neighbourIds = neighbours.stream().map(Hexagon::getId).toList();

        List<String> expectedNeighbourIds = List.of("q7rm7s0", "q5rm5s0", "q6rm7s1", "q6rm5sm1", "q7rm6sm1", "q5rm6s1");

        assertNotEquals(expectedNeighbourIds, neighbourIds);
    }


    @Test
    public void testSameColorNeighbourExists() {
        Board input = new Board(7);

        Hexagon hex = input.getHexagonById("q0r0s0");

        input.getHexagonById("q0r0s0").setColor(Color.RED);
        input.getHexagonById("qm1r0s1").setColor(Color.RED);

        assertTrue(input.sameColorNeighbourExists(hex));
    }

    @Test
    public void testSameColorNeighbourExistsFalse1() {
        Board input = new Board(7);

        Hexagon hex = input.getHexagonById("qm3r3s0");

        input.getHexagonById("qm2r2s0").setColor(Color.RED);
        input.getHexagonById("qm3r3s0").setColor(Color.BLUE);

        assertFalse(input.sameColorNeighbourExists(hex));
    }

    @Test
    public void testSameColorNeighbourExistsFalse2() {
        Board input = new Board(7);

        Hexagon hex = input.getHexagonById("qm3r3s0");

        input.getHexagonById("qm3r3s0").setColor(Color.BLUE);

        assertFalse(input.sameColorNeighbourExists(hex));
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
    public void testGetIslandSingular() {
        Board testBoard = new Board(7);

        testBoard.getHexagonById("q0r0s0").setColor(Color.RED);

        Hexagon hex = testBoard.getHexagonById("q0r0s0");

        List<Hexagon> island = testBoard.getIsland(hex);
        List<String> islandIds = island.stream().map(Hexagon::getId).toList();

        List<String> expectedIsland = List.of("q0r0s0");

        assertEquals(expectedIsland, islandIds);
    }

    @Test
    public void testGetWrongIslands() {
        Board testBoard = new Board(7);

        testBoard.getHexagonById("q0r0s0").setColor(Color.RED);
        testBoard.getHexagonById("qm1r0s1").setColor(Color.RED);
        testBoard.getHexagonById("qm2r0s2").setColor(Color.RED);

        testBoard.getHexagonById("q0r1sm1").setColor(Color.BLUE);
        testBoard.getHexagonById("qm1r1s0").setColor(Color.BLUE);
        testBoard.getHexagonById("qm2r1s1").setColor(Color.BLUE);

        Hexagon hex = testBoard.getHexagonById("qm2r0s2");

        List<Hexagon> island = testBoard.getIsland(hex);
        List<String> islandIds = island.stream().map(Hexagon::getId).toList();

        List<String> wrongIsland = List.of("q0r1sm1", "qm1r1s0", "qm2r1s1");

        assertNotEquals(wrongIsland, islandIds);
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

    @Test
    public void testWrongGetEnemyHexagon() {
        Board testBoard = new Board(7);

        testBoard.getHexagonById("qm2r0s2").setColor(Color.RED);
        testBoard.getHexagonById("qm3r0s3").setColor(Color.RED);

        testBoard.getHexagonById("q0rm1s1").setColor(Color.BLUE);
        testBoard.getHexagonById("qm1rm1s2").setColor(Color.BLUE);
        testBoard.getHexagonById("qm1r0s1").setColor(Color.BLUE);

        Hexagon hex = testBoard.getHexagonById("qm2r0s2");
        List<Hexagon> island = testBoard.getIsland(hex);
        List<Hexagon> enemyHexagons = testBoard.getEnemyHexagons(island);

        List<String> enemyHexagonIds = enemyHexagons.stream().map(Hexagon::getId).toList();

        List<String> wrongEnemyHexagons = List.of("q0rm1s1", "qm1rm1s2", "qm1r0s1");

        assertNotEquals(wrongEnemyHexagons, enemyHexagonIds);
    }

    @Test
    public void testValidateMovePreoccupiedHex() {
        Board testBoard = new Board(7);

        Hexagon hex1 = testBoard.getHexagonById("q0r0s0");
        hex1.setColor(Color.RED);

        assertNull(testBoard.validateMove(hex1, Color.BLUE));
    }

    @Test
    public void testValidateMoveNonCaptureMoveAlone() {
        Board testBoard = new Board(6);

        Hexagon hex1 = testBoard.getHexagonById("q0r0s0");

        assertEquals(testBoard.validateMove(hex1, Color.RED), new ArrayList<>());
    }

    @Test
    public void testValidateMoveInvalidCaptureMove1() {
        Board testBoard = new Board(6);

        testBoard.getHexagonById("q0r0s0").setColor(Color.RED);

        Hexagon hex1 = testBoard.getHexagonById("q1rm1s0");

        assertNull(testBoard.validateMove(hex1, Color.RED));
    }

    @Test
    public void testValidateMoveInvalidCaptureMove2() {
        Board testBoard = new Board(6);

        testBoard.getHexagonById("q2rm2s0").setColor(Color.RED);
        testBoard.getHexagonById("q3rm3s0").setColor(Color.RED);
        testBoard.getHexagonById("q4rm4s0").setColor(Color.RED);
        testBoard.getHexagonById("q0r0s0").setColor(Color.BLUE);

        Hexagon hex = testBoard.getHexagonById("q1rm1s0");

        assertNull(testBoard.validateMove(hex, Color.BLUE));
    }

    @Test
    public void testValidateMoveSelfSacrifise() {
        Board testBoard = new Board(6);

        testBoard.getHexagonById("q2rm2s0").setColor(Color.RED);
        testBoard.getHexagonById("q3rm3s0").setColor(Color.RED);
        testBoard.getHexagonById("q4rm4s0").setColor(Color.RED);

        Hexagon hex = testBoard.getHexagonById("q1rm1s0");

        assertEquals(testBoard.validateMove(hex, Color.BLUE), new ArrayList<>());
    }

    @Test
    public void testValidateMoveNonCaptureMoveEnemy() {
        Board testBoard = new Board(6);

        testBoard.getHexagonById("q2rm2s0").setColor(Color.RED);

        Hexagon hex = testBoard.getHexagonById("q1rm1s0");

        assertEquals(testBoard.validateMove(hex, Color.BLUE), new ArrayList<>());
    }

    @Test
    public void testValidateMoveCapturingMove() {
        Board testBoard = new Board(6);

        testBoard.getHexagonById("q6rm6s0").setColor(Color.RED);
        testBoard.getHexagonById("q5rm5s0").setColor(Color.RED);
        testBoard.getHexagonById("q1rm1s0").setColor(Color.RED);
        testBoard.getHexagonById("q0r0s0").setColor(Color.RED);

        testBoard.getHexagonById("q3rm3s0").setColor(Color.BLUE);
        testBoard.getHexagonById("q2rm2s0").setColor(Color.BLUE);

        Hexagon hex = testBoard.getHexagonById("q4rm4s0");

        List<String> hexToDelete = testBoard.validateMove(hex, Color.BLUE).stream().map(Hexagon::getId).toList();
        List<String> expectedHexToDelete = List.of("q5rm5s0", "q6rm6s0", "q1rm1s0", "q0r0s0");

        assertEquals(expectedHexToDelete, hexToDelete);
    }

    /**
     * Player Class Tests
     */
    @Test
    public void testGetEnemyColor() {
        Player p = new Player(Color.RED);

        assertEquals(Color.BLUE, p.getEnemyColor());
    }


    /**
     * Controller Class Tests
     */
    @Test
    public void testUpdateHexCounter1() {
        Controller con = new Controller();

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
}
