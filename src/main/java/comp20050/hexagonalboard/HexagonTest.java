package comp20050.hexagonalboard;

import javafx.scene.paint.Color;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HexagonTest {

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
}
