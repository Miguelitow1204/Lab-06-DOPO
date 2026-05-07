package test;

import domain.*;
import java.awt.Color;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class SquirrelTest.
 *
 * @author  (Murillo Rubiano)
 * @version (1.0)
 */
public class SquirrelTest{
    // Pruebas Squirrel
    @Test
    public void testSquirrelInitialColor() {
        Forest forest = new Forest();
        Squirrel alvin = new Squirrel(forest, 5, 5);
        // deberia crear una nueva squirrel de 0 años, café
        assertEquals(new Color(139, 69, 19), alvin.getColor());
    }

    @Test
    public void testSquirrelDiesAfter10Years() {
        Forest forest = new Forest();
        Squirrel alvin = new Squirrel(forest, 5, 5);
        // deberia morir despues de 10ticTac
        for (int i = 0; i < 10; i++) {
            forest.ticTac();
        }
        assertNull(forest.getThing(5, 5));
    }
}