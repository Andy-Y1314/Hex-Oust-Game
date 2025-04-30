package comp20050.hexagonalboard;

import javafx.scene.paint.Color;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BoardTest {
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
    public void testHexagonsToRemovePreoccupiedHex() {
        Board testBoard = new Board(7);

        Hexagon hex1 = testBoard.getHexagonById("q0r0s0");
        hex1.setColor(Color.RED);

        assertNull(testBoard.HexagonsToRemove(hex1, Color.BLUE));
    }

    @Test
    public void testHexagonsToRemoveAlone() {
        Board testBoard = new Board(6);

        Hexagon hex1 = testBoard.getHexagonById("q0r0s0");

        assertEquals(new ArrayList<>(), testBoard.HexagonsToRemove(hex1, Color.RED));
    }

    @Test
    public void testHexagonsToRemove1() {
        Board testBoard = new Board(6);

        testBoard.getHexagonById("q0r0s0").setColor(Color.RED);

        Hexagon hex1 = testBoard.getHexagonById("q1rm1s0");

        assertNull(testBoard.HexagonsToRemove(hex1, Color.RED));
    }

    @Test
    public void testHexagonsToRemove2() {
        Board testBoard = new Board(6);

        testBoard.getHexagonById("q2rm2s0").setColor(Color.RED);
        testBoard.getHexagonById("q3rm3s0").setColor(Color.RED);
        testBoard.getHexagonById("q4rm4s0").setColor(Color.RED);
        testBoard.getHexagonById("q0r0s0").setColor(Color.BLUE);

        Hexagon hex = testBoard.getHexagonById("q1rm1s0");

        assertNull(testBoard.HexagonsToRemove(hex, Color.BLUE));
    }

    @Test
    public void testHexagonsToRemoveSelfSacrifise() {
        Board testBoard = new Board(6);

        testBoard.getHexagonById("q2rm2s0").setColor(Color.RED);
        testBoard.getHexagonById("q3rm3s0").setColor(Color.RED);
        testBoard.getHexagonById("q4rm4s0").setColor(Color.RED);

        Hexagon hex = testBoard.getHexagonById("q1rm1s0");

        assertEquals(new ArrayList<>(),testBoard.HexagonsToRemove(hex, Color.BLUE));
    }

    @Test
    public void testHexagonsToRemoveEnemy() {
        Board testBoard = new Board(6);

        testBoard.getHexagonById("q2rm2s0").setColor(Color.RED);

        Hexagon hex = testBoard.getHexagonById("q1rm1s0");

        assertEquals(new ArrayList<>() ,testBoard.HexagonsToRemove(hex, Color.BLUE));
    }

    @Test
    public void testHexagonsToRemove() {
        Board testBoard = new Board(6);

        testBoard.getHexagonById("q6rm6s0").setColor(Color.RED);
        testBoard.getHexagonById("q5rm5s0").setColor(Color.RED);
        testBoard.getHexagonById("q1rm1s0").setColor(Color.RED);
        testBoard.getHexagonById("q0r0s0").setColor(Color.RED);

        testBoard.getHexagonById("q3rm3s0").setColor(Color.BLUE);
        testBoard.getHexagonById("q2rm2s0").setColor(Color.BLUE);

        Hexagon hex = testBoard.getHexagonById("q4rm4s0");

        List<String> hexToDelete = testBoard.HexagonsToRemove(hex, Color.BLUE).stream().map(Hexagon::getId).toList();
        List<String> expectedHexToDelete = List.of("q5rm5s0", "q6rm6s0", "q1rm1s0", "q0r0s0");

        assertEquals(expectedHexToDelete, hexToDelete);
    }
}
