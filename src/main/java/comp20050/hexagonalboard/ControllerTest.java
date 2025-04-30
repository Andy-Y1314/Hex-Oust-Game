package comp20050.hexagonalboard;

import javafx.scene.paint.Color;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ControllerTest {

    @Test
    public void testUpdateHexCounterIncrementRed() {
        Controller controller = new Controller();

        controller.updateHexCounter(Color.RED, true);
        controller.updateHexCounter(Color.RED, true);

        assertEquals(2, controller.numRedHex);
    }

    @Test
    public void testUpdateHexCounterDecrementRed() {
        Controller controller = new Controller();

        controller.numRedHex = 3;
        controller.updateHexCounter(Color.RED, false);

        assertEquals(2, controller.numRedHex);
    }

    @Test
    public void testUpdateHexCounterIncrementBlue() {
        Controller controller = new Controller();

        for (int i = 0; i < 10; i++) {
            controller.updateHexCounter(Color.BLUE, true);
        }

        assertEquals(10, controller.numBlueHex);
    }

    @Test
    public void testUpdateHexCounterDecrementBlue() {
        Controller controller = new Controller();

        controller.numBlueHex = 12;

        for (int i = 0; i < 5; i++) {
            controller.updateHexCounter(Color.BLUE, false);
        }

        assertEquals(7, controller.numBlueHex);
    }

}
