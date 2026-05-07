package test;

import domain.*;
import java.awt.Color;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class ShadowTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class ShadowTest{
    // Pruebas Shadow
    @Test
    public void testShadowColor() {
        Forest forest = new Forest();
        Shadow shadowTest = new Shadow(forest, 5, 5);
        assertEquals(Color.BLACK, shadowTest.getColor());
    }

    @Test
    public void thestShadowMovesToNorth() {
        Forest forest = new Forest();
        Shadow shadowTest = new Shadow(forest, 5, 5);
        forest.ticTac();
        assertNotNull(forest.getThing(4, 5));
        assertNull(forest.getThing(5, 5));
    }
}