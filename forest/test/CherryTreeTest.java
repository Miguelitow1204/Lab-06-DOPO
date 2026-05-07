package test;

import domain.*;
import java.awt.Color;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class CherryTreeTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class CherryTreeTest
{
    // Ciclo 4 pruebas cherryTree
    @Test
    public void testCherryTreeInitialColor() {
        Forest forest = new Forest();
        CherryTree cherry = new CherryTree(forest, 5, 5);
        assertEquals(Color.GREEN, cherry.getColor());
    }

    @Test
    public void testCherryTreeBlooms() {
        Forest forest = new Forest();
        CherryTree cherry = new CherryTree(forest, 3, 3);
        // After 4 tic-tacs it should bloom to pink
        for (int i = 0; i < 4; i++) {
            forest.ticTac();
        }
        assertEquals(new Color(205, 105, 180), cherry.getColor());
    }

    @Test
    public void testCherryTreeReturnsToGreen() {
        Forest forest = new Forest();
        CherryTree cherry = new CherryTree(forest, 3, 3);
        for (int i = 0; i < 8; i++) {
            forest.ticTac();
        }
        assertEquals(Color.GREEN, cherry.getColor());
    }
}